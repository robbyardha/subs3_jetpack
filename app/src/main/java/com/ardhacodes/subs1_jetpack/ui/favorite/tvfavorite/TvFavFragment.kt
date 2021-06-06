package com.ardhacodes.subs1_jetpack.ui.favorite.tvfavorite

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
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailMovieTvActivity
import com.ardhacodes.subs1_jetpack.ui.favorite.FavoriteViewModel
import com.ardhacodes.subs1_jetpack.ui.tv.TvAdapter
import com.ardhacodes.subs1_jetpack.ui.tv.TvFragmentCallback
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_tv_fav.*
import kotlinx.android.synthetic.main.message_empty.*
import javax.inject.Inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TvFavFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TvFavFragment : DaggerFragment(), TvFragmentCallback {

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        parentFragment?.let {
            viewModel = ViewModelProvider(it, factory)[FavoriteViewModel::class.java]
        }
        observeFavoriteTvShow()

    }

    private fun observeFavoriteTvShow() {
        viewModel.getListFavoriteTvShow().observe(viewLifecycleOwner, Observer {
            if (it != null){
                rv_favtv.adapter?.let {adapter ->
                    when (adapter) {
                        is TvAdapter -> {
                            if (it.isNullOrEmpty()){
                                rv_favtv.visibility = View.GONE
                                enableEmptyStateEmptyFavoriteTvShow()
                            } else {
                                rv_favtv.visibility = View.VISIBLE
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
        rv_favtv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TvAdapter(this@TvFavFragment)
        }
    }

    private fun enableEmptyStateEmptyFavoriteTvShow() {
        img_empty_state.setImageResource(R.drawable.ic_error)
        img_empty_state.contentDescription =
            resources.getString(R.string.empty_state_desc_no_favorite_item_tvshow)
        title_empty_state.text = resources.getString(R.string.empty_state_title_no_favorite_item)
        desc_empty_state.text =
            resources.getString(R.string.empty_state_desc_no_favorite_item_tvshow)
        favorite_tv_empty_state.visibility = View.VISIBLE
    }

    override fun onItemClicked(tvEntity: TvEntity) {
        startActivity(
            Intent(
                context,
                DetailMovieTvActivity::class.java
            )
                .putExtra(DetailMovieTvActivity.EXTRA_MOV, tvEntity.idtv)
                .putExtra(DetailMovieTvActivity.EXTRA_CATEGORY, "TYPE_TVSHOW")
        )
    }

}