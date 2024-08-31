package eu.androidudemyclass.signIn

import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import eu.androidudemyclass.eventaggregatorapp.model.Result
import eu.androidudemyclass.eventaggregatorapp.repository.UserRepository


@Composable
fun SignInScreen(
    onNavigateToSignUp:()->Unit, authViewModel: AuthViewModel, onSignInSuccess:()->Unit
) {
    val result by authViewModel.authResult.observeAsState()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showEmailError by remember {
        mutableStateOf(false)
    }
    var showPassError by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            label = { Text(
                text = "Email",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                ) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            isError = showEmailError,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            supportingText = {
                if (showEmailError) {
                    Text(text = "Invalid email ", color = Color.Red)
                }else{
                    Text(text = "Please enter your email" +
                            "" +
                            "", color = Color.Gray)
                }
            }
        )
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = "Password",

                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            isError = showPassError,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            supportingText = {
                if (showPassError) {
                    Text(text = "Invalid  password", color = Color.Red)
                }else{
                    Text(text = "Please enter your password", color = Color.Gray)
                }
            }

        )
        Spacer(modifier = Modifier.padding(2.dp))
        Button(
            onClick = {
                authViewModel.login(email,password)

            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
        ){
            Text(text = "Sign In", fontSize = 18.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Don't have an account ?",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text("Sign Up",
                style = TextStyle(color = Color.Blue),
                modifier = Modifier.padding(start = 2.dp).clickable {onNavigateToSignUp()},
                textDecoration = TextDecoration.Underline
            )
        }
        LaunchedEffect(result) {
            when (val currentResult = result) {
                is Result.Success -> {
                    authViewModel.onSignInSuccess()

                }

                is Result.Error -> {
                    val exception = currentResult.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        showEmailError = true
                    } else if (exception is FirebaseAuthInvalidCredentialsException) {
                        showPassError = true
                    }
                    // Handle other errors if needed
                }
                else -> {
                    showEmailError = false
                    showPassError = false
                }
            }
        }
    }


    }

@Preview
@Composable
fun SignInScreenPreview(){
    SignInScreen(onNavigateToSignUp = {}, authViewModel = AuthViewModel(), onSignInSuccess = {})
}
