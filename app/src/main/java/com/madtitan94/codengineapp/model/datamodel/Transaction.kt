package com.madtitan94.codengineapp.model.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @field:PrimaryKey
    val id: Int,
    val transactionId: String,
    val subTotal: Double,
    val Total: Double,
    val Tax: Double,
    val status: String,
    val orderTime: String,
    val firstName: String,
    val lastName: String,
    val mobileNo: String,
    val emailId: String
)