package eu.androidudemyclass.eventaggregatorapp.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun AnimatedSplashScreen(
    onNavigateToAboutUs: () -> Unit
) {
    val text = "FESTIVERSE"
    val letters = text.toCharArray().map { it.toString() }
    val animationDurations = listOf(500, 600, 700, 800,500, 600, 700, 800, 500, 600)
    var currentLetterIndex by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "WELCOME TO",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Serif,
                letterSpacing = 2.sp
            ),
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            letters.forEachIndexed { index, letter ->
                index * 100 // Stagger animation
                AnimatedLetter(
                    letter = letter,
                    isAnimating = index == currentLetterIndex

                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { onNavigateToAboutUs() },
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = "Get Started ->",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }


    LaunchedEffect(key1 = currentLetterIndex) {
        if (currentLetterIndex < letters.size ) {
            delay(animationDurations.getOrElse(currentLetterIndex) { 500 }.toLong())
            currentLetterIndex++
        }
    }

}



@Composable
fun AnimatedLetter(letter: String, isAnimating: Boolean) {
    val delayMillis = 100 // Delay between animations

    var animationStarted by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isAnimating && animationStarted) 1.2f else 1f,
        animationSpec = if (isAnimating && animationStarted) {
            spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        } else {
            tween(durationMillis = 0)
        }, label = ""
    )

    LaunchedEffect(key1 = isAnimating) {
        if (isAnimating) {
            delay(delayMillis.toLong())
            animationStarted = true
        }
    }

    Text(
        text =letter,
        style = MaterialTheme.typography.headlineLarge.copy(
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.2f),
                offset = Offset(2f, 2f),
                blurRadius = 4f
            )
        ),
        modifier = Modifier
            .scale(scale)
            .padding(horizontal = 4.dp)
    )
}
