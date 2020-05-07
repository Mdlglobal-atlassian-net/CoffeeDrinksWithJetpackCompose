package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.frames.ModelList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem
import kotlinx.coroutines.launch

class CoffeeDrinksViewModel(
    val repository: CoffeeDrinkRepository,
    val mapper: CoffeeDrinkItemMapper
) : ViewModel() {
    private val _coffeeDrinks = MutableLiveData<ModelList<CoffeeDrinkItem>>()
    val coffeeDrinks: LiveData<ModelList<CoffeeDrinkItem>> = _coffeeDrinks

    fun loadCoffeeDrinks() {
        val modelList = ModelList<CoffeeDrinkItem>()
        viewModelScope.launch {
            repository.getCoffeeDrinks()
                .forEach { modelList.add(mapper.map(it)) }

            _coffeeDrinks.value = modelList
        }
    }

    fun changeFavouriteState(coffeeDrink: CoffeeDrinkItem) {
        repository.updateCoffeeDrink(coffeeDrink.id)
        loadCoffeeDrinks()
    }
}
