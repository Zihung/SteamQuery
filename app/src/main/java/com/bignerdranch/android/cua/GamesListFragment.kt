package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.Game

private const val TAG = "CrimeListFragment"

class GamesListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: GameAdapter? = null

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games_followed_list,
            container, false)
        crimeRecyclerView =
            view.findViewById(R.id.games_followed_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        val games = gamesListViewModel.games
        adapter = GameAdapter(games)
        crimeRecyclerView.adapter = adapter
    }



    private inner class GameHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var game: Game

        private val titleTextView: TextView =
            itemView.findViewById(R.id.game_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.game_appid)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(game: Game) {
            this.game = game
            titleTextView.text = this.game.name
            dateTextView.text = this.game.appid
        }


        // CODE ADD/REMOVE LOGIC HERE PROBABLY
        override fun onClick(v: View) {
            Toast.makeText(context, "${game.name} pressed!",
                Toast.LENGTH_SHORT)
                .show()
        }
    }



    private inner class GameAdapter(var games: List<Game>)
        : RecyclerView.Adapter<GameHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType:
        Int)
                : GameHolder {
            val view = layoutInflater.inflate(R.layout.list_item_game,
                parent, false)
            return GameHolder(view)
        }
        override fun getItemCount() = games.size
        override fun onBindViewHolder(holder: GameHolder, position:
        Int) {
            val game = games[position]
            holder.bind(game)
        }
    }
}