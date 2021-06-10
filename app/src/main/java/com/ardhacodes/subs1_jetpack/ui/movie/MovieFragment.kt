package com.ardhacodes.subs1_jetpack.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment(), CallbackMovTv {
    private lateinit var fragmentMovieBind: FragmentMovieBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            viewModelProviderConfig()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMovieBind = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBind.root
    }

    override fun onItemClicked(movtvEntity: MovieTvEntity) {
        startActivity(
            Intent(context, DetailMovieTvActivity::class.java)
//                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.title)
                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.id)
                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, EXTRA_MOVIE)
        )
    }

    fun viewModelProviderConfig() {
        val factoryVm = ViewModelFactory.getInstance()
        val viewmodel = ViewModelProvider(this, factoryVm)[MovieViewModel::class.java]
        val movieAdapter = MovieAdapter(this)
        fragmentMovieBind.progressBar.visibility = View.VISIBLE
        viewmodel.getDataMovieApi().observe(viewLifecycleOwner, { movie ->
            fragmentMovieBind.progressBar.visibility = View.GONE
            movieAdapter.setMovies(movie)
            movieAdapter.notifyDataSetChanged()
        })

        with(fragmentMovieBind.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = movieAdapter
        }
    }

    fun progressBarAction() {
        val factoryVm = ViewModelFactory.getInstance()
        val viewmodel = ViewModelProvider(this, factoryVm)[MovieViewModel::class.java]
        val movieAdapter = MovieAdapter(this)
        fragmentMovieBind.progressBar.visibility = View.VISIBLE
        viewmodel.getDataMovieApi().observe(viewLifecycleOwner, { movie ->
            fragmentMovieBind.progressBar.visibility = View.GONE
            movieAdapter.setMovies(movie)
            movieAdapter.notifyDataSetChanged()
        })
    }

}