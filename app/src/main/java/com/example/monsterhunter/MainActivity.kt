package com.example.monsterhunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val videosViewModel: ArmorsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videosViewModel.status.observe(this, Observer { status ->
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
        videosViewModel.armors.observe(this, Observer { armors ->
            if (!armors.isNullOrEmpty()) {
                rv.adapter = ArmorsAdapter(armors, this)
            }
        })
    }
}