@file:OptIn(ExperimentalMaterial3Api::class)

package eu.androidudemyclass.eventaggregatorapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var selectedTitle by remember { mutableStateOf("Festiverse") }
    val drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)

    ModalNavigationDrawer(
        modifier = Modifier
            .width(300.dp),
        drawerContent = {
            ModalDrawerSheet(
                drawerTonalElevation = 0.dp,
                drawerContainerColor = MaterialTheme.colorScheme.background,
                drawerShape = drawerShape

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Account",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onPrimary // High contrast icon color
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        firstName?.let { name ->
                            Text(
                                text = "Welcome, $name!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

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

