package eu.androidudemyclass.eventaggregatorapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import eu.androidudemyclass.eventaggregatorapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel: ViewModel(){
    private val userRepository: UserRepository

    object Injection{
        private val instance:FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }
        fun instance():FirebaseFirestore {
            return instance
        }
    }

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult
    private val _firstName = MutableLiveData<String?>()
    val firstName: LiveData<String?> = _firstName

    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    fun signUp(email: String, password: String, firstName: String, lastName: String, collegeName: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email, password, firstName, lastName, collegeName)
            if (_authResult.value is Result.Success) {
                Log.d("AuthViewModel", "Sign-up successful, calling onSignInSuccess")
                onSignInSuccess()
            } else {
                // Handle sign-up error
                Log.e("AuthViewModel", "Sign-up failed: ${_authResult.value}")
            }
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.login(email, password)
            if (_authResult.value is Result.Success) {
                onSignInSuccess() // Call onSignInSuccess after successful login
            }
        }
    }
    fun getCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> {
                    _authResult.value = Result.Success(true)
                }
                is Result.Error -> {
                    _authResult.value = Result.Error(result.exception)
                }
            }
        }



    }


    fun fetchFirstName() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> {
                    _firstName.value = result.data.firstName
                }is Result.Error -> {
                _firstName.value = null // Or handle the error appropriately
            }
            }
        }
    }
    fun onSignInSuccess() {
        viewModelScope.launch {
            _navigateToHome.emit(true)
            Log.d("AuthViewModel", "Emitted navigateToHome signal")// Make sure this is called
            fetchFirstName()
        }
    }
}