package com.bangkit.coffee.presentation.welcome

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val carouselItems = listOf(
        WelcomeCarouselItem(
            image = R.drawable.welcome_1,
            title = R.string.welcome_title_1,
            description = R.string.welcome_description_1
        ),
        WelcomeCarouselItem(
            image = R.drawable.welcome_2,
            title = R.string.welcome_title_2,
            description = R.string.welcome_description_2
        ),
        WelcomeCarouselItem(
            image = R.drawable.welcome_3,
            title = R.string.welcome_title_3,
            description = R.string.welcome_description_3
        )
    )

    private val _stateFlow = MutableStateFlow(
        WelcomeState(
            carouselItems = carouselItems
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

}