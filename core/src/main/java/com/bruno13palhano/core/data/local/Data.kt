package com.bruno13palhano.core.data.local

import kotlinx.coroutines.flow.Flow

interface Data<T> {
    fun insert(model: T)
    fun update(model: T)
    fun delete(id: Long)
    fun getAll(): Flow<List<T>>
    fun getLastInsertId(): Long
}