package com.misw.vinilos.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.misw.vinilos.data.model.Collector

@Entity
data class CollectorEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "telephone") val telephone: String,
    @ColumnInfo(name = "email") val email: String,
)

fun CollectorEntity.toDto() = Collector(
    id = id,
    name = name,
    telephone = telephone,
    email = email,
)

fun List<CollectorEntity>.toDto() = map { it.toDto() }
