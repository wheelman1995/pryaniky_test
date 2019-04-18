package ru.wheelman.pryaniky_test.model.network

import ru.wheelman.pryaniky_test.model.entities.Result

interface IRemoteDataSource {

    suspend fun getData(): Result
}
