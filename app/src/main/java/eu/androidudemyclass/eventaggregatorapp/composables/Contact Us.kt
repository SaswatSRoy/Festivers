package eu.androidudemyclass.eventaggregatorapp.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.androidudemyclass.eventaggregatorapp.R
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel



import com.example.compose.EventAggregatorAppTheme





@Composable
fun ContactUsScreen(
    authViewModel: AuthViewModel
) {
    EventAggregatorAppTheme {
        val backgroundColor = MaterialTheme.colorScheme.tertiaryContainer

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            // Feedback Form
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var feedback by remember { mutableStateOf("") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )

            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = feedback,
                onValueChange = { feedback = it },
                label = { Text("Feedback", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle feedback submission */ },
                modifier =Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Submit Feedback", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Or, contact us directly:",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Email: support@eventaggregator.com",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { /* Open email client */ }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Phone: +1 (555) 123-4567",
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable { /* Open phone dialer */ }
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row {
                AnimatedIcon(
                    iconResourceId = R.drawable.twitter,
                    contentDescription = "Twitter",
                    onClick = {  }
                )
                Spacer(modifier = Modifier.width(16.dp))
                AnimatedIcon(
                    iconResourceId = R.drawable.facebook,
                    contentDescription = "Facebook",
                    onClick = {  }
                )
                Spacer(modifier = Modifier.width(16.dp))
                AnimatedIcon(
                    iconResourceId = R.drawable.instagram,
                    contentDescription = "Instagram",
                    onClick = {  }
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val faqItems = listOf(
                "How do I create an account?" to "Tap on the 'Sign Up' button...",
                "Can I attend events for free?" to "Some events are free, while others...",

            )

                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    items(faqItems) { item ->
                        Column(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                        ) {
                            Text(
                                text = item.first,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(text = item.second, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

// Animated Icon Composable
@Composable
fun AnimatedIcon(
    iconResourceId: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    var iconScale by remember { mutableFloatStateOf(1f) }
    val animatedScale = animateFloatAsState(
        targetValue = iconScale,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    IconButton(
        onClick = {
            iconScale = if (iconScale == 1f) 1.2f else 1f
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(24.dp)
                .scale(animatedScale.value),


        )
    }
}