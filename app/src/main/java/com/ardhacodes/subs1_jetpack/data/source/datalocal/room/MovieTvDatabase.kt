package com.ardhacodes.subs1_jetpack.data.source.datalocal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardhacodes.subs1_jetpack.data.source.datalocal.MovieEntity
import com.ardhacodes.subs1_jetpack.data.source.datalocal.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1, exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun MovieTvDao() : MovieTvDao

    companion object
    {
        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase = INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvDatabase::class.java,
                    "Movietv.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}