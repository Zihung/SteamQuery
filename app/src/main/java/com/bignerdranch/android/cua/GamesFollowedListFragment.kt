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

private const val TAG = "GamesFollowedListFrag"
private const val ARG_APPS = "apps_map"

class GamesFollowedListFragment : Fragment() {

    private lateinit var gameRecyclerView: RecyclerView
    private var adapter: GameAdapter? = null
    private lateinit var appsMap : HashMap<String, String>


    private val gamesFollowedListViewModel: GamesFollowedListViewModel by lazy {
        ViewModelProviders.of(this).get(gamesFollowedListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)

        //gamesFollowedListViewModel = ViewModelProviders.of(this).get(GamesFollowedListViewModel::class.java)
        appsMap = arguments?.getSerializable(ARG_APPS) as HashMap<String, String>



    }

    companion object {
        fun newInstance(appsMap: HashMap<String, String>): NewsFeedFragment {

            val args = Bundle().apply{
//                putAll(ARG_APPS, appsMap)
                putSerializable(ARG_APPS, appsMap)
            }
            return NewsFeedFragment().apply{
                arguments = args
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games_followed_list,
            container, false)
        gameRecyclerView =
            view.findViewById(R.id.games_followed_recycler_view) as RecyclerView
        gameRecyclerView.layoutManager = LinearLayoutManager(context)
        //idk if need
        updateUI()

        return view
    }

    private fun updateUI() {
        val games = gamesFollowedListViewModel.games
        adapter = GameAdapter(games)
        gameRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_games_followed_list, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String):
                        Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    gamesFollowedListViewModel.findGames(queryText)
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

    private inner class GameHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var game: Game

        private val nameTextView: TextView = itemView.findViewById(R.id.game_name)
        private val appidTextView: TextView = itemView.findViewById(R.id.game_appid)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(game: Game) {
            this.game= game
            nameTextView.text = this.game.name
            appidTextView.text = this.game.appid
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${game.name} pressed!",
                Toast.LENGTH_SHORT)
                .show()

            //val intent = NewsItemPageActivity.newIntent(requireContext(), newsItem.newsPageUri)
            //startActivity(intent)
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
