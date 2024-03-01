package com.jsavelli.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jsavelli.calculator.intent.CalculatorViewModel
import com.jsavelli.calculator.ui.theme.CalculatorTheme
import com.jsavelli.calculator.view.CalculatorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    // Obtain an instance of CalculatorViewModel
                    val calculatorViewModel: CalculatorViewModel by viewModels()

                    // Pass the ViewModel to your CalculatorScreen composable
                    CalculatorScreen(calculatorViewModel)
                }
            }
        }
    }
}
