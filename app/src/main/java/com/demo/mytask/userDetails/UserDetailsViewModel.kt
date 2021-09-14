package com.demo.mytask.userDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.mytask.database.UserEntity
import com.demo.mytask.database.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application) {

    private val TAG = "UserDetailsViewModel"

    val users = repository.users

    fun delete(user: UserEntity): Job = viewModelScope.launch {
        repository.deleteUser(user)
    }

    init {
        Log.i(TAG, "inside_users_Lisrt_init")
    }


    private val _navigateto = MutableLiveData<Boolean>()

    private val _navigatetoRegister = MutableLiveData<Boolean>()

    val navigatetoRegister: LiveData<Boolean>
        get() = _navigatetoRegister

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating() {
        _navigateto.value = false
    }

    fun backButtonclicked() {
        _navigateto.value = true
    }

    fun addUserButtonClicked() {
        _navigatetoRegister.value = true
    }

    fun doneNavigatingRegiter() {
        _navigatetoRegister.value = false
    }


}