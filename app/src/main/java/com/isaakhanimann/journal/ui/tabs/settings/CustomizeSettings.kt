/*
 * Copyright (c) 2026. MrCaden4.
 * This file is part of PsyJournal, a fork of PsychonautWiki Journal.
 *
 * PsyJournal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * PsyJournal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PsyJournal.  If not, see https://www.gnu.org/licenses/gpl-3.0.en.html.
 */

package com.isaakhanimann.journal.ui.tabs.settings

import com.isaakhanimann.journal.ui.main.navigation.DrugsTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.SaferUseTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.StatsTopLevelRoute

// the Customize settings: which optional parts of the app are shown. Everything is shown until it's turned off.
data class CustomizeSettings(
    val isStatsTabShown: Boolean = true,
    val isDrugsTabShown: Boolean = true,
    val isSaferTabShown: Boolean = true,
    val isFeedbackShown: Boolean = true,
) {
    // Journal and Settings have no switch, so there is always a way back to the journal and to these settings
    fun isTabShown(route: Any): Boolean = when (route) {
        StatsTopLevelRoute -> isStatsTabShown
        DrugsTopLevelRoute -> isDrugsTabShown
        SaferUseTopLevelRoute -> isSaferTabShown
        else -> true
    }

    // the tab routes that were shown with the settings before and are hidden with these
    fun tabsHiddenSince(before: CustomizeSettings): List<Any> =
        optionalTabRoutes.filter { before.isTabShown(it) && !isTabShown(it) }

    companion object {
        private val optionalTabRoutes = listOf(StatsTopLevelRoute, DrugsTopLevelRoute, SaferUseTopLevelRoute)
    }
}
