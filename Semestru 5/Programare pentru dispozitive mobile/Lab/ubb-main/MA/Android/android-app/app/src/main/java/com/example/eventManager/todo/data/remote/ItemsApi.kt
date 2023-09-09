package com.example.eventManager.todo.data.remote

import com.example.eventManager.core.Api
import com.example.eventManager.todo.data.Item
import retrofit2.http.*

object ItemsApi {
    interface Service {
//        @GET("api/bookRow")
//        suspend fun find(): List<Item>

        @GET("/item?status=active")
        suspend fun find(): List<Item>

        @GET("/api/bookRow/{id}")
        suspend fun read(@Path("id") specialEventId: String): Item;

        @Headers("Content-Type: application/json")
        @POST("/api/bookRow")
        suspend fun create(@Body specialEvent: Item): Item

        @Headers("Content-Type: application/json")
        @PUT("/api/bookRow/{id}")
        suspend fun update(@Path("id") specialEventId: String, @Body specialEvent: Item): Item
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}