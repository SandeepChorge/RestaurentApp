package com.madtitan94.codengineapp.viewmodel

import android.text.Editable
import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.User
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.model.repository.UserRepository
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository) : ViewModel() {
    private val matchingUSer = MediatorLiveData<List<User>>()

    fun MatchingUSer(): LiveData<List<User>> {
        return matchingUSer
    }

    fun login(username:String, password: String) {

        matchingUSer.addSource(repository.login(username,password).asLiveData()){
                s -> matchingUSer.postValue(s)
        }
        }

}
class LoginViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}