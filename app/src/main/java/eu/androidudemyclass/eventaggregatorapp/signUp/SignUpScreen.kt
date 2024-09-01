package eu.androidudemyclass.eventaggregatorapp.signUp


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.androidudemyclass.eventaggregatorapp.R
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel
import eu.androidudemyclass.eventaggregatorapp.model.Result


@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel,

){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var collegeName by remember{
        mutableStateOf("")
    }
    val result by authViewModel.authResult.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.festiverse),
            contentDescription= "Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(3.dp, Color.Black, CircleShape)
                .padding(4.dp),
            alignment = Alignment.Center


        )
        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            label = { Text(text = "Email",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle)},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = "Password",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,)},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            value = firstName,
            onValueChange = {firstName=it},
            label = { Text(text = "First Name",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,)},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = {lastName=it},
            label = { Text(text = "Last Name",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,)},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        OutlinedTextField(
            value =collegeName,
            onValueChange = {collegeName=it},
            label = { Text(text = "College Name",
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,)},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Button(
            onClick = {
                authViewModel.signUp(email,password,firstName,lastName,collegeName)
                email=""
                password=""
                firstName=""
                lastName=""
                collegeName=""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
            Text(text = "Sign Up", fontSize = 20.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Already Have an Account ?",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text("Sign In",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .clickable { onNavigateToLogin() },
                textDecoration = TextDecoration.Underline
            )
        }

        LaunchedEffect(result) {
            if (result is Result.Success) {
                authViewModel.onSignInSuccess()

            }
        }





    }
}
@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(onNavigateToLogin = {}, authViewModel = AuthViewModel()  )
}



