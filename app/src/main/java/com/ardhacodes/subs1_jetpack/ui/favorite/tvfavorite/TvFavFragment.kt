package com.ardhacodes.subs1_jetpack.ui.favorite.tvfavorite

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.FragmentTvFavBinding
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.TV_VIEWMDL
import com.ardhacodes.subs1_jetpack.ui.tv.TvAdapter
import com.ardhacodes.subs1_jetpack.ui.tv.TvFragmentCallback
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_fav.*
import kotlinx.android.synthetic.main.message_empty.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TvFavFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TvFavFragment : Fragment(), TvFavAdapter.OnItemClickCallback {

    private var fragmentTvFavBinding: FragmentTvFavBinding? = null
    private val binding get() = fragmentTvFavBinding
    private lateinit var adapter: TvFavAdapter
    private lateinit var viewModel: TvFavViewModel
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTvFavBinding = FragmentTvFavBinding.inflate(layoutInflater, container, false)
        return binding?.root
//        return inflater.inflate(R.layout.fragment_tv_fav, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavTv().observe(viewLifecycleOwner, { favTvShow ->
            if (favTvShow != null) {
                adapter.submitList(favTvShow)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[TvFavViewModel::class.java]

            adapter = TvFavAdapter()
            adapter.setOnItemClickCallback(this)

            viewModel.getFavTv().observe(viewLifecycleOwner, { favTvShow ->
                if (favTvShow != null) {
                    adapter.submitList(favTvShow)
                }
            })

            val marginVertical = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16f,
                resources.displayMetrics
            )

            with(binding?.rvFavtv) {
                this?.addItemDecoration(TvFavDecoration(marginVertical.toInt()))
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        setupRecyclerView()
//
//        parentFragment?.let {
//            viewModel = ViewModelProvider(it, factory)[FavoriteViewModel::class.java]
//        }
//        observeFavoriteTvShow()
//
//    }

    //
//    private fun setupRecyclerView() {
//        rv_favtv.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = TvAdapter(this@TvFavFragment)
//        }
//    }
//
    override fun onItemClicked(id: Int) {
        val intent = Intent(context, DetailMovieTvActivity::class.java)
        intent.putExtra(DetailMovieTvActivity.EXTRA_MOV, id)
        intent.putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, TV_VIEWMDL)

        context?.startActivity(intent)
    }

}