package com.ardhacodes.subs1_jetpack.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.MOVIE_VIEWMDL
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.utils.SortUtils.VOTE_BEST
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.ardhacodes.subs1_jetpack.vo.Resource
import com.ardhacodes.subs1_jetpack.vo.Status

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment(), MovieAdapter.OnItemClickCallback {
    private lateinit var viewmodel: MovieViewModel
    private lateinit var movieadapter: MovieAdapter
    private lateinit var fragmentMovieBind: FragmentMovieBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

//

    fun viewModelProviderConfig() {

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewmodel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        movieadapter = MovieAdapter()
        viewmodel.getDataMovieApi(VOTE_BEST).observe(viewLifecycleOwner, movObeserver)

        val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

        with(fragmentMovieBind.rvMovie) {
            addItemDecoration(MovieDecoration(marginVertical.toInt()))
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = movieadapter
        }



//        val factoryVm = ViewModelFactory.getInstance()
//        val viewmodel = ViewModelProvider(this, factoryVm)[MovieViewModel::class.java]
//        val movieAdapter = MovieAdapter(this)
//        fragmentMovieBind.progressBar.visibility = View.VISIBLE
//        viewmodel.getDataMovieApi().observe(viewLifecycleOwner, { movie ->
//            fragmentMovieBind.progressBar.visibility = View.GONE
//            movieAdapter.setMovies(movie)
//            movieAdapter.notifyDataSetChanged()
//        })
//
//        with(fragmentMovieBind.rvMovie) {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            this.adapter = movieAdapter
//        }
    }

    private val movObeserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> progressBarAction(true)
                Status.SUCCESS -> {
                    progressBarAction(false)
                    movieadapter.submitList(movies.data)
                    movieadapter.setOnItemClickCallback(this)
                    movieadapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    progressBarAction(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun progressBarAction(state: Boolean) {
        fragmentMovieBind.progressBar.isVisible = state
        fragmentMovieBind.rvMovie.isInvisible = state

    }

    override fun onItemClicked(id: Int) {
        val intent = Intent(context, DetailMovieTvActivity::class.java)
        intent.putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
        intent.putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, MOVIE_VIEWMDL)

        context?.startActivity(intent)
    }

//    override fun onItemClicked(movtvEntity: MovieTvEntity) {
////        startActivity(
////            Intent(context, DetailMovieTvActivity::class.java)
//////                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.title)
////                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.id)
////                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, EXTRA_MOVIE)
////        )
////    }

}