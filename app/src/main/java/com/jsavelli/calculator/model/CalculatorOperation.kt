package com.jsavelli.calculator.model

enum class CalculatorOperation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE, EQUALS;

    companion object {
        fun fromString(op: String): CalculatorOperation? = when (op) {
            "+" -> ADD
            "-" -> SUBTRACT
            "*" -> MULTIPLY
            "/" -> DIVIDE
            "=" -> EQUALS
            else -> null
        }
    }
}
