package com.alexzh.coffeedrinks.di

import com.alexzh.coffeedrinks.data.CoffeeDrinkRepository
import com.alexzh.coffeedrinks.data.RuntimeCoffeeDrinkRepository
import com.alexzh.coffeedrinks.ui.screen.coffeedetails.mapper.CoffeeDrinkDetailMapper
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.CoffeeDrinksViewModel
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.mapper.CoffeeDrinkItemMapper
import com.alexzh.coffeedrinks.ui.screen.order.mapper.OrderCoffeeDrinkMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<CoffeeDrinkRepository> { RuntimeCoffeeDrinkRepository }
}

val coffeeDrinksModule = module {
    factory { CoffeeDrinkItemMapper() }
    viewModel { CoffeeDrinksViewModel(repository = get(), mapper = get()) }
}

// TODO: fix it by introducing feature modules
val mapperModule = module {
    factory { CoffeeDrinkDetailMapper() }
    factory { OrderCoffeeDrinkMapper() }
}
