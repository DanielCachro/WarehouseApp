package com.example.warehouseadmin

data class Item(
    val sku: String,
    val name: String,
    val weight: String,
    val location: String,
    val updateDate: String
) {
    constructor() : this("", "", "", "", "")
}
