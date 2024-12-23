package com.example.warehouseadmin

data class Item(
    val sku: String,
    val name: String?=null,
    val weight: String?=null,
    val location: String?=null,
    val updateDate: String?=null
)
