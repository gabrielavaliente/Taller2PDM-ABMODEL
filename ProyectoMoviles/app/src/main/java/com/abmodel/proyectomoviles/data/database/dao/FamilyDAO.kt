package com.abmodel.proyectomoviles.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abmodel.proyectomoviles.data.database.entity.Family

@Dao
interface FamilyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamily(family: Family)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamilies(families: List<Family>)

    @Query("SELECT * FROM Family")
    suspend fun getAllFamilies(): List<Family>
}
