package com.bruno13palhano.core.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import cache.HistoryTableQueries
import com.bruno13palhano.core.di.AppDispatcher.IO
import com.bruno13palhano.core.di.Dispatcher
import com.bruno13palhano.core.di.IOScope
import com.bruno13palhano.core.model.History
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HistoryLocalData @Inject constructor(
    private val historyQueries: HistoryTableQueries,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher,
    @IOScope private val ioScope: CoroutineScope
) : Data<History> {

    override fun insert(model: History) {
        ioScope.launch {
            historyQueries.insert(
                participant = model.participant,
                textContent = model.textContent,
                isPending = model.isPending
            )
        }
    }

    override fun update(model: History) {
        ioScope.launch {
            historyQueries.update(
                participant = model.participant,
                textContent = model.textContent,
                isPending = model.isPending,
                id = model.id
            )
        }
    }

    override fun delete(id: Long) {
        ioScope.launch {
            historyQueries.delete(id)
        }
    }

    override fun getAll(): Flow<List<History>> {
        return historyQueries.getAll(mapper = ::mapToHistory)
            .asFlow()
            .mapToList(context = dispatcher)
    }

    override fun getLastInsertId(): Long {
        return historyQueries.lastInsertId().executeAsOne()
    }

    private fun mapToHistory(
        id: Long,
        participant: String,
        textContent: String,
        isPending: Boolean
    ) = History(
        id = id,
        participant = participant,
        textContent = textContent,
        isPending = isPending
    )
}