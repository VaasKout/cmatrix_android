package com.example.coroutinestest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstColumn = findViewById<TextView>(R.id.firstColumn)
        val secondColumn = findViewById<TextView>(R.id.secondColumn)
        val thirdColumn = findViewById<TextView>(R.id.thirdColumn)
        val fourthColumn = findViewById<TextView>(R.id.fourthColumn)
        val fifthColumn = findViewById<TextView>(R.id.fifthColumn)
        val sixthColumn = findViewById<TextView>(R.id.sixthColumn)
        val seventhColumn = findViewById<TextView>(R.id.seventhColumn)
        val eighthColumn = findViewById<TextView>(R.id.eighthColumn)
        val ninthColumn = findViewById<TextView>(R.id.ninthColumn)
        val tenthColumn = findViewById<TextView>(R.id.tenthColumn)
        val eleventhColumn = findViewById<TextView>(R.id.eleventhColumn)

        val columns = listOf(firstColumn, secondColumn, thirdColumn,
            fourthColumn, fifthColumn, sixthColumn, seventhColumn,
            eighthColumn, ninthColumn, tenthColumn, eleventhColumn,
        )

        val viewModel =
            ViewModelProvider(this, MainViewModel.FACTORY(columns.size))
                .get(MainViewModel::class.java)

        if (!viewModel.init){
            viewModel.setColumnsEmpty(columns)
            viewModel.initAscii()
            viewModel.onUpdate(0,500)
            viewModel.onUpdate(1, 2000)
            viewModel.onUpdate(2, 2500)
            viewModel.onUpdate(3, 1500)
            viewModel.onUpdate(4, 2400)
            viewModel.onUpdate(5, 2700)
            viewModel.onUpdate(6, 1800)
            viewModel.onUpdate(7, 2000)
            viewModel.onUpdate(8, 800)
            viewModel.onUpdate(9, 1200)
            viewModel.onUpdate(10, 3000)
        }

        for (index in 0 until viewModel.mutableLiveDataList.size){
            viewModel.mutableLiveDataList[index].observe(this, {
                    columns[index].text = it
            })
        }
    }
}