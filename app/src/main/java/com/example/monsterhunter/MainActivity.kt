package com.example.monsterhunter

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val armorsViewModel: ArmorsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        armorsViewModel.status.observe(this, Observer { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    pb.visibility = View.VISIBLE
                    error.visibility  = View.GONE
                    rv.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    pb.visibility = View.GONE
                    error.visibility  = View.VISIBLE
                    rv.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    pb.visibility = View.GONE
                    error.visibility  = View.GONE
                    rv.visibility = View.VISIBLE
                }
            }
        })
        armorsViewModel.armors.observe(this, Observer { armors ->
            if (!armors.isNullOrEmpty()) {
                rv.adapter = ArmorsAdapter(armors, this)
            }
        })

//        searchEditText.addTextChangedListener { searchText ->
//            if (searchText?.length != 0){
//                armorsViewModel.getFilteredArmors(searchEditText.text.toString())
//            }
//            else{
//                armorsViewModel.getArmors()
//            }
//        }
    }
}