package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifySearchFieldDisplayed() {
        composeTestRule.onNodeWithText("Search tasks").assertIsDisplayed()
    }

    @Test
    fun searchFieldAcceptsInput() {
        composeTestRule.onNodeWithText("Search tasks").performClick()
        composeTestRule.onNodeWithText("Search tasks").performTextInput("test")
        composeTestRule.onNodeWithText("Search tasks").assertIsDisplayed()
    }

    @Test
    fun verifyTodayTabCount() {
        composeTestRule.onNodeWithText("Today (0)").assertIsDisplayed()
    }

    @Test
    fun verifyUpcomingTabCount() {
        composeTestRule.onNodeWithText("Upcoming (0)").assertIsDisplayed()
    }

    @Test
    fun verifyCompletedTabCount() {
        composeTestRule.onNodeWithText("Completed (0)").assertIsDisplayed()
    }
}
