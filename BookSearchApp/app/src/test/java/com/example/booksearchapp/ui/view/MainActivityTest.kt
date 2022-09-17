package com.example.booksearchapp.ui.view

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


class MainActivityTest {
    @RunWith(RobolectricTestRunner::class)
    class MainActivityTest {

        @Test
        fun test_activitiy_state() {
            val controller = Robolectric.setupActivity(MainActivity::class.java)
            val activityState = controller.lifecycle.currentState.name
            assertThat(activityState).isEqualTo("RESUMED")
        }
    }
}