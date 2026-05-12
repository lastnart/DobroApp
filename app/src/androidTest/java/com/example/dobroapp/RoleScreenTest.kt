package com.example.dobroapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class RoleScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun roleScreen_isShownOnLaunch() {
        composeRule.onNodeWithText("ДоброРядом").assertIsDisplayed()
        composeRule.onNodeWithText("Мне нужна\nпомощь").assertIsDisplayed()
        composeRule.onNodeWithText("Я хочу\nпомогать").assertIsDisplayed()
    }
}