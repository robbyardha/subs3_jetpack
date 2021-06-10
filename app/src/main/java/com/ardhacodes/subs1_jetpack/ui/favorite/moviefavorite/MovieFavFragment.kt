package com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.favorite.FavoriteViewModel
import com.ardhacodes.subs1_jetpack.ui.movie.CallbackMov
import com.ardhacodes.subs1_jetpack.ui.movie.MovieAdapter
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_fav.*
import kotlinx.android.synthetic.main.message_empty.*
import javax.inject.Inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFavFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFavFragment : DaggerFragment(), CallbackMov {
    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        parentFragment?.let {
            viewModel = ViewModelProvider(it, factory)[FavoriteViewModel::class.java]
        }
        observeFavoriteMovies()

    }

    private fun observeFavoriteMovies() {
        viewModel.getListFavoriteMovie().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                rv_favmovie.adapter?.let { adapter ->
                    when (adapter) {
                        is MovieAdapter -> {
                            if (it.isNullOrEmpty()) {
                                rv_favmovie.visibility = View.GONE
                                enableEmptyStateEmptyFavoriteMovie()
                            } else {
                                rv_favmovie.visibility = View.VISIBLE
                                adapter.submitList(it)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rv_favmovie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(this)
        }
    }

    private fun enableEmptyStateEmptyFavoriteMovie() {
        img_empty_state.setImageResource(R.drawable.ic_error)
        img_empty_state.contentDescription =
            resources.getString(R.string.empty_state_desc_no_favorite_item_movie)
        title_empty_state.text = resources.getString(R.string.empty_state_title_no_favorite_item)
        desc_empty_state.text =
            resources.getString(R.string.empty_state_desc_no_favorite_item_movie)
        favorite_movie_empty_state.visibility = View.VISIBLE
    }


    override fun onItemClicked(movieEntity: MovieEntity) {
        startActivity(
            Intent(
                context,
                DetailMovieTvActivity::class.java
            )
                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movieEntity.idmovie)
                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, "TYPE")
        )
    }
}