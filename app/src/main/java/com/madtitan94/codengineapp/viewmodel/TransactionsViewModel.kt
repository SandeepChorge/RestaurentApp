package com.madtitan94.codengineapp.viewmodel

import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.Transaction
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.model.repository.TransactionRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsViewModel(private val repository: TransactionRepository) : ViewModel() {

   /* private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text*/

    private val _transactionsList = MediatorLiveData<List<Transaction>>()

    fun getTransactions(): LiveData<List<Transaction>> {
        return _transactionsList
    }

    init {
        getAllTransactions()
    }

    fun getAllTransactions() {
        CoroutineScope(Dispatchers.Main).launch {
        _transactionsList.addSource(repository.getAllTransactions().asLiveData()){
                tran -> _transactionsList.postValue(tran)
        }
        }
    }

}

class TransactionViewModelFactory(private val repository: TransactionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
