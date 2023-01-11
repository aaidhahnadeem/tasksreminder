package com.aaidhah.nadeem

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.aaidhah.nadeem.data.datastore.ThemeManager
import com.aaidhah.nadeem.navigation.NavGraph
import com.aaidhah.nadeem.ui.theme.AppTheme
import com.aaidhah.nadeem.ui.theme.AppTheme.AppTheme
import com.aaidhah.nadeem.ui.theme.darkColors
import com.aaidhah.nadeem.ui.theme.lightColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var themeManager: ThemeManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()

             //Observe the Theme Mode
            observeThemeMode()
        }
    }

    @Composable
    fun Main() {

        /**
         * Check if the UI Mode is in Dark Mode
         */
        val darkMode by themeManager.uiModeFlow.collectAsState(initial = isSystemInDarkTheme())

        /**
         * Change UI Mode on toggle
         */
        val toggleTheme: () -> Unit = {
            lifecycleScope.launch {
                themeManager.setDarkMode(!darkMode)
            }
        }

        val colors = if (darkMode) darkColors() else lightColors()

        AppTheme(colors = colors) {
            Surface(color = AppTheme.colors.background) {
                SetStatusBarColor()
                NavGraph(toggleTheme)
            }
        }
    }

    private fun observeThemeMode() {
        lifecycleScope.launchWhenStarted {
            themeManager.uiModeFlow.collect {
                val mode = when (it) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}

@Composable
fun SetStatusBarColor() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val color = AppTheme.colors.background

    /**
     *  Update all of the system bar colors to be transparent, and use
     *  dark icons if we're in light theme
     */
    SideEffect {
        systemUiController.setStatusBarColor(color = color)
    }
}