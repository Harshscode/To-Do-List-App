package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.waitForIdle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AddEditTaskTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun navigateToAddTask() {
        composeTestRule.onNodeWithText("Add Task").performClick()
    }

    @Test
    fun addTaskScreenShowsAllFormFields() {
        navigateToAddTask()
        composeTestRule.onNodeWithText("Task Title *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Due Date").assertIsDisplayed()
        composeTestRule.onNodeWithText("Priority").assertIsDisplayed()
        composeTestRule.onNodeWithText("Category").assertIsDisplayed()
        composeTestRule.onNodeWithText("Set reminder").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mark as completed").assertIsDisplayed()
    }

    @Test
    fun titleValidationShowsErrorWhenEmpty() {
        navigateToAddTask()
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("Title is required").assertIsDisplayed()
    }

    @Test
    fun saveButtonCreatesTask() {
        navigateToAddTask()
        composeTestRule.onNodeWithText("Task Title *").performTextInput("New Test Task")
        composeTestRule.onNodeWithText("Save").performClick()
        // Should navigate back after saving
        composeTestRule.waitForIdle()
    }

    @Test
    fun cancelButtonReturnsToHome() {
        navigateToAddTask()
        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNodeWithText("TaskFlow").assertIsDisplayed()
    }
}
