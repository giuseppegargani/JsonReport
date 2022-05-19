package com.example.instrumentedreport

import android.util.Log
import org.junit.After
import org.junit.AfterClass
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class GlobalUTClass: TestWatcher() {

    companion object {

        @AfterClass
        @JvmStatic
        fun afterClass() {
            println("Dopo il test!!!!!!!")
        }

    }

    @get:Rule
    public val watchman: TestRule? = SingleUnitTestWatcher()
}