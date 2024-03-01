package com.jsavelli.calculator.intent

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jsavelli.calculator.model.CalculatorIntent
import com.jsavelli.calculator.model.CalculatorState
import com.jsavelli.calculator.model.CalculatorOperation

class CalculatorViewModel: ViewModel() {
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state

    fun dispatch(intent: CalculatorIntent) {
        when (intent) {
            is CalculatorIntent.Number -> appendNumberOrDecimal(intent.value)
            is CalculatorIntent.Operation -> performOperation(intent.operation)
            is CalculatorIntent.Calculate -> calculateResult()
            is CalculatorIntent.Clear -> clearInput()
        }
    }

    private fun appendNumberOrDecimal(number: String) {
        if (_state.value.isResultDisplayed) {
            // If the previous state was displaying a result, start fresh with the new number
            _state.value = CalculatorState(input = number, isResultDisplayed = false)
        } else {
            // Existing logic for appending numbers
            val currentInput = _state.value.input
            _state.value = if (currentInput == "0") {
                if (number == ".") {
                    _state.value.copy(input = "$currentInput$number")
                } else {
                    _state.value.copy(input = number)
                }
            } else {
                _state.value.copy(input = "$currentInput$number")
            }
        }
    }



    private fun performOperation(operation: CalculatorOperation) {
        val currentInput = _state.value.input
        val isResultDisplayed = _state.value.isResultDisplayed

        val newInput = if (isResultDisplayed) {
            // Use the result as the start of the new input, then append the operation
            "$currentInput " // Assume result is already in `input` and reset `isResultDisplayed`
        } else if (currentInput.isNotEmpty()) {
            // Continue appending to the existing input
            "$currentInput "
        } else {
            // Fallback for any other case, though this should not happen
            ""
        }

        // Append the operation symbol to the input string based on the operation enum
        val operationSymbol = when (operation) {
            CalculatorOperation.ADD -> "+"
            CalculatorOperation.SUBTRACT -> "-"
            CalculatorOperation.MULTIPLY -> "*"
            CalculatorOperation.DIVIDE -> "/"
            CalculatorOperation.EQUALS -> "=" // Though typically, you wouldn't append '=' to the input
        }
        _state.value = _state.value.copy(input = "$newInput$operationSymbol ", isResultDisplayed = false)    }

    private fun calculateResult() {
        val input = _state.value.input.trim()
        try {
            val result = evaluateExpression(input)
            val formattedResult = formatResult(result)
            _state.value = _state.value.copy(result = formattedResult, input = formattedResult, isResultDisplayed = true)
        } catch (e: Exception) {
            _state.value = _state.value.copy(result = "Error", isResultDisplayed = true)
        }
    }



    private fun clearInput() {
        _state.value = CalculatorState()
    }


    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.trim().split(" ")
        var result = tokens[0].toDouble()

        for (i in 1 until tokens.size step 2) {
            val operator = tokens[i]
            val nextNumber = tokens[i + 1].toDouble()

            result = when (operator) {
                "+" -> result + nextNumber
                "-" -> result - nextNumber
                "*" -> result * nextNumber
                "/" -> result / nextNumber
                else -> throw IllegalArgumentException("Unknown operator")
            }
        }

        return result
    }

    fun formatResult(result: Double): String {
        // Check if the result is an integer value
        return if (result % 1.0 == 0.0) {
            // If yes, format it as an integer (no decimal point)
            result.toInt().toString()
        } else {
            // If no, format it with decimal points
            result.toString()
        }
    }

}