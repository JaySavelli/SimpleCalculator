package com.jsavelli.calculator.model

data class CalculatorState(
    val input: String = "0",
    val result: String = "",
    val isResultDisplayed: Boolean = false // New flag to track if result is displayed
)
