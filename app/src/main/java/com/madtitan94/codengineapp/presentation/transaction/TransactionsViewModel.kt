package com.madtitan94.codengineapp.presentation.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madtitan94.codengineapp.domain.model.Transaction
import com.madtitan94.codengineapp.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    private var _transactionsList = MutableStateFlow<List<Transaction>>(emptyList())

    fun getTransactions(): StateFlow<List<Transaction>> {
        return _transactionsList
    }

    init {
        getAllTransactions()
    }

    private fun getAllTransactions() {
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                _transactionsList.value = it
            }
        }
    }

}
