package com.ardhacodes.subs1_jetpack.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardhacodes.subs1_jetpack.data.MovTvRepository
import com.ardhacodes.subs1_jetpack.data.MovieTvEntity
import com.ardhacodes.subs1_jetpack.utils.MoviesTvDataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest
{
    private lateinit var viewmodel: MovieViewModel
    val dataMov = MoviesTvDataDummy.DataMovies()
    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<MovieTvEntity>>

    @Mock
    private lateinit var catalogRepository: MovTvRepository


    @Before
    fun setData(){
        viewmodel = MovieViewModel(catalogRepository)
    }

    @Test
    fun getMoviePopular()
    {
        val movie = MutableLiveData<List<MovieTvEntity>>()
        movie.value = dataMov

        Mockito.`when`(catalogRepository.getPopularMovies()).thenReturn(movie)

        val dataListMovie = viewmodel.getDataMovieApi().value

        verify(catalogRepository).getPopularMovies()
        Assert.assertNotNull(dataListMovie)
        Assert.assertEquals(10, dataListMovie?.size)

        viewmodel.getDataMovieApi().observeForever(observer)
        verify(observer).onChanged(dataMov)
    }

//    @Test
//    fun getMovie(){
//        val movie = viewmodel.getdDataMovie()
//        val countdata = 10
//        assertNotNull(movie)
//        assertEquals(countdata, movie.size)
//    }
}