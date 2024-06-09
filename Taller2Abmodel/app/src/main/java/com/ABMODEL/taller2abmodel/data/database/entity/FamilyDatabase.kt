package com.ABMODEL.taller2abmodel.data.database.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ABMODEL.taller2abmodel.data.database.dao.FamilyDAO
import com.ABMODEL.taller2abmodel.data.database.dao.PersonaDAO

@Database(entities = [Family::class, Persona::class], version = 2)
abstract class FamilyDatabase : RoomDatabase() {
    abstract fun familyDAO(): FamilyDAO
    abstract fun personaDAO(): PersonaDAO

    companion object {
        @Volatile
        private var INSTANCE: FamilyDatabase? = null

        fun getDatabase(context: Context): FamilyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FamilyDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Family ADD COLUMN latitude DOUBLE")
                db.execSQL("ALTER TABLE Family ADD COLUMN longitude DOUBLE")
            }
        }
    }
}
