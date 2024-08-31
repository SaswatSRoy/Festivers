package eu.androidudemyclass.eventaggregatorapp.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.androidudemyclass.eventaggregatorapp.R
import eu.androidudemyclass.eventaggregatorapp.model.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun AboutUsScreen(
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel
) {
    val aboutUsText = buildAnnotatedString {
        append("Welcome to ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Festiverse!")
        }
        append(" We are your one-stop platform for discovering and attending exciting events happening around you. Our mission is to connect people with experiences that matter. Whether you're into ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("music, art, tech,")
        }
        append(" or anything in between,we've got you covered.\n\n")

        append("With our app, you can:\n")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(" • Easily browse and search for events based on your interests.\n")
            append(" • Get personalized recommendations tailored just for you.\n")
            append(" • Save events to your calendar so you never miss out.\n")
            append(" • Purchase tickets securely and conveniently.\n\n")
        }

        append("We're constantly working to improve your experience, so stay tuned for exciting new features!")
    }

    var textVisibility by remember { mutableStateOf(false) }
    val textAlpha by animateFloatAsState(
        targetValue = if (textVisibility) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeight = screenHeight * 0.3f

    LaunchedEffect(Unit) {
        delay(300)
        textVisibility = true
    }

    // Use a Box to layer elements and add a subtle background animation
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.errorContainer)
    ) {// Subtle background animation (a slowly moving gradient)
        var gradientOffset by remember { mutableFloatStateOf(0f) }
        val animateOffset by animateFloatAsState(
            targetValue = gradientOffset,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
        val pulseScale by animateFloatAsState(
            targetValue = if (gradientOffset %200f < 100f) 1.1f else 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        val imageAngle by animateFloatAsState(
            targetValue = if (textVisibility) 0f else -10f, // Rotateslightly when visible
            animationSpec = tween(durationMillis = 800), label = ""
        )
        val imageScale by animateFloatAsState(
            targetValue = if (textVisibility) 1f else 0.9f,animationSpec = tween(durationMillis = 800), label = ""
        )


        LaunchedEffect(Unit) {
            // Continuously update the gradient offset
            while (true) {
                gradientOffset += 1f
                if (gradientOffset >1000f) gradientOffset = 0f // Reset offset
                delay(10) // Adjust delay for animation speed
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = primaryColor, // Adjust color and alpha
                radius = (size.minDimension / 3) * pulseScale, // Reduce circle size
                center = Offset(x = size.width / 2 + animateOffset, y = size.height / 2)
            )
        }


        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedText(
                text = "ABOUT US",
                style = MaterialTheme.typography.headlineLarge, // More prominent headline style
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary, // Use primary color for emphasis
                isVisible = textVisibility
            )

            Spacer(modifier = Modifier.padding(16.dp))

            AnimatedText(
                text = aboutUsText.text, // Use the annotated string
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                isVisible = textVisibility
            )

            Spacer(modifier = Modifier.padding(16.dp))

            // Image with a subtle zoom-in animation

            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "About Us Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .alpha(textAlpha)
                    .scale(imageScale)
                    .rotate(imageAngle) // Apply rotation animation
            )

            Spacer(modifier = Modifier.padding(16.dp))

            // Additional Text (Example)
            AnimatedText(
                text = "Join us on this exciting journey!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                isVisible = textVisibility
            )

            Spacer(modifier = Modifier.weight(1f)) // Push the button to the bottom

            AnimatedButton(
                onClick = onNavigateToSignUp,
                text = "Sign Up",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the button
                    .padding(16.dp)
                    .size(20.dp),
                isVisible = textVisibility
            )
        }
    }
}

@Composable
fun AnimatedText(
    text: String,
    style: androidx.compose.ui.text.TextStyle,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    isVisible: Boolean
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Text(
        text = text,
        style = style,
        modifier = modifier.alpha(alpha),
        color = color
    )
}

@Composable
fun AnimatedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    // Get the color outside the Text composable
    val primaryColor = MaterialTheme.colorScheme.primary

    TextButton(
        onClick = onClick,
        modifier = modifier.alpha(alpha)
    ) {
        Text(
            text = text,
            color = primaryColor // Usethe retrieved color here
        )
    }
}
@Preview
@Composable

fun AboutUsScreenPreview(){


}