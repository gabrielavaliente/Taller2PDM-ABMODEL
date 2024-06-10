package com.ABMODEL.myapplication.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Family")
data class Family(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "family_id")
    val family_id: Int = 0,

    @ColumnInfo(name = "family_name")
    val family_name: String = "",

    @ColumnInfo(name = "community")
    val community: String = "",

    @ColumnInfo(name = "houseType")
    val houseType: String = "",

    @ColumnInfo(name = "risk")
    val risk: String = "",

)