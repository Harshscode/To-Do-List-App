package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunchesWithTaskFlowTitle() {
        composeTestRule.onNodeWithText("TaskFlow").assertIsDisplayed()
    }

    @Test
    fun homeScreenShowsTodayUpcomingCompletedTabs() {
        composeTestRule.onNodeWithText("Today").assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming").assertIsDisplayed()
        composeTestRule.onNodeWithText("Completed").assertIsDisplayed()
    }

    @Test
    fun fabNavigatesToAddTaskScreen() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Add Task").assertIsDisplayed()
    }

    @Test
    fun emptyStateDisplaysCorrectly() {
        composeTestRule.onNodeWithText("No today tasks").assertIsDisplayed()
    }

    @Test
    fun tabNavigationWorks() {
        // Click on Upcoming tab
        composeTestRule.onNodeWithText("Upcoming").performClick()
        composeTestRule.onNodeWithText("No upcoming tasks").assertIsDisplayed()

        // Click on Completed tab
        composeTestRule.onNodeWithText("Completed").performClick()
        composeTestRule.onNodeWithText("No completed tasks").assertIsDisplayed()

        // Click back to Today tab
        composeTestRule.onNodeWithText("Today").performClick()
        composeTestRule.onNodeWithText("No today tasks").assertIsDisplayed()
    }
}
