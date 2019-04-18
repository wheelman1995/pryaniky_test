package ru.wheelman.pryaniky_test.model.entities

data class Hz(val name: String, val data: HzData) {

    data class HzData(val text: String)
}