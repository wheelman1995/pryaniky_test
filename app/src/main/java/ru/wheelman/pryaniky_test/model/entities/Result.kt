package ru.wheelman.pryaniky_test.model.entities

sealed class Result {
    data class Success(val data: Data, val view: List<DataType>) : Result()
    data class Failure(val error: String) : Result()
}