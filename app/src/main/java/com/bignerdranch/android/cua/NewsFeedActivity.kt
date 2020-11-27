package com.bignerdranch.android.cua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed)


        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    NewsFeedFragment.newInstance())
                .commit()
        }
    }
}