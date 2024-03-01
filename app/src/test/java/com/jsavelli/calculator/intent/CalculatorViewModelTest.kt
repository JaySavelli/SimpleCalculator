package com.jsavelli.calculator.intent
import androidx.compose.runtime.getValue
import com.jsavelli.calculator.model.CalculatorIntent
import com.jsavelli.calculator.model.CalculatorOperation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun appendNumberToInitialZero() {
        viewModel.dispatch(CalculatorIntent.Number("5"))
        val state by viewModel.state
        assertEquals("5", state.input)
    }

    @Test
    fun appendOperationAfterNumber() {
        viewModel.dispatch(CalculatorIntent.Number("5"))
        viewModel.dispatch(CalculatorIntent.Operation(CalculatorOperation.MULTIPLY))
        val state by viewModel.state
        assertEquals("5 * ", state.input)
    }

    @Test
    fun clearInputResetsToInitialState() {
        viewModel.dispatch(CalculatorIntent.Number("5"))
        viewModel.dispatch(CalculatorIntent.Clear)
        val state by viewModel.state
        assertEquals("0", state.input)
        assertEquals("", state.result)
    }

    @Test
    fun calculateResultOfSimpleExpression() {
        viewModel.dispatch(CalculatorIntent.Number("5"))
        viewModel.dispatch(CalculatorIntent.Operation(CalculatorOperation.ADD))
        viewModel.dispatch(CalculatorIntent.Number("3"))
        viewModel.dispatch(CalculatorIntent.Calculate)
        val state by viewModel.state
        // Assuming evaluateExpression works as intended
        assertEquals("8", state.result)
    }
}
