@file:OptIn(ExperimentalMaterial3Api::class)

package eu.androidudemyclass.eventaggregatorapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel
import eu.androidudemyclass.eventaggregatorapp.model.NavigationItems
import eu.androidudemyclass.eventaggregatorapp.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    authViewModel: AuthViewModel = viewModel(),
    userRepo: UserRepository ,
    onNavigateTo: () -> Unit
) {
    val firstName by authViewModel.firstName.observeAsState()
    val items = listOf(
        NavigationItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItems(
            title = "Contact us",
            selectedIcon = Icons.Filled.Phone,
            unselectedIcon = Icons.Outlined.Phone
        ),
        NavigationItems(
            title = "Registered Events",
            selectedIcon = Icons.Filled.MoreVert,
            unselectedIcon = Icons.Outlined.MoreVert,
        ),
    )


    LaunchedEffect(key1 = Unit) {
        authViewModel.fetchFirstName()
    }
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var selectedTitle by remember { mutableStateOf("Festiverse") }

    ModalNavigationDrawer(
        modifier = Modifier
            .width(300.dp),
        drawerContent = {
            ModalDrawerSheet(
                drawerTonalElevation = 8.dp,
                drawerContainerColor = MaterialTheme.colorScheme.background

            ) {
                Row(
                    modifier = Modifier.padding(16.dp), // Increased padding for better spacing
                    horizontalArrangement = Arrangement.Start // Align items to the start
                ) {
                    // Display the account icon
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Account",
                        modifier = Modifier.size(40.dp))

                    Spacer(modifier = Modifier.width(16.dp))

                    // Display the first name if available
                    firstName?.let { name ->
                        Text(text = "Welcome, $name!")
                    }
                }

                items.forEachIndexed { index, items ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = items.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            selectedTitle = items.title
                            scope.launch {
                                drawerState.close()

                            }
                        },
                        icon = {
                            IconButton(onClick ={onNavigateTo()}) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        items.selectedIcon
                                    } else items.unselectedIcon,
                                    contentDescription = items.title
                                )
                            }
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = selectedTitle) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (selectedTitle == "Festiverse") {
                                scope.launch { drawerState.open() }
                            } else {
                                onNavigateTo() // Handle back navigation
                            }
                        }) {
                            Icon(
                                imageVector = if (selectedTitle == "Festiverse") {
                                    Icons.Default.Menu
                                } else {
                                    Icons.AutoMirrored.Default.ArrowBack
                                },
                                contentDescription = if (selectedTitle == "Festiverse") "Menu" else "Back"
                            )
                        }
                    }
                )
            }
        ){
            Column(modifier = Modifier.padding(it)) {

            }

        }

    }
}

