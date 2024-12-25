package com.example.warehouseapp

data class Item(
    val sku: String,
    val name: String,
    val weight: String,
    val location: String,
    val updateDate: String
) {
    constructor() : this("", "", "", "", "")
}
