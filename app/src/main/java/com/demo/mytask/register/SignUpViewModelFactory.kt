package com.demo.mytask.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.mytask.database.UserRepository
import java.lang.IllegalArgumentException

class SignUpViewModelFactory(
    private  val repository: UserRepository,
    private val application: Application):ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
           return SignUpViewModel(repository, application) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}