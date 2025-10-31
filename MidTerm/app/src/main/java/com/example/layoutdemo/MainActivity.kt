package com.example.layoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutdemo.ui.theme.LayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DemoLayout(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            onClick ={-> null }
        ) {
            Text(stringResource(R.string.press))
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            onClick ={-> null }
        ) {
            Text(stringResource(R.string.press))
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)

            ) {
                Text(stringResource(R.string.press))
            }

            Spacer(modifier.weight(1f))
            
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)

            ) {
                Text(stringResource(R.string.press))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DemoLayoutPreview() {
    LayoutDemoTheme {
        DemoLayout()
    }
}