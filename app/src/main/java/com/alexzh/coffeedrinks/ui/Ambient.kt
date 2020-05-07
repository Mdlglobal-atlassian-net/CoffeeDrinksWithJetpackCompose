package com.alexzh.coffeedrinks.ui

import androidx.compose.ambientOf
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksViewModel
import java.lang.IllegalArgumentException

val CoffeeDrinksViewModelAmbient = ambientOf<CoffeeDrinksViewModel> {
    throw IllegalArgumentException("CoffeeDrinksViewModel is not initialized")
}
