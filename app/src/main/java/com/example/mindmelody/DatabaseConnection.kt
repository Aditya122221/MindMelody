package com.example.mindmelody

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseConnection(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        companion object {
                const val DB_NAME = "MoodTuneUserDB.db"
                const val DB_VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
                // Called only once when the database is first created
                val createUserTable = """
            CREATE TABLE Users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER UNIQUE,
                fullName TEXT,
                email TEXT UNIQUE,
                password TEXT,
                created_at TEXT
            )
        """.trimIndent()
                db.execSQL(createUserTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                // Called when DB_VERSION is increased
                db.execSQL("DROP TABLE IF EXISTS Users")
                onCreate(db)
        }
}