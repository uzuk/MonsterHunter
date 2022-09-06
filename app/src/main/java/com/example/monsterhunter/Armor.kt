package com.example.monsterhunter

data class Armor(
    val name: String? = null,
    val rank: String? = null,
    val defense: Defense? = null,
    val slots: List<Slot>? = null,
    val type: String? = null
)

data class Defense(
    val base: Int? = null,
    val max: Int? = null,
    val augmented: Int? = null
)

data class Slot(
    val rank: String? = null
)