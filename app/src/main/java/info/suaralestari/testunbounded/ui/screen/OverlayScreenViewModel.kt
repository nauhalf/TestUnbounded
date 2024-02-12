package info.suaralestari.testunbounded.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class OverlayScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun addAnimation(animationOverlay: AnimationOverlay){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    listAnimation = it.listAnimation + animationOverlay
                )
            }
        }
    }

    fun addAnimationTrash(animationOverlay: AnimationOverlay){
        viewModelScope.launch {
            if(!uiState.value.trashAnimation.contains(animationOverlay)){
                _uiState.update {
                    it.copy(
                        trashAnimation = it.trashAnimation + animationOverlay
                    )
                }
            }
        }
    }

    fun deleteAnimation(){
       viewModelScope.launch {
           _uiState.update {
               it.copy(
                   listAnimation = emptyList(),
                   trashAnimation = emptyList()
               )
           }
       }
    }
}

data class UiState(
    val listAnimation: List<AnimationOverlay> = emptyList(),
    val trashAnimation: List<AnimationOverlay> = emptyList(),
)

data class AnimationOverlay(
    val id: Long = Random.nextLong(),
    val asset: String = listOf("https://lottie.host/dd7b6c81-1b8d-4da3-8ec6-9feda183f1ef/wTWR4JPqPl.json", "https://lottie.host/a1ed85e2-7083-4a02-8b38-6d72a19b88ce/jfepHofoiu.json").random(),
    val biasX: Float = Random.nextFloat(),
    val biasY: Float = Random.nextFloat(),
)
