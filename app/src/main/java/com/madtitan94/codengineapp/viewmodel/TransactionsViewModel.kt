package com.madtitan94.codengineapp.viewmodel

import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.Transaction
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.model.repository.TransactionRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    private lateinit var _transactionsList: LiveData<List<Transaction>>

    fun getTransactions(): LiveData<List<Transaction>> {
        return _transactionsList
    }

    init {
        getAllTransactions()
    }

    private fun getAllTransactions() = viewModelScope.launch {
        _transactionsList = repository.getAllTransactions().asLiveData()
    }

}
