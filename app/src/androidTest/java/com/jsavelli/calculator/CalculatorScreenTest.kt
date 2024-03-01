package com.jsavelli.calculator

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.jsavelli.calculator.intent.CalculatorViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.Before
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsavelli.calculator.view.CalculatorScreen

class CalculatorScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            val viewModel = viewModel<CalculatorViewModel>()
            CalculatorScreen(viewModel = viewModel)
        }
    }

    @Test
    fun pressNumberButtonUpdatesInput() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("Input: 5", useUnmergedTree = true).assertExists()
    }

    @Test
    fun pressOperationButtonAfterNumberUpdatesInput() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Input: 5 + ", useUnmergedTree = true).assertExists()
    }

    @Test
    fun pressClearButtonResetsInput() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("C").performClick()
        composeTestRule.onNodeWithText("Input: 0", useUnmergedTree = true).assertExists()
    }

    @Test
    fun calculateSimpleExpressionDisplaysResult() {
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("=").performClick()
        composeTestRule.onNodeWithText("Result: 8", useUnmergedTree = true).assertExists()
    }
}