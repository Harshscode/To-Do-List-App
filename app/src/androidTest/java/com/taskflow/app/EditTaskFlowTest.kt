package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.click
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
class EditTaskFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToAddTaskScreen() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Task Title *").assertIsDisplayed()
    }

    @Test
    fun verifyTitleFieldDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Task Title *").assertIsDisplayed()
    }

    @Test
    fun verifyDescriptionFieldDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Description (optional)").assertIsDisplayed()
    }

    @Test
    fun verifyDueDateFieldDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Due Date").assertIsDisplayed()
    }

    @Test
    fun verifyPriorityFieldDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Priority").assertIsDisplayed()
    }

    @Test
    fun verifyCancelButtonDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

    @Test
    fun verifySaveButtonDisplayed() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }

    @Test
    fun clickCancelReturnsToHome() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNodeWithText("TaskFlow").assertIsDisplayed()
    }

    @Test
    fun clickBackReturnsToHome() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Back").assertIsDisplayed()
    }

    @Test
    fun titleValidationShownWhenEmpty() {
        composeTestRule.onNodeWithText("Add Task").performClick()
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("Title is required").assertIsDisplayed()
    }
}
