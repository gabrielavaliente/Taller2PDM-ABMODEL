package com.ABMODEL.taller2abmodel.data.database

import android.app.Application
import androidx.room.Room
import com.ABMODEL.taller2abmodel.data.database.entity.FamilyDatabase

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