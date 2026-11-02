package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CenturiesApp
import com.example.ui.CenturiesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: CenturiesViewModel = viewModel(
                factory = CenturiesViewModel.provideFactory(applicationContext)
            )
            CenturiesApp(viewModel = vm)
        }
    }
}
