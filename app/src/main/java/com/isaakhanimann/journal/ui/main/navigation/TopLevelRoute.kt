package com.isaakhanimann.journal.ui.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.isaakhanimann.journal.ui.tabs.settings.CustomizeSettings
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

val topLevelRoutes = listOf(
    TopLevelRoute("Stats", StatsTopLevelRoute, Icons.Filled.BarChart, Icons.Outlined.BarChart),
    TopLevelRoute("Journal", JournalTopLevelRoute, Icons.Filled.Book, Icons.Outlined.Book),
    TopLevelRoute("Drugs", DrugsTopLevelRoute, Icons.Filled.Medication, Icons.Outlined.Medication),
    TopLevelRoute("Safer", SaferUseTopLevelRoute, Icons.Filled.HealthAndSafety, Icons.Outlined.HealthAndSafety),
    TopLevelRoute("Settings", SettingsTopLevelRoute, Icons.Filled.Settings, Icons.Outlined.Settings)
)

// the tabs in the navigation bar: the Customize settings can hide Stats, Drugs and Safer, never Journal or Settings.
// Every graph stays in the NavHost, so a hidden tab's screens can still be opened from elsewhere.
fun shownTopLevelRoutes(settings: CustomizeSettings) = topLevelRoutes.filter { settings.isTabShown(it.route) }

@Serializable
object StatsTopLevelRoute

@Serializable
object JournalTopLevelRoute

@Serializable
object DrugsTopLevelRoute

@Serializable
object SaferUseTopLevelRoute

@Serializable
object SettingsTopLevelRoute