package com.example.jsonreport

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.instrumentedreport.GlobalTWClass

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.Exception
import java.nio.charset.StandardCharsets

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: GlobalTWClass() {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jsonreport", appContext.packageName)
    }

    @Test
    fun useAppContext2() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jsonreport", appContext.packageName)
    }

    @Test
    fun useAppContext3() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jsonreport", appContext.packageName)
    }
}
object DBExtrac {
    @Throws(IOException::class)
    @JvmStatic
    fun main(/*args: Array<String>*/) {
        Log.d("giuseppecomando", "ENTRATO DENTRO CUSTOM CLASS")
        var process:Process? = null
        try {
            process = Runtime.getRuntime().exec("adb shell ls")
            Log.d("giuseppecomando", "instanziato comando")
            println("instanziato un comando")
        }catch (exception: Exception) {
            Log.d("giuseppecomando", "NON instanziato comando")
            println("NON instanziato un comando")
        }
        PrintWriter(
            BufferedWriter(
                OutputStreamWriter(
                    process?.getOutputStream(),
                    StandardCharsets.UTF_8
                )
            )
        ).use { pw ->
            Log.d("giuseppecomando", "ENTRATO DENTRO CUSTOM CLASS2")
            pw.println("ls")
//            pw.println("run-as com.sk.shaft")
//            pw.println("cd files")
//            pw.println("cp file.db /sdcard/download/sample.db3")
//            pw.println("exit")
            pw.println("exit")
        }
        process = Runtime.getRuntime().exec("adb devices")
    }
}