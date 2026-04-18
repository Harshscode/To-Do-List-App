package com.taskflow.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun settingsScreenShowsAllSections() {
        // Navigate to Settings by clicking the Settings icon in top bar
        composeTestRule.onNodeWithContentDescription("Settings").performClick()

        // Verify all section headers
        composeTestRule.onNodeWithText("APPEARANCE").assertIsDisplayed()
        composeTestRule.onNodeWithText("TASK LIST PREFERENCES").assertIsDisplayed()
        composeTestRule.onNodeWithText("DATA").assertIsDisplayed()

        // Verify Appearance section items
        composeTestRule.onNodeWithText("Theme").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dynamic color").assertIsDisplayed()

        // Verify Task List Preferences section items
        composeTestRule.onNodeWithText("Default tab").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sort order").assertIsDisplayed()
        composeTestRule.onNodeWithText("Show completed tasks count badge").assertIsDisplayed()

        // Verify Data section items
        composeTestRule.onNodeWithText("Export tasks").assertIsDisplayed()
        composeTestRule.onNodeWithText("Clear completed tasks").assertIsDisplayed()
    }
}
