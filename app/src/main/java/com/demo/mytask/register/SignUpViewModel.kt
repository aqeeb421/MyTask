package com.demo.mytask.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.demo.mytask.database.UserEntity
import com.demo.mytask.database.UserRepository
import kotlinx.coroutines.*


class SignUpViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application), Observable {

    private val TAG = "SignUpViewModel"

    init {
        Log.i(TAG, "init")
    }


    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<UserEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String>()

    @Bindable
    val inputLastName = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun sumbitButton() {
        Log.i(TAG, "Inside SUBMIT BUTTON")
        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                Log.i(TAG, usersNames.toString() + "------------------")
                if (usersNames != null) {
                    _errorToastUsername.value = true
                    Log.i(TAG, "Inside if Not null")
                } else {
                    Log.i(TAG, userDetailsLiveData.value.toString() + "ASDFASDFASDFASDF")
                    Log.i(TAG, "OK im in")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputUsername.value!!
                    val password = inputPassword.value!!
                    Log.i(TAG, "insidi Sumbit")
                    insert(UserEntity(0, firstName, lastName, email, password))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateto.value = true
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i(TAG, "Done navigating ")
    }

    fun donetoast() {
        _errorToast.value = false
        Log.i(TAG, "Done taoasting ")
    }

    fun cancelButton() {
        _navigateto.value = true
    }

    fun donetoastUserName() {
        _errorToast.value = false
        Log.i(TAG, "Done taoasting  username")
    }

    private fun insert(user: UserEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    fun clearALl(): Job = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}





