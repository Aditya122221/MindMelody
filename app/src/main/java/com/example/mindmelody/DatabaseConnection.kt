package com.example.mindmelody

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class User(
        val userId: Int,
        val name: String,
        val email: String,
        val password: String,
        val created_at: String
)

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

        fun isUserIdExists(userId: Int): Boolean {
                val db = this.readableDatabase
                val cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(userId.toString()))
                val exists = cursor.moveToFirst()
                cursor.close()
                return exists
        }

        fun getUserByEmail(email: String): User? {
                val db = this.readableDatabase
                val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))

                var user: User? = null
                if (cursor.moveToFirst()) {
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("userId"))
                        val name = cursor.getString(cursor.getColumnIndexOrThrow("fullName"))
                        val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                        val created = cursor.getString(cursor.getColumnIndexOrThrow("created_at"))

                        user = User(id, name, email, password, created)
                }

                cursor.close()
                return user
        }

        fun deleteUserByEmail(email: String): Boolean {
                val db = this.writableDatabase
                val result = db.delete("users", "email = ?", arrayOf(email))
                db.close()
                return result > 0  // returns true if at least one row was deleted
        }


        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                // Called when DB_VERSION is increased
                db.execSQL("DROP TABLE IF EXISTS Users")
                onCreate(db)
        }
}