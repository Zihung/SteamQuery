package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

private const val TAG = "CrimeListFragment"

class GamesListFragment : Fragment() {
    private val gamesListViewModel: GamesListViewModel by lazy {
        ViewModelProviders.of(this).get(GamesListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${gamesListViewModel.games.size}")
    }
    companion object {
        fun newInstance(): GamesListFragment {
            return GamesListFragment()
        }
    }
}