package info.suaralestari.testunbounded.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay


@Composable
fun OverlayScreen(viewModel: OverlayScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = uiState.listAnimation) {
        Log.d("ListConstraintLayout", "List item ${uiState.listAnimation}")
    }

    LaunchedEffect(key1 = Unit) {
        while (true){
            if(uiState.listAnimation.isNotEmpty()){
                if(uiState.trashAnimation.containsAll(uiState.listAnimation)){
                    viewModel.deleteAnimation()
                }
            }
            delay(100L)
        }
    }
    Column {
//        LazyColumn(
//            Modifier
//                .weight(1f)
//                .fillMaxWidth()
//        ) {
//            items(uiState.listAnimation){
//                OverlayAnimationItem(
//                    animationOverlay = it,
//                    onFinish = { a ->
//                        Log.d("OnFinish Item","OnFinish Item $a")
//
//                        viewModel.deleteAnimation(a)
//                    },
//                    modifier = Modifier
//                        .size(50.dp)
////                        .constrainAs(
////                            ref
////                        ) {
////                            linkTo(start = parent.start, end = parent.end, bias = it.biasX)
////                            linkTo(top = parent.top, bottom = parent.bottom, bias = it.biasY)
////                        }
//                )
//            }
        ConstraintLayout(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            uiState.listAnimation.forEach {
                val ref = createRef()
                OverlayAnimationItem(
                    animationOverlay = it,
                    onFinish = { a ->
                        viewModel.addAnimationTrash(a)
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(
                            ref
                        ) {
                            linkTo(start = parent.start, end = parent.end, bias = it.biasX)
                            linkTo(top = parent.top, bottom = parent.bottom, bias = it.biasY)
                        },
                )
            }
        }
        Box(
            Modifier.background(Color.Black.copy(alpha = 0.5f))
        ) {
            Button(onClick = {
                val animation = AnimationOverlay()
                Log.d("onAdd Item", "onAdd Item $animation")

                viewModel.addAnimation(animation)
            }) {
                Text(text = "Add Animation")
            }
        }
    }
}

@Composable
fun OverlayAnimationItem(
    animationOverlay: AnimationOverlay,
    onFinish: (AnimationOverlay) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Use LaunchedEffect to trigger the start of the animation
    Box(modifier = modifier) {

        val composition by rememberLottieComposition(LottieCompositionSpec.Url(animationOverlay.asset))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1
        )
        AnimatedVisibility(visible = progress < 1f, enter = fadeIn(), exit = fadeOut()) {
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
            )
            if (progress >= 1f) {
                onFinish.invoke(animationOverlay)
            }
        }


    }
}
