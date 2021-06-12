package com.ardhacodes.subs1_jetpack.ui.tv

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.databinding.FragmentTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.TV_VIEWMDL
import com.ardhacodes.subs1_jetpack.ui.main.MainActivity
import com.ardhacodes.subs1_jetpack.ui.movie.MovieAdapter
import com.ardhacodes.subs1_jetpack.ui.movie.MovieDecoration
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.utils.Helper
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.ardhacodes.subs1_jetpack.utils.SortUtils
import com.ardhacodes.subs1_jetpack.utils.SortUtils.VOTE_BEST
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.ardhacodes.subs1_jetpack.vo.Status


class TvFragment : Fragment(), TvAdapter.OnItemClickCallback {

    lateinit var fragmentTvBinding : FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentTvBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            viewModelProviderConfig()
        }
    }

    private fun viewModelProviderConfig() {
        (activity as MainActivity).setActionBarTitle("TV Shows List")

        showProgressBar(true)
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]

        val tvShowAdapter = TvAdapter()
        viewModel.getDataTvAPI(VOTE_BEST).observe(viewLifecycleOwner, { tvShows ->
            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        showProgressBar(false)
                        tvShowAdapter.submitList(tvShows.data)
                        tvShowAdapter.setOnItemClickCallback(this)
                        tvShowAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

        with(fragmentTvBinding.rvTv) {
            addItemDecoration(TvDecoration(marginVertical.toInt()))
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = tvShowAdapter
        }
    }


    override fun onItemClicked(id: Int) {
        val intent = Intent(context, DetailMovieTvActivity::class.java)
        intent.putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
        intent.putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, TV_VIEWMDL)

        context?.startActivity(intent)
    }

    private fun showProgressBar(state: Boolean) {
        fragmentTvBinding.progressBar.isVisible = state
        fragmentTvBinding.rvTv.isInvisible = state
    }
}