package com.bignerdranch.android.cua

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GamesFollowedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_followed)

        //idk if need bot
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        //empty game showing game
        if (currentFragment == null) {
            val fragment = GamesFollowedListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
