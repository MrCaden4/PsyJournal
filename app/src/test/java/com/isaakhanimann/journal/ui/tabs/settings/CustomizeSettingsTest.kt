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
import com.isaakhanimann.journal.ui.main.navigation.JournalTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.SaferUseTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.SettingsTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.StatsTopLevelRoute
import com.isaakhanimann.journal.ui.main.navigation.shownTopLevelRoutes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomizeSettingsTest {

    private val allRoutes = listOf(
        StatsTopLevelRoute, JournalTopLevelRoute, DrugsTopLevelRoute, SaferUseTopLevelRoute, SettingsTopLevelRoute
    )

    @Test
    fun everythingIsShownByDefault() {
        val settings = CustomizeSettings()
        assertTrue(settings.isFeedbackShown)
        allRoutes.forEach { assertTrue(it.toString(), settings.isTabShown(it)) }
    }

    @Test
    fun eachSwitchHidesItsOwnTab() {
        assertEquals(false, CustomizeSettings(isStatsTabShown = false).isTabShown(StatsTopLevelRoute))
        assertEquals(true, CustomizeSettings(isStatsTabShown = false).isTabShown(DrugsTopLevelRoute))
        assertEquals(false, CustomizeSettings(isDrugsTabShown = false).isTabShown(DrugsTopLevelRoute))
        assertEquals(false, CustomizeSettings(isSaferTabShown = false).isTabShown(SaferUseTopLevelRoute))
    }

    @Test
    fun journalAndSettingsCanNotBeHidden() {
        val everythingOff = CustomizeSettings(
            isStatsTabShown = false, isDrugsTabShown = false, isSaferTabShown = false, isFeedbackShown = false
        )
        assertTrue(everythingOff.isTabShown(JournalTopLevelRoute))
        assertTrue(everythingOff.isTabShown(SettingsTopLevelRoute))
        assertEquals(listOf("Journal", "Settings"), shownTopLevelRoutes(everythingOff).map { it.name })
    }

    @Test
    fun theNavigationBarKeepsTheOrderOfTheTabs() {
        val withoutDrugs = CustomizeSettings(isDrugsTabShown = false)
        assertEquals(listOf("Stats", "Journal", "Safer", "Settings"), shownTopLevelRoutes(withoutDrugs).map { it.name })
        assertEquals(
            listOf("Stats", "Journal", "Drugs", "Safer", "Settings"),
            shownTopLevelRoutes(CustomizeSettings()).map { it.name }
        )
    }

    @Test
    fun onlyTabsThatJustGotHiddenCount() {
        val before = CustomizeSettings(isStatsTabShown = false)
        val after = CustomizeSettings(isStatsTabShown = false, isSaferTabShown = false)
        assertEquals(listOf<Any>(SaferUseTopLevelRoute), after.tabsHiddenSince(before))
        // showing a tab again, or changing nothing, hides none
        assertEquals(emptyList<Any>(), before.tabsHiddenSince(after))
        assertEquals(emptyList<Any>(), after.tabsHiddenSince(after))
        // the Feedback section is not a tab
        assertEquals(emptyList<Any>(), CustomizeSettings(isFeedbackShown = false).tabsHiddenSince(CustomizeSettings()))
    }
}
