package com.bignerdranch.android.cua

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cua.api.Game

private const val TAG = "CrimeListFragment"
private const val ARG_APPS = "apps_map"

class GamesListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: GameAdapter? = null
    private lateinit var appsMap : HashMap<String, String>

    private val gamesListViewModel: GamesListViewModel by lazy {
        ViewModelProviders.of(this).get(GamesListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        setHasOptionsMenu(true)
        Log.d(TAG, "Total crimes: ${gamesListViewModel.games.size}")
        appsMap = arguments?.getSerializable(ARG_APPS) as HashMap<String, String>
    }
    companion object {
        fun newInstance(appsMap: HashMap<String, String>): GamesListFragment {

            val args = Bundle().apply {
//                putAll(ARG_APPS, appsMap)
                putSerializable(ARG_APPS, appsMap)
            }
            return GamesListFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_news_feed, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String):
                        Boolean {

                    // convert name into app id
                    val appid : String? = appsMap[queryText.toLowerCase()]
                    Log.d(TAG, "Name: $queryText, Appid: $appid")

                    if (appid != null) {
                        gamesListViewModel.followGame(queryText, appid)
                    }
                    else{
                        val text = "Game not found. Please enter full name of game including spaces and special characters."
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }
                    updateUI()
                    return true
                }

                override fun onQueryTextChange(queryText: String):
                        Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })
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