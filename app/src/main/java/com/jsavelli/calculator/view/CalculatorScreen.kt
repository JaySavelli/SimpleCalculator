package com.jsavelli.calculator.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsavelli.calculator.intent.CalculatorViewModel
import com.jsavelli.calculator.model.CalculatorIntent
import com.jsavelli.calculator.model.CalculatorOperation

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val state = viewModel.state.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Input: ${state.input}", style = MaterialTheme.typography.h5)
            Text(text = "Result: ${state.result}", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))

            // Numeric buttons layout
            val numbers = arrayOf(
                arrayOf("7", "8", "9"),
                arrayOf("4", "5", "6"),
                arrayOf("1", "2", "3"),
                arrayOf("0", ".", "=")
            )
            numbers.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { label ->
                        CalculatorButton(text = label, onClick = {
                            when (label) {
                                "=" -> viewModel.dispatch(CalculatorIntent.Calculate)
                                "." -> viewModel.dispatch(CalculatorIntent.Number(label)) // Correctly handled
                                else -> viewModel.dispatch(CalculatorIntent.Number(label))
                            }
                        }, modifier = Modifier.weight(1f))
                    }
                }
            }

            // Operation buttons layout
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorOperation.values().forEach { operation ->
                    if (operation != CalculatorOperation.EQUALS) { // Exclude EQUALS as it's already handled
                        val opSymbol = when (operation) {
                            CalculatorOperation.ADD -> "+"
                            CalculatorOperation.SUBTRACT -> "-"
                            CalculatorOperation.MULTIPLY -> "*"
                            CalculatorOperation.DIVIDE -> "/"
                            else -> "" // Ideally, this case won't be hit
                        }
                        CalculatorButton(text = opSymbol, onClick = {
                            viewModel.dispatch(CalculatorIntent.Operation(operation))
                        })
                    }
                }
            }

            // Clear button
            CalculatorButton(text = "C", onClick = {
                viewModel.dispatch(CalculatorIntent.Clear)
            }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
    ) {
        Text(text)
    }
}
