package ru.wheelman.pryaniky_test.model.entities

data class Picture(val name: String, val data: PictureData) {

    data class PictureData(val url: String, val text: String)
}