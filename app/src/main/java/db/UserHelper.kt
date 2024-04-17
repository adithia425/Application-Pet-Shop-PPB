package db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import java.sql.SQLException

class UserHelper (context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: UserHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
                }

     }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
        }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
     }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.NoteColumns._ID} ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.NoteColumns._ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }


    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
        }
}