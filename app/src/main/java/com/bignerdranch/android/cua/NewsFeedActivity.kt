package com.bignerdranch.android.cua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.bignerdranch.android.cua.api.MyPojo
import com.bignerdranch.android.cua.api.getJsonDataFromAsset

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_feed.*

class NewsFeedActivity : AppCompatActivity() {

    private val gamesFollowedListFragment = GamesFollowedListFragment()
    private val newsFeedFragment = NewsFeedFragment()
    private val infoFragment = InfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed)

        val jsonFileString = getJsonDataFromAsset(applicationContext, "games.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }

        val gson = Gson()

        val games: MyPojo = gson.fromJson(jsonFileString, MyPojo::class.java)
        games.applist?.setAppsMap()

        val appsMap : HashMap<String, String> = games.applist!!.getAppsMap()



        val bundle = Bundle()
        bundle.putSerializable("hashmap", appsMap)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    NewsFeedFragment.newInstance(appsMap))
                .commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.ic_home -> replaceFragment(newsFeedFragment)
                R.id.ic_favorite -> replaceFragment(gamesFollowedListFragment)
                R.id.ic_info -> replaceFragment(infoFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }
    }
}