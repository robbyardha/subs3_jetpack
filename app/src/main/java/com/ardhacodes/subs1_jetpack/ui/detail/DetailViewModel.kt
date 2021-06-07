package com.ardhacodes.subs1_jetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity
import com.ardhacodes.subs1_jetpack.vo.Resource
import javax.inject.Inject

//class DetailViewModel(val modelMovTvRepository: MovTvRepository) : ViewModel() {
class DetailViewModel (private val movTvRepository: MovTvRepository) : ViewModel() {
    companion object {
        const val MOVIE = "movie"
        const val TV_SHOW = "tvShow"
    }
    private lateinit var detailtv: LiveData<Resource<TvEntity>>
    private lateinit var detailmov: LiveData<Resource<MovieEntity>>

//    private lateinit var mov_id: String
//    private lateinit var tv_id: String
//
//    fun setMovieById(movieId: String) {
//        this.mov_id = movieId
//    }
//
//    fun setTvShowById(tvShowId: String) {
//        this.tv_id = tvShowId
//    }

//    private fun getListMovie(): ArrayList<MovieTvEntity> = MoviesTvDataDummy.DataMovies() as ArrayList<MovieTvEntity>
//    private fun getListTvShow(): ArrayList<MovieTvEntity> = MoviesTvDataDummy.DataTvShow() as ArrayList<MovieTvEntity>


//    fun getMovieById(): MovieTvEntity? {
//        var result: MovieTvEntity? = null
//        val listMovie = getListMovie()
//        for (movie in listMovie){
//            if(movie.title == mov_id){
//                result = movie
//                break
//            }
//        }
//        return result
//    }
//
//
//    fun getTvShowById(): MovieTvEntity? {
//        var result: MovieTvEntity? = null
//        val listTvShow = getListTvShow()
//        for(tvshow in listTvShow){
//            if(tvshow.title == tv_id){
//                result = tvshow
//                break
//            }
//        }
//        return result
//    }

    fun setFilm(id: String, category: String) {
        when (category) {
            TV_SHOW -> {
                detailtv = movTvRepository.getTvDetail(id.toInt())
            }

            MOVIE -> {
                detailmov = movTvRepository.getMovieDetail(id.toInt())
            }
        }
    }

    fun setFavoriteMovie() {
        val resource = detailmov.value
        if (resource?.data != null) {
            val newState = resource.data!!.is_favorite
            movTvRepository.setFavoriteMovie(resource.data!!, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = detailtv.value
        if (resource?.data != null) {
            val newState = resource.data!!.is_favorite
            movTvRepository.setFavoriteTv(resource.data!!, newState)
        }
    }

    fun getDetailTvApis() = detailtv
    fun getDetailMovieApis() = detailmov

//    fun getDetailMovieapis(movieId: Int): LiveData<MovieEntity> {
//        return movTvRepository.getMovieDetail(movieId)
//    }
//
//    fun getDetailTvapis(tvShowId: Int): LiveData<TvEntity> {
//        return movTvRepository.getTvDetail(tvShowId)
//    }
//
//    fun setFavoriteMovie(movie: MovieEntity)  {
//        return movTvRepository.setFavoriteMovie(movie)
//    }
//
//    fun setFavoriteTv(tv: TvEntity){
//        return movTvRepository.setFavoriteTv(tv)
//    }
}