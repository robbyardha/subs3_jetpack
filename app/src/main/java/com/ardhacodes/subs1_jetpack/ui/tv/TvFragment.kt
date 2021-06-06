package com.ardhacodes.subs1_jetpack.ui.tv

import android.content.Intent
import android.media.tv.TvView
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentMovieBinding
import com.ardhacodes.subs1_jetpack.databinding.FragmentTvBinding
import com.ardhacodes.subs1_jetpack.ui.CallbackMovTv
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.main.MainActivity
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


class TvFragment : Fragment(), TvAdapter.OnItemClickCallback {
    private lateinit var viewModel: TvViewModel

    lateinit var factory: ViewModelFactory
    private lateinit var fragmentTvBind: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTvBind = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTvBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            (activity as MainActivity).setActionBarTitle("TV Shows List")

            showProgressBar(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]

            val tvShowAdapter = TvAdapter()
            viewModel.getDataTvAPI().observe(viewLifecycleOwner, { tvShows ->
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

//            val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

            with(fragmentTvBind.rvTv) {
//                addItemDecoration(MarginItemDecoration(marginVertical.toInt()))
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        fragmentTvBind.progressBar.isVisible = state
        fragmentTvBind.progressBar.isInvisible = state
    }

//    override fun onItemClicked(tvEntity: TvEntity) {
//        startActivity(
//            Intent(context, DetailMovieTvActivity::class.java)
////            .putExtra(DetailMovieTvActivity.EXTRA_MOV, movtvEntity.title)
//                .putExtra(DetailMovieTvActivity.EXTRA_MOV, tvEntity.idtv)
//                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, Helper.EXTRA_TV_SHOW)
//        )
//    }

//    fun configRecyclerView()
//    {
//        rv_tv.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = TvAdapter(this@TvFragment)
//        }
//    }

    private fun setupViewModel(fragmentActivity: FragmentActivity) {
        viewModel = ViewModelProvider(fragmentActivity, factory)[TvViewModel::class.java]
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailMovieTvActivity::class.java)
        intent.putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
        intent.putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, )

        context?.startActivity(intent)
    }


}
