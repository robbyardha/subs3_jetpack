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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.main.MainActivity
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.ardhacodes.subs1_jetpack.vo.Resource
import com.ardhacodes.subs1_jetpack.vo.Status
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//class MovieFragment : Fragment(), CallbackMovTv {
class MovieFragment : Fragment(), MovieAdapter.OnItemClickCallback {
    private lateinit var fragMovBinds: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragMovBinds = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragMovBinds.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            (activity as MainActivity).setActionBarTitle("Movies List")

            showProgressBar(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            movieAdapter = MovieAdapter()
            viewModel.getDataMovieApi().observe(viewLifecycleOwner, movieObserver)

            val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

            with(fragMovBinds.rvMovie) {
//                addItemDecoration(MarginItemDecoration(marginVertical.toInt()))
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = movieAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    movieAdapter.submitList(movies.data)
                    movieAdapter.setOnItemClickCallback(this)
                    movieAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    fun configRecyclerView() {
//        rv_movie.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = MovieAdapter(this@MovieFragment)
//        }
//    }

    private fun setupViewModel(fragmentActivity: FragmentActivity) {
        viewModel = ViewModelProvider(fragmentActivity, factory)[MovieViewModel::class.java]
    }

//    private fun observeListMovies() {
//        viewModel.getListNowPlayingMovies().observe(viewLifecycleOwner, Observer { listMovie ->
//            if (listMovie != null) {
//                when (listMovie.status) {
//                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
//                    Status.SUCCESS -> {
//                        progress_bar.visibility = View.GONE
//                        rv_movie.adapter?.let { adapter ->
//                            when (adapter) {
//                                is MovieAdapter -> {
//                                    adapter.submitList(listMovie.data)
//                                    adapter.notifyDataSetChanged()
//                                }
//                            }
//                        }
//                    }
//                    Status.ERROR -> {
//                        progress_bar.visibility = View.GONE
//                        Toast.makeText(
//                            context,
//                            "Check your internet connection",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        })
//    }

//    fun viewModelProviderConfig() {
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
//    }

//    fun progressBarAction() {
//        val factoryVm = ViewModelFactory.getInstance()
//        val viewmodel = ViewModelProvider(this, factoryVm)[MovieViewModel::class.java]
//        val movieAdapter = MovieAdapter(this)
//        fragmentMovieBind.progressBar.visibility = View.VISIBLE
//        viewmodel.getDataMovieApi().observe(viewLifecycleOwner, { movie ->
//            fragmentMovieBind.progressBar.visibility = View.GONE
//            movieAdapter.setMovies(movie)
//            movieAdapter.notifyDataSetChanged()
//        })
//    }

    private fun showProgressBar(state: Boolean) {
        fragMovBinds.progressBar.isVisible = state
        fragMovBinds.progressBar.isInvisible = state
    }

    override fun onItemClicked(id: Int) {
        startActivity(
            Intent(context, DetailMovieTvActivity::class.java)
//                .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.title)
                .putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, EXTRA_MOVIE)
        )
    }

}