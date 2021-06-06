package com.ardhacodes.subs1_jetpack.ui.tv

import android.content.Intent
import android.media.tv.TvView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.databinding.FragmentTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.movie.MovieAdapter
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.utils.Helper
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.ardhacodes.subs1_jetpack.vo.Status
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.progress_bar
import kotlinx.android.synthetic.main.fragment_tv.*
import javax.inject.Inject


class TvFragment : DaggerFragment(), TvFragmentCallback {
    private lateinit var viewModel: TvViewModel

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var fragmentMovieBind: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMovieBind = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configRecyclerView()
        activity?.let { setupViewModel(it) }
        observeListMovies()
    }

    override fun onItemClicked(tvEntity: TvEntity) {
        startActivity(
            Intent(context, DetailMovieTvActivity::class.java)
//            .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.title)
                .putExtra(DetailMovieTvActivity.EXTRA_MOV, tvEntity.id)
                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, Helper.EXTRA_TV_SHOW)
        )
    }

    fun configRecyclerView()
    {
        rv_tv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TvAdapter(this@TvFragment)
        }
    }

    private fun setupViewModel(fragmentActivity: FragmentActivity) {
        viewModel = ViewModelProvider(fragmentActivity, factory)[TvViewModel::class.java]
    }

    private fun observeListMovies() {
        viewModel.getListPopularTv().observe(viewLifecycleOwner, Observer { listTv ->
            if (listTv != null) {
                when (listTv.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        rv_tv.adapter?.let { adapter ->
                            when (adapter) {
                                is TvAdapter -> {
                                    adapter.submitList(listTv.data)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

}
