package com.ardhacodes.subs1_jetpack.ui.detail

import android.graphics.Color
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.ActivityDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.databinding.ContentDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_TV_SHOW
import com.ardhacodes.subs1_jetpack.utils.Helper.setImageGlide
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class DetailMovieTvActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOV = "extra_mov"
        const val EXTRA_CATEGORY = "extra_cat"
    }

    private lateinit var detailbinding: ActivityDetailMovieTvBinding
    private lateinit var result: MovieTvEntity
    private lateinit var viewModel: DetailViewModel
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailbinding = ActivityDetailMovieTvBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

//        viewModelProviderConfig()

//        setupToolbar()
        setupViewModel()

        val id = intent.getIntExtra(EXTRA_MOV, 0)
        val category = intent.getStringExtra(EXTRA_CATEGORY)

        if (category.equals("TYPE_MOVIE", ignoreCase = true)) {
//            setupToolbarTitle(resources.getString(R.string.title_detail_mov))
            observeDetailMovie(id)

        } else if (category.equals("TYPE_TVSHOW", ignoreCase = true)) {
//            setupToolbarTitle(resources.getString(R.string.title_detail_tv))
            observeDetailTv(id)
        }


    }

    fun dataDetail(result: MovieTvEntity) {
        var titlebinding = detailbinding.tvTitle
        var genrebinding = detailbinding.tvGenre
//        var genrebinding = detailbinding.tvGenre
        var releasebinding = detailbinding.tvYear
        var scorebinding = detailbinding.tvScore
//        var durationbinding = detailbinding.tvDuration
        var overviewbinding = detailbinding.tvOverview
        var posterbinding = detailbinding.ivPoster

        titlebinding.text = result.title
        genrebinding.text = "Release : ${result.release_date}"
        releasebinding.text = "Vote Average : ${result.vote_average}"
        scorebinding.text = "Popularity : ${result.popularity}"
        overviewbinding.text = result.overview
        Glide.with(this@DetailMovieTvActivity)
            .load(path + image_w185 + result.poster_path)
            .apply(RequestOptions())
            .into(posterbinding)
    }

    private fun observeDetailMovie(movieId: Int) {
        viewModel.getDetailMovieapis(movieId).observe(this, Observer {
            displayData(it, null)
        })
    }

    private fun observeDetailTv(tvId: Int) {
        viewModel.getDetailTvapis(tvId).observe(this, Observer {
            it?.let {
                displayData(null, it)
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this@DetailMovieTvActivity,
            factory
        )[DetailViewModel::class.java]
    }

    private fun displayData(movie: MovieEntity?, tvShow: TvEntity?) {
//        Binding
        var titlebinding = detailbinding.tvTitle
        var genrebinding = detailbinding.tvGenre
        var releasebinding = detailbinding.tvYear
        var scorebinding = detailbinding.tvScore
        var overviewbinding = detailbinding.tvOverview
        var posterbinding = detailbinding.ivPoster

//        Entity
        val statusLove = movie?.is_favorite; tvShow?.is_favorite

//        Into
        if (movie != null) {
            titlebinding.text = movie.title
            genrebinding.text = "Release : ${movie.release_date}"
            releasebinding.text = "Vote Average : ${movie.vote_average}"
            scorebinding.text = "Popularity : ${movie.popularity}"
            overviewbinding.text = movie.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + movie.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)
            statusLove?.let { status ->
                setIsLove(status)
            }
            var loveIs = detailbinding.floatingActionButton
            loveIs.setOnClickListener {
                setFavorite(movie, tvShow)
            }
        } else if (tvShow != null) {
            titlebinding.text = tvShow.title
            genrebinding.text = "Release : ${tvShow.release_date}"
            releasebinding.text = "Vote Average : ${tvShow.vote_average}"
            scorebinding.text = "Popularity : ${tvShow.popularity}"
            overviewbinding.text = tvShow.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + tvShow.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)
            statusLove?.let { status ->
                setIsLove(status)
            }
            var loveIs = detailbinding.floatingActionButton
            loveIs.setOnClickListener {
                setFavorite(movie, tvShow)
            }
        }
    }

    private fun setIsLove(status: Boolean) {
        if (status) {
            detailbinding.floatingActionButton.setImageResource(R.drawable.ic_love_red)
        } else {
            detailbinding.floatingActionButton.setImageResource(R.drawable.ic_love_white)
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun setFavorite(movie: MovieEntity?, tvShow: TvEntity?) {
        if (movie != null) {
            if (movie.is_favorite) {
                showSnackBar("${movie.title} Dihapus dari daftar favorite")
            } else {
                showSnackBar("${movie.title} Ditambah dari daftar favorite")
            }
            viewModel.setFavoriteMovie(movie)
        } else {
            if (tvShow != null) {
                if (tvShow.is_favorite) {
                    showSnackBar("${tvShow.title} Ditambah dari daftar favorite")
                } else {
                    showSnackBar("${tvShow.title} Dihapus dari daftar favorite")
                }
                viewModel.setFavoriteTv(tvShow)
            }
        }
    }

//    private fun setupToolbar() {
//        setSupportActionBar(detail_toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT)
//    }

//    private fun setupToolbarTitle(title: String) {
//        supportActionBar?.title = title
//    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}