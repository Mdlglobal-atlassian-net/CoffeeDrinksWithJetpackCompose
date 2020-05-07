package com.alexzh.coffeedrinks.ui.screen.coffeedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.model.CoffeeDrinkDetail
import kotlinx.coroutines.launch

class CoffeeDrinkDetailsViewModel(
    val repository: CoffeeDrinkRepository,
    val mapper: CoffeeDrinkDetailMapper
) : ViewModel() {
    private val _coffeeDrink = MutableLiveData<CoffeeDrinkDetail>()
    val coffeeDrink: LiveData<CoffeeDrinkDetail> = _coffeeDrink

    fun loadCoffeeDrink(coffeeDrinkId: Long) {
        viewModelScope.launch {
            val coffeeDrink = mapper.map(repository.getCoffeeDrink(coffeeDrinkId))
            _coffeeDrink.value = coffeeDrink
        }
    }

    fun changeFavouriteState(currentCoffeeDrink: CoffeeDrinkDetail) {
        viewModelScope.launch {
            val newValue = if (repository.updateCoffeeDrink(currentCoffeeDrink.id)) {
                currentCoffeeDrink.copy(isFavourite = currentCoffeeDrink.isFavourite)
            } else {
                currentCoffeeDrink
            }
            _coffeeDrink.value = newValue
        }
    }
}
