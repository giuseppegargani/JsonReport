package com.example.jsonreport

import com.example.instrumentedreport.GlobalTWClass
import com.example.instrumentedreport.GlobalUTClass
import org.junit.Test

import org.junit.Assert.*
import org.junit.Ignore

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: GlobalUTClass() {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    @Ignore
    fun addition_isCorrect2() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun addition_isCorrect3() {
        assertEquals(4, 2 + 2)
    }
}