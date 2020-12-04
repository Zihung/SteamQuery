package com.bignerdranch.android.cua

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NewsItemPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed)

        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = NewsItemPageFragment.newInstance(intent.data)
            fm.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    companion object {
        fun newIntent(context: Context, newsItemPageUri: Uri): Intent {
            return Intent(context, NewsItemPageActivity::class.java).apply {
                data = newsItemPageUri
            }
        }
    }
}