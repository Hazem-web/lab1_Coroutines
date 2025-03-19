package com.example.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvvm.fav.FavoriteActivity
import com.example.mvvm.online.OnlineActivity
import com.example.mvvm.ui.theme.MVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVMTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    HomeUI(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    @Composable
    fun HomeUI(modifier: Modifier){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                colorFilter = ColorFilter.tint(color = Color.Green)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                startActivity(Intent(this@MainActivity,OnlineActivity::class.java))
            }) {
                Text(text = "Get All Products")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                startActivity(Intent(this@MainActivity,FavoriteActivity::class.java))
            }) {
                Text(text = "Get Favorites Products")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                finish()
            }) {
                Text(text = "Exit")
            }
        }
    }
}

