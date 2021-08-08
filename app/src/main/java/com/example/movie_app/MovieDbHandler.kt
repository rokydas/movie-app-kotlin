package com.example.movie_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.movie_app.models.movies

class MovieDbHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "favoriteMovieDatabase"
        private val TABLE_CONTACTS = "favoriteMovie"
        private val KEY_ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_POSTER_PATH = "poster_path"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_POSTER_PATH + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    fun addFavoriteMovie(movie: movies):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_ID, movie.id)
        contentValues.put(KEY_TITLE, movie.title)
        contentValues.put(KEY_POSTER_PATH, movie.poster_path)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun viewFavoriteMovies():List<movies>{
        var favoriteMovieList:List<movies> = listOf<movies>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return favoriteMovieList
        }
        var id: Int
        var title: String
        var poster_path: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                poster_path = cursor.getString(cursor.getColumnIndex("poster_path"))
                val movie = movies(id = id, title = title, poster_path = poster_path)
                favoriteMovieList += movie
            } while (cursor.moveToNext())
        }
        return favoriteMovieList
    }

    fun deleteEmployee(id: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_ID, id)
        val success = db.delete(TABLE_CONTACTS,"id="+id,null)

        db.close()
        return success
    }
}