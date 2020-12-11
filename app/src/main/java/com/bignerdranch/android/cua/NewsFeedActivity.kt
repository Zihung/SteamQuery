package com.bignerdranch.android.cua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bignerdranch.android.cua.api.MyPojo
import com.bignerdranch.android.cua.api.getJsonDataFromAsset

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_feed.*

class NewsFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed)


    }

}