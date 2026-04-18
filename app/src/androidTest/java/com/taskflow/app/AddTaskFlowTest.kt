package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.clickable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddTaskFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyHomeScreenDisplayed() {
        composeTestRule.onNodeWithText("TaskFlow").assertIsDisplayed()
    }

    @Test
    fun verifyTodayTabDisplayed() {
        composeTestRule.onNodeWithText("Today").assertIsDisplayed()
    }

    @Test
    fun verifyUpcomingTabDisplayed() {
        composeTestRule.onNodeWithText("Upcoming").assertIsDisplayed()
    }

    @Test
    fun verifyCompletedTabDisplayed() {
        composeTestRule.onNodeWithText("Completed").assertIsDisplayed()
    }

    @Test
    fun verifyEmptyStateDisplayed() {
        composeTestRule.onNodeWithText("No today tasks").assertIsDisplayed()
    }

    @Test
    fun verifyFabDisplayed() {
        composeTestRule.onNodeWithText("Add Task").assertIsDisplayed()
    }

    @Test
    fun clickFabNavigatesToAddTask() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Add Task").assertIsDisplayed()
    }
}
