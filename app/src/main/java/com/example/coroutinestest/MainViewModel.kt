package com.example.coroutinestest

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class MainViewModel(size: Int): ViewModel(){

    companion object{
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val mutableLiveDataList = MutableList(size){MutableLiveData<String>()}

    private val ascii = mutableListOf<Char>()
    private val emptyText = List(100){" "}
    private var text = ""
    var init = false

    fun setColumnsEmpty(columns: List<TextView>){
        for(ch in emptyText){
            text += ch
        }
        for (tView in columns){
            tView.text = text
        }
    }



    fun initAscii(){
        /**
         * two loops to avoid unknown symbols on screen
         */
        for (b in 0..20){
            ascii.add(10.toChar())
        }
        for (b in 32 until 64){
            ascii.add(10.toChar())
            ascii.add(b.toChar())
        }
        for (b in 64 until 96){
            ascii.add(10.toChar())
            ascii.add(b.toChar())
        }
        for (b in 0..10){
            ascii.add(10.toChar())
        }
        for (b in 96 until Byte.MAX_VALUE){
            ascii.add(10.toChar())
            ascii.add(b.toChar())
        }
        init = true
    }



    private suspend fun updateFlow(index: Int, delay: Long){
//        withContext(Dispatchers.Default) {
            val charText: MutableList<Char> = text.toMutableList()
            delay(delay)
            while (true) {
                val asciiIterator = ascii.iterator()
                while (asciiIterator.hasNext()) {
                            charText.removeLast()
                            charText.add(0, asciiIterator.next())
                            text = charText.toString()
                            text = text.replace(",", "")
                            text = text.replace("[", "")
                            text = text.replace("]", "")
                            mutableLiveDataList[index].postValue(text)
                            delay(16)

                }
            }
//        }
    }

    fun onUpdate(index: Int,
                 delay: Long){
        uiScope.launch(Dispatchers.Default){
            updateFlow(index, delay)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}