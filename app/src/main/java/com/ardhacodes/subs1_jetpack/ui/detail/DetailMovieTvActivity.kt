package com.ardhacodes.subs1_jetpack.ui.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.ActivityDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.databinding.ContentDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.MOVIE
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_TV_SHOW
import com.ardhacodes.subs1_jetpack.utils.Helper.setImageGlide
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.ardhacodes.subs1_jetpack.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject

class DetailMovieTvActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_MOV = "extra_mov"
        const val EXTRA_CATEGORY = "extra_cat"
    }

    private lateinit var detailbinding: ActivityDetailMovieTvBinding
    private lateinit var result: MovieTvEntity
    private lateinit var viewModel: DetailViewModel
    private var dataCategory: String? = null
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailbinding = ActivityDetailMovieTvBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

        supportActionBar?.hide()

        showProgressBar(true)

//        detailbinding.toolbar.setNavigationOnClickListener { onBackPressed() }
//        detailbinding.appbar.addOnOffsetChangedListener(this)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        detailbinding.floatingActionButton.setOnClickListener(this)

        val extras = intent.extras
        if (extras != null) {
            val dataId = extras.getString(EXTRA_MOV)
            dataCategory = extras.getString(EXTRA_CATEGORY)

            if (dataId != null && dataCategory != null) {
                viewModel.setFilm(dataId, dataCategory.toString())
                setupState()
                if (dataCategory == MOVIE) {
                    viewModel.getDetailMovieApis().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetailMov(detail.data!!)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                } else if (dataCategory == TV_SHOW) {
                    viewModel.getDetailTvApis().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetailTv(detail.data!!)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }


    }

    private fun showProgressBar(state: Boolean) {
        detailbinding.progressBar.isVisible = state
        detailbinding.progressBar.isInvisible = state
    }

    private fun setupState() {
        if (dataCategory == MOVIE) {
            viewModel.getDetailMovieApis().observe(this, { movie ->
                when (movie.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (movie.data != null) {
                            showProgressBar(false)
                            val state = movie.data!!.is_favorite
                            setIsLove(state)
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if (dataCategory == TV_SHOW) {
            viewModel.getDetailTvApis().observe(this, { tvShow ->
                when (tvShow.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (tvShow.data != null) {
                            showProgressBar(false)
                            val state = tvShow.data!!.is_favorite
                            setIsLove(state)
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
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



//    private fun setupViewModel() {
//        viewModel = ViewModelProvider(
//            this@DetailMovieTvActivity,
//            factory
//        )[DetailViewModel::class.java]
//    }

//    private fun displayData(movie: MovieEntity?, tvShow: TvEntity?) {
////        Binding
//        var titlebinding = detailbinding.tvTitle
//        var genrebinding = detailbinding.tvGenre
//        var releasebinding = detailbinding.tvYear
//        var scorebinding = detailbinding.tvScore
//        var overviewbinding = detailbinding.tvOverview
//        var posterbinding = detailbinding.ivPoster
//
////        Entity
//        val statusLove = movie?.is_favorite; tvShow?.is_favorite
//
////        Into
//        if (movie != null) {
//            titlebinding.text = movie.title
//            genrebinding.text = "Release : ${movie.release_date}"
//            releasebinding.text = "Vote Average : ${movie.vote_average}"
//            scorebinding.text = "Popularity : ${movie.popularity}"
//            overviewbinding.text = movie.overview
//            Glide.with(this@DetailMovieTvActivity)
//                .load(path + image_w185 + movie.poster_path)
//                .apply(RequestOptions())
//                .into(posterbinding)
//            statusLove?.let { status ->
//                setIsLove(status)
//            }
//            var loveIs = detailbinding.floatingActionButton
//            loveIs.setOnClickListener {
//                setFavorite(movie, tvShow)
//            }
//        } else if (tvShow != null) {
//            titlebinding.text = tvShow.title
//            genrebinding.text = "Release : ${tvShow.release_date}"
//            releasebinding.text = "Vote Average : ${tvShow.vote_average}"
//            scorebinding.text = "Popularity : ${tvShow.popularity}"
//            overviewbinding.text = tvShow.overview
//            Glide.with(this@DetailMovieTvActivity)
//                .load(path + image_w185 + tvShow.poster_path)
//                .apply(RequestOptions())
//                .into(posterbinding)
//            statusLove?.let { status ->
//                setIsLove(status)
//            }
//            var loveIs = detailbinding.floatingActionButton
//            loveIs.setOnClickListener {
//                setFavorite(movie, tvShow)
//            }
//        }
//    }

    private fun setIsLove(status: Boolean) {
        val loveicon = detailbinding.floatingActionButton
        if (status) {
            detailbinding.floatingActionButton.setImageResource(R.drawable.ic_love_red)
        } else {
            detailbinding.floatingActionButton.setImageResource(R.drawable.ic_love_white)
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

//    private fun setFavorite(movie: MovieEntity?, tvShow: TvEntity?) {
//        if (movie != null) {
//            if (movie.is_favorite) {
//                showSnackBar("${movie.title} Dihapus dari daftar favorite")
//            } else {
//                showSnackBar("${movie.title} Ditambah dari daftar favorite")
//            }
//            viewModel.setFavoriteMovie(movie)
//        } else {
//            if (tvShow != null) {
//                if (tvShow.is_favorite) {
//                    showSnackBar("${tvShow.title} Ditambah dari daftar favorite")
//                } else {
//                    showSnackBar("${tvShow.title} Dihapus dari daftar favorite")
//                }
//                viewModel.setFavoriteTv(tvShow)
//            }
//        }
//    }

//    private fun setupToolbar() {
//        setSupportActionBar(detail_toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT)
//    }

//    private fun setupToolbarTitle(title: String) {
//        supportActionBar?.title = title
//    }

    @JvmName("populateDataDetailForMovie")
    private fun populateDataDetailMov(movie: MovieEntity) {
        with(movie) {
            var titlebinding = detailbinding.tvTitle
            var genrebinding = detailbinding.tvGenre
            var releasebinding = detailbinding.tvYear
            var scorebinding = detailbinding.tvScore
            var overviewbinding = detailbinding.tvOverview
            var posterbinding = detailbinding.ivPoster

            titlebinding.text = movie.title
            genrebinding.text = "Release : ${movie.release_date}"
            releasebinding.text = "Vote Average : ${movie.vote_average}"
            scorebinding.text = "Popularity : ${movie.popularity}"
            overviewbinding.text = movie.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + movie.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)

//            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())
//
//            detailbinding.tvGenre.text = genreDurationText
//            detailBinding.collapsing.title = this.title
//            detailBinding.tvDetailOverview.text = this.overview
//
//            Glide.with(this@DetailActivity)
//                .asBitmap()
//                .load(IMAGE_URL + this.posterPath)
//                .into(object : CustomTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        detailBinding.ivDetail.setImageBitmap(resource)
//                        setColorByPalette(resource)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {}
//                })
//
//            Glide.with(this@DetailActivity)
//                .load(IMAGE_URL + this.backdropPath)
//                .into(detailBinding.ivBackdrop)
//
//            detailBinding.ivDetail.tag = this.posterPath
//            detailBinding.ivBackdrop.tag = this.backdropPath
//
            showProgressBar(false)
        }
    }

    @JvmName("populateDataDetailForTvShow")
    private fun populateDataDetailTv(tvShow: TvEntity) {
        with(tvShow) {
            var titlebinding = detailbinding.tvTitle
            var genrebinding = detailbinding.tvGenre
            var releasebinding = detailbinding.tvYear
            var scorebinding = detailbinding.tvScore
            var overviewbinding = detailbinding.tvOverview
            var posterbinding = detailbinding.ivPoster

            titlebinding.text = tvShow.title
            genrebinding.text = "Release : ${tvShow.release_date}"
            releasebinding.text = "Vote Average : ${tvShow.vote_average}"
            scorebinding.text = "Popularity : ${tvShow.popularity}"
            overviewbinding.text = tvShow.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + tvShow.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)
//            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())
//
//            detailBinding.tvDetailGenreDuration.text = genreDurationText
//            detailBinding.collapsing.title = this.name
//            detailBinding.tvDetailOverview.text = this.overview
//
//            Glide.with(this@DetailActivity)
//                .asBitmap()
//                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
//                .load(IMAGE_URL + this.posterPath)
//                .into(object : CustomTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        detailBinding.ivDetail.setImageBitmap(resource)
//                        setColorByPalette(resource)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {}
//                })
//
//            Glide.with(this@DetailActivity)
//                .load(IMAGE_URL + this.backdropPath)
//                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
//                .into(detailBinding.ivBackdrop)
//
//            detailBinding.ivDetail.tag = this.posterPath
//            detailBinding.ivBackdrop.tag = this.backdropPath
//
            showProgressBar(false)
        }
    }

    private fun onLoveClicked() {
        if (dataCategory == MOVIE) {
            viewModel.setFavoriteMovie()
        } else if (dataCategory == TV_SHOW) {
            viewModel.setFavoriteTvShow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionButton -> {
                onLoveClicked()
            }
        }
    }


}

