package com.ardhacodes.subs1_jetpack.ui.favorite.moviefavorite

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieFavBinding
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.MOVIE_VIEWMDL
import com.ardhacodes.subs1_jetpack.ui.movie.CallbackMov
import com.ardhacodes.subs1_jetpack.ui.movie.MovieAdapter
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_fav.*
import kotlinx.android.synthetic.main.message_empty.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFavFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFavFragment : Fragment(), MovieFavAdapter.OnItemClickCallback {
    private var fragmentFavMovBinding : FragmentMovieFavBinding? = null
    private val binding get() = fragmentFavMovBinding

    private lateinit var viewModel: MovieFavViewModel
    private lateinit var adapter: MovieFavAdapter

    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentFavMovBinding = FragmentMovieFavBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        itemTouchHelper.attachToRecyclerView(binding?.rvFavmovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieFavViewModel::class.java]

            adapter = MovieFavAdapter()
            adapter.setOnItemClickCallback(this)

            viewModel.getFavMov().observe(viewLifecycleOwner, { favMovies ->
                if (favMovies != null) {
                    adapter.submitList(favMovies)
                }
            })

            val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

            with(binding?.rvFavmovie) {
                this?.addItemDecoration(MovieFavDecoration(marginVertical.toInt()))
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }



//    private fun setupRecyclerView() {
//        rv_favmovie.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = MovieAdapter(this)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavMovBinding = null
    }

    override fun onItemClicked(id: Int) {
        val intent = Intent(context, DetailMovieTvActivity::class.java)
        intent.putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
        intent.putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, MOVIE_VIEWMDL)

        context?.startActivity(intent)
    }
}