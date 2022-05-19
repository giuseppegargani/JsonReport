package com.example.instrumentedreport

import org.junit.rules.TestWatcher
import org.junit.runner.Description

open class SingleUnitTestWatcher: TestWatcher() {

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.SUCCESSFUL) }
        //GlobalTWClass.addSingleTest(singleTest!!)
        println("DENTRO TESTWATCHER E RISULTATO: $singleTest")
    }
}