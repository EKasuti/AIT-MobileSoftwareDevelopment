package com.example.weatherreport.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherreport.R

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoIcon()
            Text(stringResource(R.string.no_cities_yet))
            Text(stringResource(R.string.add_cities_empty_state))
        }
    }
}

@Composable
private fun LogoIcon() {
    Box(
        modifier = Modifier
            .size(80.dp) // total size including border
            .border(
                width = 2.dp,
                color = colorScheme.primary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Public,
            contentDescription = stringResource(R.string.logo_icon),
            modifier = Modifier.size(40.dp),
            tint = colorScheme.primary
        )
    }
}
