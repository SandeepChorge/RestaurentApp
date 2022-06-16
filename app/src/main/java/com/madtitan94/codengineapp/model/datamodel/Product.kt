package com.madtitan94.codengineapp.model.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @field:PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val category: String
)