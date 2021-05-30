package com.ardhacodes.subs1_jetpack.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import androidx.lifecycle.Observer
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest
{
    private lateinit var viewmodel: DetailViewModel
    val dataMov = MoviesTvDataDummy.DataMovies()[0]
    val dataTv = MoviesTvDataDummy.DataTvShow()[0]
    val movieId = dataMov.id
    val tvShowId = dataTv.id
    @Mock
    private lateinit var catalogRepository: MovTvRepository

    @Mock
    private lateinit var observer: Observer<MovieTvEntity>

    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Before
    fun setDataMovie() {
//        lateinit var catalogRepository: CatalogRepository
        viewmodel = DetailViewModel(catalogRepository)
//        viewmodel.setMovieById(movieId)
//        viewmodel.setTvShowById(tvShowId)
    }



    @Test
    fun getMov()
    {
        val movie = MutableLiveData<MovieTvEntity>()
        movie.value = dataMov

        `when`(catalogRepository.getMovieDetail(movieId)).thenReturn(movie)
        val movEntity = viewmodel.getDetailMovieapis(movieId).value as MovieTvEntity

        assertNotNull(movEntity)
        assertEquals(dataMov.id, movEntity.id)
        assertEquals(dataMov.title, movEntity.title)
        assertEquals(dataMov.release_date, movEntity.release_date)
        assertEquals(dataMov.popularity, movEntity.popularity)
        assertEquals(dataMov.overview, movEntity.overview)
        assertEquals(dataMov.vote_average, movEntity.vote_average)
        assertEquals(dataMov.poster_path, movEntity.poster_path)
        viewmodel.getDetailMovieapis(movieId).observeForever(observer)
        verify(observer).onChanged(dataMov)
    }


    @Test
    fun getTv()
    {
        val tv = MutableLiveData<MovieTvEntity>()
        tv.value = dataTv

        `when`(catalogRepository.getTvDetail(tvShowId)).thenReturn(tv)
        val tvEntity = viewmodel.getDetailTvapis(tvShowId).value as MovieTvEntity

        assertNotNull(tvEntity)
        assertEquals(dataTv.id, tvEntity.id)
        assertEquals(dataTv.title, tvEntity.title)
        assertEquals(dataTv.release_date, tvEntity.release_date)
        assertEquals(dataTv.popularity, tvEntity.popularity)
        assertEquals(dataTv.overview, tvEntity.overview)
        assertEquals(dataTv.vote_average, tvEntity.vote_average)
        assertEquals(dataTv.poster_path, tvEntity.poster_path)
        viewmodel.getDetailTvapis(tvShowId).observeForever(observer)
        verify(observer).onChanged(dataTv)
    }

    @Test
    fun getAllData()
    {
        val movie = MutableLiveData<MovieTvEntity>()
        movie.value = dataMov

        `when`(catalogRepository.getMovieDetail(movieId)).thenReturn(movie)
        val movEntity = viewmodel.getDetailMovieapis(movieId).value as MovieTvEntity

        assertNotNull(movEntity)
        assertEquals(dataMov.id, movEntity.id)
        assertEquals(dataMov.title, movEntity.title)
        assertEquals(dataMov.release_date, movEntity.release_date)
        assertEquals(dataMov.popularity, movEntity.popularity)
        assertEquals(dataMov.overview, movEntity.overview)
        assertEquals(dataMov.vote_average, movEntity.vote_average)
        assertEquals(dataMov.poster_path, movEntity.poster_path)
        viewmodel.getDetailMovieapis(movieId).observeForever(observer)
        verify(observer).onChanged(dataMov)

        val tv = MutableLiveData<MovieTvEntity>()
        tv.value = dataTv

        `when`(catalogRepository.getTvDetail(tvShowId)).thenReturn(tv)
        val tvEntity = viewmodel.getDetailTvapis(tvShowId).value as MovieTvEntity

        assertNotNull(tvEntity)
        assertEquals(dataTv.id, tvEntity.id)
        assertEquals(dataTv.title, tvEntity.title)
        assertEquals(dataTv.release_date, tvEntity.release_date)
        assertEquals(dataTv.popularity, tvEntity.popularity)
        assertEquals(dataTv.overview, tvEntity.overview)
        assertEquals(dataTv.vote_average, tvEntity.vote_average)
        assertEquals(dataTv.poster_path, tvEntity.poster_path)
        viewmodel.getDetailTvapis(tvShowId).observeForever(observer)
        verify(observer).onChanged(dataTv)
    }

//    old data

//    @Test
//    fun getData()
//    {
//        val movie = viewmodel.getMovieById()
//        val tvShow = viewmodel.getTvShowById()
//
//        assertNotNull(movie)
//        assertEquals(dataMov.title, movie?.title)
//        assertEquals(dataMov.release_date, movie?.release_date)
//        assertEquals(dataMov.popularity, movie?.popularity)
//        assertEquals(dataMov.overview, movie?.overview)
//        assertEquals(dataMov.vote_average, movie?.vote_average)
//        assertEquals(dataMov.poster_path, movie?.poster_path)
//
//        assertNotNull(tvShow)
//        assertEquals(dataTv.title, tvShow?.title)
//        assertEquals(dataTv.release_date, tvShow?.release_date)
//        assertEquals(dataTv.popularity, tvShow?.popularity)
//        assertEquals(dataTv.overview, tvShow?.overview)
//        assertEquals(dataTv.vote_average, tvShow?.vote_average)
//        assertEquals(dataTv.poster_path, tvShow?.poster_path)
//    }

//    @Test
//    fun getDataMov()
//    {
//        val movie = viewmodel.getMovieById()
//        assertNotNull(movie)
//        assertEquals(dataMov.title, movie?.title)
//        assertEquals(dataMov.genre, movie?.genre)
//        assertEquals(dataMov.yearrelease, movie?.yearrelease)
//        assertEquals(dataMov.poster, movie?.poster)
//        assertEquals(dataMov.score, movie?.score)
//        assertEquals(dataMov.duration, movie?.duration)
//        assertEquals(dataMov.overview, movie?.overview)
//    }
//    @Test
//    fun getDataTv()
//    {
//        val tvShow = viewmodel.getTvShowById()
//        assertNotNull(tvShow)
//        assertEquals(dataTv.title, tvShow?.title)
//        assertEquals(dataTv.genre, tvShow?.genre)
//        assertEquals(dataTv.yearrelease, tvShow?.yearrelease)
//        assertEquals(dataTv.poster, tvShow?.poster)
//        assertEquals(dataTv.score, tvShow?.score)
//        assertEquals(dataTv.duration, tvShow?.duration)
//        assertEquals(dataTv.overview, tvShow?.overview)
//    }

//    @Test
//    fun getData() {
//        val movie = viewmodel.getMovieById()
//        val tvShow = viewmodel.getTvShowById()
//        assertNotNull(tvShow)
//        assertNotNull(movie)
//        if (movie != null) {
//            assertEquals(dataMov.title, movie.title)
//            assertEquals(dataMov.genre, movie.genre)
//            assertEquals(dataMov.yearrelease, movie.yearrelease)
//            assertEquals(dataMov.poster, movie.poster)
//            assertEquals(dataMov.score, movie.score)
//            assertEquals(dataMov.duration, movie.duration)
//            assertEquals(dataMov.overview, movie.overview)
//        }
//        if (tvShow != null) {
//            assertEquals(dataTv.title, tvShow.title)
//            assertEquals(dataTv.genre, tvShow.genre)
//            assertEquals(dataTv.yearrelease, tvShow.yearrelease)
//            assertEquals(dataTv.poster, tvShow.poster)
//            assertEquals(dataTv.score, tvShow.score)
//            assertEquals(dataTv.duration, tvShow.duration)
//            assertEquals(dataTv.overview, tvShow.overview)
//        }
//    }
}
