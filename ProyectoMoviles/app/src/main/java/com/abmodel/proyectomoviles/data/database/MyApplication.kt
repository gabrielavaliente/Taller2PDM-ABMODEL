package com.abmodel.proyectomoviles.data.database

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    companion object {
        lateinit var database: FamilyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            FamilyDatabase::class.java,
            "FamilyDatabase"
        ).build()

    }
}