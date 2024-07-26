package com.madtitan94.codengineapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madtitan94.codengineapp.domain.model.User
import com.madtitan94.codengineapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var _matchingUser = MutableStateFlow<List<User>>(emptyList())
    val matchingUSer : StateFlow<List<User>> = _matchingUser

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.login(username, password).collect{
                _matchingUser.value = it
            }
        }
    }

}