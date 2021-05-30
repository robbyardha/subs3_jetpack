package com.ardhacodes.subs1_jetpack.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.ui.detail.DetailViewModel
import com.ardhacodes.subs1_jetpack.ui.movie.MovieViewModel
import com.ardhacodes.subs1_jetpack.ui.tv.TvViewModel
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest
{

    private lateinit var viewmodeldetail: DetailViewModel
    private lateinit var viewmodelmov: MovieViewModel
    private lateinit var viewmodeltv: TvViewModel
//    val dataMov = MoviesTvDataDummy.DataMovies()[0]
//    val dataTv = MoviesTvDataDummy.DataTvShow()[0]
    val dataMov = MoviesTvDataDummy.DataMovies()
    val dataTv = MoviesTvDataDummy.DataTvShow()

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepos: MovTvRepository

    @Mock
    private lateinit var observer: Observer<List<MovieTvEntity>>

    @Before
    fun setData()
    {
        viewmodelmov = MovieViewModel(catalogRepos)
        viewmodelmov.getdDataMovie()

        viewmodeltv = TvViewModel(catalogRepos)
        viewmodeltv.getdDataTv()


        viewmodeldetail = DetailViewModel(catalogRepos)
//        viewmodeldetail.setMovieById(movieId)
//        viewmodeldetail.setTvShowById(tvShowId)
    }

    @Test
    fun getPopularMovie()
    {
        val movie = MutableLiveData<List<MovieTvEntity>>()
        movie.value = dataMov

        Mockito.`when`(catalogRepos.getPopularMovies()).thenReturn(movie)

        val dataListMovie = viewmodelmov.getDataMovieApi().value

        verify(catalogRepos).getPopularMovies()
        Assert.assertNotNull(dataListMovie)
        Assert.assertEquals(10, dataListMovie?.size)

        viewmodelmov.getDataMovieApi().observeForever(observer)
        verify(observer).onChanged(dataMov)
    }

    @Test
    fun getTvShowed()
    {
        val tv = MutableLiveData<List<MovieTvEntity>>()
        tv.value = dataTv

        Mockito.`when`(catalogRepos.getTv()).thenReturn(tv)

        val dataListTv = viewmodeltv.getDataTvAPI().value

        verify(catalogRepos).getTv()
        Assert.assertNotNull(dataListTv)
        Assert.assertEquals(11, dataListTv?.size)

        viewmodeltv.getDataTvAPI().observeForever(observer)
        verify(observer).onChanged(dataListTv)
    }

    @Test
    fun getListMovieItem() {
        val movies = viewmodelmov.getdDataMovie()
        TestCase.assertNotNull(movies)
        assertEquals(10, movies.size)
    }

    @Test
    fun getListTvShowItem() {
        val tvShows = viewmodeltv.getdDataTv()
        TestCase.assertNotNull(tvShows)
        assertEquals(11, tvShows.size)
    }

//    @Test
//    fun getNotNull()
//    {
//        val movie = viewmodeldetail.getMovieById()
//        val tvShow = viewmodeldetail.getTvShowById()
//
//        assertNotNull(movie)
//        assertEquals(dataMov.id, movie?.id)
//        assertEquals(dataMov.title, movie?.title)
//        assertEquals(dataMov.release_date, movie?.release_date)
//        assertEquals(dataMov.popularity, movie?.popularity)
//        assertEquals(dataMov.overview, movie?.overview)
//        assertEquals(dataMov.vote_average, movie?.vote_average)
//        assertEquals(dataMov.poster_path, movie?.poster_path)
//
//        assertNotNull(tvShow)
//        assertEquals(dataTv.id, tvShow?.id)
//        assertEquals(dataTv.title, tvShow?.title)
//        assertEquals(dataTv.release_date, tvShow?.release_date)
//        assertEquals(dataTv.popularity, tvShow?.popularity)
//        assertEquals(dataTv.overview, tvShow?.overview)
//        assertEquals(dataTv.vote_average, tvShow?.vote_average)
//        assertEquals(dataTv.poster_path, tvShow?.poster_path)
//    }
}
