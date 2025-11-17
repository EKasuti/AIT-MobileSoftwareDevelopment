package com.example.exchangerates.ui.screen.moneyscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.exchangerates.data.MoneyResult

@Composable
fun MoneyScreen(
    viewModel: MoneyViewModel = hiltViewModel()
) {
    Column {
        Text("Money exchange rates")
        Button(
            onClick = {viewModel.getRates()}
        ) {
            Text("Refresh exchange rates")
        }

        when (viewModel.moneyUiState) {
            MoneyUiState.Error -> Text("Error")
            MoneyUiState.Init -> Text("Press refresh to start ...")
            MoneyUiState.Loading -> CircularProgressIndicator()
            is MoneyUiState.Success -> MoneyResultWidget(
                (viewModel.moneyUiState as MoneyUiState.Success).moneyRates
            )
        }
    }
}

@Composable
fun MoneyResultWidget(moneyRates: MoneyResult) {
    Column() {
        Text(text = "Base: EUR")
        Text(text = "USD: ${moneyRates.rates?.uSD}")
        Text(text = "EUR: ${moneyRates.rates?.eUR}")
        Text(text = "HUF: ${moneyRates.rates?.hUF}")
        Text(text = "GBP: ${moneyRates.rates?.gBP}")
    }
}