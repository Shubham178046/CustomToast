package com.example.customtoast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customtoast.util.CommonUtil

class UserViewModel : ViewModel() {
    private val _uiState = MutableLiveData<Int>(-1)
    val uiState: LiveData<Int>
        get() = _uiState

    private val _uiErrorMsg = MutableLiveData<String>("")
    val uiErrorMsg: LiveData<String>
        get() = _uiErrorMsg

    fun checkValidation(email: String, password: String) {
        if (!invalidInput(email, password)) {
            if (!CommonUtil.emailValidation(email)) {
                _uiErrorMsg.value = "Invalid Email Id"
                _uiState.value = 0
                return
            }
            _uiState.value = 1
        } else {
            _uiErrorMsg.value = "Field is Required"
            _uiState.value = 0
        }
    }

    fun checkRegistrationValidation(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        if (!invalidRegistrationInput(name, email, phone, password, confirmPassword)) {
            if (!CommonUtil.emailValidation(email)) {
                _uiErrorMsg.value = "Invalid Email Id"
                _uiState.value = 0
            } else if (phone.length < 10) {
                _uiErrorMsg.value = "Invalid Phone Number"
                _uiState.value = 0
            } else if (!password.equals(confirmPassword)) {
                _uiErrorMsg.value = "confirm password could not match with password"
                _uiState.value = 0
            } else {
                _uiState.value = 1
            }

        } else {
            _uiErrorMsg.value = "Field is Required"
            _uiState.value = 0
        }
    }

    fun invalidInput(email: String, password: String) =
        (email.isBlank()) || password.isBlank()

    fun invalidRegistrationInput(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) =
        name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() || confirmPassword.isBlank()

}