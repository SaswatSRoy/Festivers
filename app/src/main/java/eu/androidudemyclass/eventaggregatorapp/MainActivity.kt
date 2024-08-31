package eu.androidudemyclass.eventaggregatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.EventAggregatorAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.androidudemyclass.eventaggregatorapp.composables.Navigation
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel
import eu.androidudemyclass.eventaggregatorapp.repository.UserRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val userRepository = UserRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
            EventAggregatorAppTheme {
                 Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = MaterialTheme.colorScheme.background
                 ) {

                    Navigation(navController = navController, authViewModel = authViewModel,userRepository)
                 }
            }
        }
    }
}

