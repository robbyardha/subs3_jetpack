package com.ardhacodes.subs1_jetpack.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.entity.TvEntity
import com.ardhacodes.subs1_jetpack.databinding.ActivityDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.databinding.ContentDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.MOVIE_VIEWMDL
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel.Companion.TV_VIEWMDL
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

class DetailMovieTvActivity : AppCompatActivity(),View.OnClickListener {
    companion object {
        const val EXTRA_MOV = "extra_mov"
        const val EXTRA_CATEGORY = "extra_cat"
    }

    private lateinit var detailbinding: ActivityDetailMovieTvBinding
    private lateinit var viewModel: DetailViewModel
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"
    private var dataCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailbinding = ActivityDetailMovieTvBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

        supportActionBar?.hide()
        viewModelProviderConfig()

//        Declaration Binding
//        var titlebinding = detailbinding.tvTitle
//        var genrebinding = detailbinding.tvGenre
//        var releasebinding = detailbinding.tvYear
//        var scorebinding = detailbinding.tvScore
//        var durationbinding = detailbinding.tvDuration
//        var overviewbinding = detailbinding.tvOverview
//        var posterbinding = detailbinding.ivPoster

        //Load
//        titlebinding.text = result.title
//        genrebinding.text = result.genre
//        releasebinding.text = "Release : ${result.yearrelease}"
//        scorebinding.text = "Score : ${result.score}"
//        durationbinding.text = "Duration : ${result.duration}"
//        durationbinding.text = result.duration
//        overviewbinding.text = result.overview
        //load Image using Glide
//        Glide.with(this@DetailMovieTvActivity)
//                .load(result.poster)
//                .apply(RequestOptions())
//                .into(posterbinding)

//        Load image using helper
        //        setImageGlide(this@DetailMovieTvActivity, result.poster, detailbinding.ivPoster)

//        Databinding quick
//        detailbinding.tvTitle.text = result.title
//        detailbinding.tvGenre.text = result.genre
//        detailbinding.tvYear.text = "Release : ${result.yearrelease}"
//        detailbinding.tvScore.text = "Score : ${result.score}"
//        detailbinding.tvDuration.text = "Duration : ${result.duration}"
//        detailbinding.tvOverview.text = result.overview


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun viewModelProviderConfig() {
        val factory = ViewModelFactory.getInstance(this)
        val viewmodel =
            ViewModelProvider(this@DetailMovieTvActivity, factory)[DetailViewModel::class.java]
        detailbinding.floatingActionFavorite.setOnClickListener(this)

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_MOV)
            dataCategory = extras.getString(EXTRA_CATEGORY)

            if (id != null && dataCategory != null) {
                viewModel.setMovTvCategory(id, dataCategory.toString())

                setupState()

                if (dataCategory == MOVIE_VIEWMDL) {
                    viewModel.getDetailMovieapis().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetail(detail.data!!)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                } else if (dataCategory == TV_VIEWMDL) {
                    viewModel.getDetailTvapis().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetail(detail.data!!)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }
    }


    private fun setupState() {
        if (dataCategory == MOVIE_VIEWMDL) {
            viewModel.getDetailMovieapis().observe(this, { movie ->
                when (movie.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (movie.data != null) {
                            showProgressBar(false)
                            val state = movie.data!!.is_favorite
                            setFavoriteState(state)
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        } else if (dataCategory == TV_VIEWMDL) {
            viewModel.getDetailTvapis().observe(this, { tvShow ->
                when (tvShow.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (tvShow.data != null) {
                            showProgressBar(false)
                            val state = tvShow.data!!.is_favorite
                            setFavoriteState(state)
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    private fun showProgressBar(state: Boolean) {
        detailbinding.progressBar.isVisible = state
//        detailbinding.appbar.isInvisible = state
//        detailbinding.nestedScrollView.isInvisible = state
        detailbinding.floatingActionFavorite.isInvisible = state
    }

    private fun setFavoriteState(state: Boolean) {
        val fab = detailbinding.floatingActionFavorite
        if (state) {
            fab.setImageResource(R.drawable.ic_love_red)
        } else {
            fab.setImageResource(R.drawable.ic_love_white)
        }
    }

    @JvmName("populateDataDetailForMovie")
    private fun populateDataDetail(movie: MovieEntity) {
        with(movie) {
//            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())

//            detailbinding.tvDetailGenreDuration.text = genreDurationText
//            detailbinding.collapsing.title = this.name
            var titlebinding = detailbinding.tvTitle
            var genrebinding = detailbinding.tvGenre
//        var genrebinding = detailbinding.tvGenre
            var releasebinding = detailbinding.tvYear
            var scorebinding = detailbinding.tvScore
//        var durationbinding = detailbinding.tvDuration
            var overviewbinding = detailbinding.tvOverview
            var posterbinding = detailbinding.ivPoster

            titlebinding.text = this.title
            genrebinding.text = "Release : ${this.release_date}"
            releasebinding.text = "Vote Average : ${this.vote_average}"
            scorebinding.text = "Popularity : ${this.popularity}"
            overviewbinding.text = this.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + this.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)

            showProgressBar(false)
        }
    }

    @JvmName("populateDataDetailForTvShow")
    private fun populateDataDetail(tvShow: TvEntity) {
        with(tvShow) {
//            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())

//            detailbinding.tvDetailGenreDuration.text = genreDurationText
//            detailbinding.collapsing.title = this.name
            var titlebinding = detailbinding.tvTitle
            var genrebinding = detailbinding.tvGenre
//        var genrebinding = detailbinding.tvGenre
            var releasebinding = detailbinding.tvYear
            var scorebinding = detailbinding.tvScore
//        var durationbinding = detailbinding.tvDuration
            var overviewbinding = detailbinding.tvOverview
            var posterbinding = detailbinding.ivPoster

            titlebinding.text = this.title
            genrebinding.text = "Release : ${this.release_date}"
            releasebinding.text = "Vote Average : ${this.vote_average}"
            scorebinding.text = "Popularity : ${this.popularity}"
            overviewbinding.text = this.overview
            Glide.with(this@DetailMovieTvActivity)
                .load(path + image_w185 + this.poster_path)
                .apply(RequestOptions())
                .into(posterbinding)

            showProgressBar(false)
        }
    }

    fun TitleActionBar() {
        val getTitle = intent.getStringExtra(EXTRA_MOV)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val actionbar = supportActionBar
        actionbar!!.title = "Detail ${getTitle}"
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

    private fun onFabClicked() {
        if (dataCategory == MOVIE_VIEWMDL) {
            viewModel.setterFavMov()
        } else if (dataCategory == TV_VIEWMDL) {
            viewModel.setterFavTv()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionFavorite -> {
                onFabClicked()
            }
        }
    }
}