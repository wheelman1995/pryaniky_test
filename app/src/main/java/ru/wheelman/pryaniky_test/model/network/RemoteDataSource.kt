package ru.wheelman.pryaniky_test.model.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.wheelman.pryaniky_test.logd
import ru.wheelman.pryaniky_test.model.entities.Result
import ru.wheelman.pryaniky_test.model.entities.Result.Failure
import ru.wheelman.pryaniky_test.model.network.RemoteDataMapper.mapRawData

class RemoteDataSource(private val remoteApi: RemoteApi) : IRemoteDataSource {

    override suspend fun getData(): Result = withContext(Dispatchers.IO) {
        try {
            val data = remoteApi.getData().await()
            logd(data)
            val result = mapRawData(data)
            result
        } catch (e: Exception) {
            Failure(e.message ?: "Unknown error")
            throw e
        }
    }
}