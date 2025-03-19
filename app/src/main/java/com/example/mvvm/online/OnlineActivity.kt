package com.example.mvvm.online

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mvvm.NetworkConnectivityObserver
import com.example.mvvm.db.LocalDataStoreImp
import com.example.mvvm.models.Product
import com.example.mvvm.network.RemoteDataSourceImp
import com.example.mvvm.repo.ProductsRepositoryImp
import com.example.mvvm.ui.theme.MVVMTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnlineActivity : ComponentActivity() {
    lateinit var isConnectedState: MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            isConnectedState= remember { mutableStateOf(false) }
            MVVMTheme {
                val factory=OnlineProductViewModelFactory(ProductsRepositoryImp(
                    localDataStore = LocalDataStoreImp.getInstance(this@OnlineActivity),
                    remoteDataSource = RemoteDataSourceImp.getInstance()
                ))
                val viewModel= ViewModelProvider(this, factory = factory)[OnlineProductViewModel::class.java]
                OnlineUI(viewModel)
            }
        }
        val networkObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {
            networkObserver.observeNetworkStatus().collect { status ->
                if (!isConnectedState.value)
                isConnectedState.value=status
            }
            delay(50)
            isConnectedState.value=networkObserver.currentStatus()
        }
    }
    @Composable
    fun OnlineUI(viewModel: OnlineProductViewModel) {
        if (isConnectedState.value){
            viewModel.getAll()
            val dataState=viewModel.product.observeAsState()
            val scope= rememberCoroutineScope()
            val snackBarHostState= remember{ SnackbarHostState()}
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackBarHostState) }
            ) { innerPadding ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    items(dataState.value?.size ?: 0){
                        val current=dataState.value?.get(it)
                        ProductItem(product = current) {
                            viewModel.addProduct(current)
                            scope.launch {
                                snackBarHostState.showSnackbar(message = "item:$current added to Favorites", duration = SnackbarDuration.Short)
                            }
                        }
                    }
                }
            }
        }
        else{
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ){inner->
                Text(
                    "No Internet Connection",
                    modifier = Modifier.padding(inner)
                    )

            }

        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Product?,action: ()->Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        GlideImage(
            model = product?.imgUrl,
            contentDescription = product?.description,
            modifier = Modifier
                .height(100.dp)
                .width(120.dp)
        )
        Column(modifier = Modifier.width(150.dp)) {
            Text(
                text = product?.title?:"",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = product?.category?:"",
                fontSize = 15.sp,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = if (product!=null) product.price.toString() + "$" else "",
                color = Color(0.0f, 0.0f, 0.0f, 0.302f),
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = action) {
            Text(text = "add to Favorites", textAlign = TextAlign.Center)
        }
    }
}
