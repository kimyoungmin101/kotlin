package com.example.booksearchapp.ui.view

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest_one {

//    private lateinit var activityScenario: ActivityScenario<MainActivity>
//    // ActivityScenario를 사용하면 Test용 Activity를 쉽게 생성 가능하다.
//
//    @Before
//    fun setUp() {
//        activityScenario = ActivityScenario.launch(MainActivity::class.java)
//    }
//
//    @After
//    fun tearDown() {
//        activityScenario.close()
//    }

    @get:Rule
    val scenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    // scenarioRule에 의해 activity가 자동으로 생성되고 파괴가 된다. !! 굳이 위처럼 Before After 설정 안해주어도 된다.ㅇ

    @Test
    @SmallTest
    fun test_activitiy_state() {
        val activityState = scenarioRule.scenario.state.name
        // ActivityScenario의 상태가 Resume인지 확인하는 간단한 코드 !!
        assertThat(activityState).isEqualTo("RESUMED")
    }

}