package com.ardhacodes.subs1_jetpack.ui.detail

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.databinding.ActivityDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.databinding.ContentDetailMovieTvBinding
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_MOVIE
import com.ardhacodes.subs1_jetpack.utils.Helper.EXTRA_TV_SHOW
import com.ardhacodes.subs1_jetpack.utils.Helper.setImageGlide
import com.ardhacodes.subs1_jetpack.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMovieTvActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOV = "extra_mov"
        const val EXTRA_CATEGORY = "extra_cat"
    }

    private lateinit var detailbinding : ActivityDetailMovieTvBinding
    private lateinit var result: MovieTvEntity
    private lateinit var viewModel: DetailViewModel
    val path = "https://image.tmdb.org/t/p/"
    val image_w185 = "w185"
    val image_w780 = "w780"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailbinding = ActivityDetailMovieTvBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

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

    fun viewModelProviderConfig()
    {
        val factory = ViewModelFactory.getInstance()
        val viewmodel = ViewModelProvider(this@DetailMovieTvActivity, factory)[DetailViewModel::class.java]
        val ex_id_mov_tv = intent.getIntExtra(EXTRA_MOV, 0)
        val ex_category = intent.getStringExtra(EXTRA_CATEGORY)

        if(ex_category.equals(EXTRA_MOVIE, ignoreCase = true)){
            viewmodel.getDetailMovieapis(ex_id_mov_tv).observe(this,{
//                viewmodel.getDetailMovieapis(0)
                dataDetail(it)
            })
        }
        else if (ex_category.equals(EXTRA_TV_SHOW, ignoreCase = true)){
            viewmodel.getDetailTvapis(ex_id_mov_tv).observe(this,{
                dataDetail(it)
//                viewmodel.getDetailTvapis(0)
            })
        }
    }

    fun TitleActionBar()
    {
        val getTitle =intent.getStringExtra(EXTRA_MOV)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val actionbar = supportActionBar
        actionbar!!.title = "Detail ${getTitle}"
    }

    fun  dataDetail(result: MovieTvEntity)
    {
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
            .load(path+image_w185+result.poster_path)
            .apply(RequestOptions())
            .into(posterbinding)
    }
}