package com.abmodel.proyectomoviles.data.network

import com.abmodel.proyectomoviles.data.database.entity.Family
import com.abmodel.proyectomoviles.data.database.entity.Persona
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("postfamily")
    suspend fun postFamily(@Body family: Family): Response<Void>

    @POST("postperson")
    suspend fun postPerson(@Body persona: Persona): Response<Void>

    @GET("getallfamilies")
    suspend fun getAllFamilies(): Response<List<Family>>

    @POST("/api/migratefamilies")
    suspend fun migrateFamilies(@Body families: List<Family>): Response<Unit>

    @POST("/migrate")
    suspend fun migrateData(
        @Body data: DataMigrationRequest
    ): Response<Unit>

    data class DataMigrationRequest(
        val families: List<Family>,
        val personas: List<Persona>
    )

}