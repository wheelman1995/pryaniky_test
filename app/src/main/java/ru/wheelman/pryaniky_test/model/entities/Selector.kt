package ru.wheelman.pryaniky_test.model.entities

data class Selector(
    val name: String,
    val data: SelectorData
) {

    data class SelectorData(val selectedId: Int, val variants: List<SelectorVariant>)
    data class SelectorVariant(val id: Int, val text: String)
}