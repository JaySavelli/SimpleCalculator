package com.jsavelli.calculator.model

sealed class CalculatorIntent {
    data class Number(val value: String) : CalculatorIntent()
    data class Operation(val operation: CalculatorOperation) : CalculatorIntent()
    object Calculate : CalculatorIntent()
    object Clear : CalculatorIntent()
}
