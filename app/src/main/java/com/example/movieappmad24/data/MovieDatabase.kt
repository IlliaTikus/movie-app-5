package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Movie::class, MovieImage::class],
    version = 15,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieImageDao(): MovieImageDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build()

                instance.also {
                    this.instance = it
                    CoroutineScope(Dispatchers.IO).launch {
                        seedDatabase(it)
                    }
                }
            }
        }

        private fun seedDatabase(database: MovieDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                val seed = getMovies()
                seed.forEach { movieWithImages ->
                    val movie = movieWithImages.movie
                    val images = movieWithImages.movieImages
                    val movieId = database.movieDao().add(movie)
                    images.forEach { img ->
                        img.movieId = movieId
                        database.movieImageDao().add(img)
                    }
                }
            }
        }
    }
}
//
//        private suspend fun seedMovies(movieDao: MovieDao) {
//            val movies = Movie.getMovieWithImages()
//            for (movie in movies) {
//                movieDao.add(movie)
//            }
//        }
//
//        private suspend fun seedMovieImages(movieImageDao: MovieImageDao) {
//            val movieImages = MovieImage.getMovieImages()
//            for (movieImage in movieImages) {
//                movieImageDao.add(movieImage)
//            }
//        }
//    }
//    }