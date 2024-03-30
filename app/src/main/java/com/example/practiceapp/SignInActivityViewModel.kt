package com.example.practiceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practiceapp.data2.User

class SignInActivityViewModel: ViewModel() {
        private val _user : MutableLiveData<User> = MutableLiveData(null)
        val user: LiveData<User> get() = _user

        private val _saveRequest = MutableLiveData<Boolean>()
        val saveRequest: LiveData<Boolean> get() = _saveRequest

        fun updateUser(user: User) {
            _user.value = user
        }

        fun triggerSave() {
            _saveRequest.value = true
        }
        fun triggerSaveComplete() {
            _saveRequest.value = false
        }
}