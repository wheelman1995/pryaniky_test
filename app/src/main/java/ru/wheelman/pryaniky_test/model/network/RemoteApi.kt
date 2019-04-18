package ru.wheelman.pryaniky_test.model.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RemoteApi {

    @GET("/tmp/JSONSample.json")
    fun getData(): Deferred<String>
}