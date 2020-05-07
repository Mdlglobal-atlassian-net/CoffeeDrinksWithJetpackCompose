package com.alexzh.coffeedrinks.ui.screen.coffeedrinks

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.ripple
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.alexzh.coffeedrinks.R
import com.alexzh.coffeedrinks.ui.CoffeeDrinksViewModelAmbient
import com.alexzh.coffeedrinks.ui.Screen
import com.alexzh.coffeedrinks.ui.appTypography
import com.alexzh.coffeedrinks.ui.lightThemeColors
import com.alexzh.coffeedrinks.ui.navigateTo
import com.alexzh.coffeedrinks.ui.observe
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CardType
import com.alexzh.coffeedrinks.ui.screen.coffeedrinks.model.CoffeeDrinkItem

val cardType = CardType(isDetailedCard = false)

@Preview
@Composable
fun previewCoffeeDrinksScreen() {
    MaterialTheme(colors = lightThemeColors, typography = appTypography) {
        CoffeeDrinksScreen()
    }
}

@Composable
fun CoffeeDrinksScreen() {
    val viewModel = CoffeeDrinksViewModelAmbient.current
    val coffeeDrinks = observe(viewModel.coffeeDrinks)
    viewModel.loadCoffeeDrinks()

    if (coffeeDrinks != null) {
        Surface {
            Column {
                CoffeeDrinkAppBar(cardType)
                CoffeeDrinkList(
                    cardType = cardType,
                    coffeeDrinks = coffeeDrinks,
                    onCoffeeDrinkClicked = { onCoffeeDrinkClicked(it) },
                    onFavouriteStateChanged = { onCoffeeFavouriteStateChanged(viewModel, it) }
                )
            }
        }
    }
}

@Composable
fun CoffeeDrinkAppBar(cardType: CardType) {
    TopAppBar(
        title = {
            Text(
                text = "Coffee Drinks",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            IconButton(
                onClick = { cardType.isDetailedCard = !cardType.isDetailedCard }
            ) {
                Icon(
                    painter = ImagePainter(
                        imageResource(id = if (cardType.isDetailedCard) R.drawable.ic_list_white else R.drawable.ic_extended_list_white)
                    ),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = { navigateTo(Screen.OrderCoffeeDrinks) }) {
                Icon(
                    painter = ImagePainter(imageResource(id = R.drawable.ic_order_white)),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Composable
fun CoffeeDrinkList(
    cardType: CardType,
    coffeeDrinks: ModelList<CoffeeDrinkItem>,
    onCoffeeDrinkClicked: (CoffeeDrinkItem) -> Unit,
    onFavouriteStateChanged: (CoffeeDrinkItem) -> Unit
) {
    AdapterList(
        data = coffeeDrinks
    ) { coffeeDrink ->
        Clickable(
            onClick = { onCoffeeDrinkClicked(coffeeDrink) },
            modifier = Modifier.ripple(bounded = true)
        ) {
            if (cardType.isDetailedCard) {
                Box(modifier = Modifier.padding(8.dp)) {
                    CoffeeDrinkDetailedItem(
                        coffeeDrink = coffeeDrink,
                        onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                    )
                }
            } else {
                CoffeeDrinkListItemWithDivider(
                    coffeeDrink = coffeeDrink,
                    onFavouriteStateChanged = { onFavouriteStateChanged(it) }
                )
            }
        }
    }
}

private fun onCoffeeFavouriteStateChanged(
    viewModel: CoffeeDrinksViewModel,
    coffee: CoffeeDrinkItem
) {
    viewModel.changeFavouriteState(coffee)
}

private fun onCoffeeDrinkClicked(coffee: CoffeeDrinkItem) {
    navigateTo(Screen.CoffeeDrinkDetails(coffee.id))
}
