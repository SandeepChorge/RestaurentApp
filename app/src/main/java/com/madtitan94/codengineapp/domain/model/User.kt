package com.madtitan94.codengineapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @field:PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val password: Int,
    val image: String,
    val isAdmin: Boolean
)