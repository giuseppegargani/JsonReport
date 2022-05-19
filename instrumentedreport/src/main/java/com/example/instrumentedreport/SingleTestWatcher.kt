package com.example.instrumentedreport

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.jar.Attributes

/* Alcuni appunti
    Log.d("giuseppeRisultati", "nuovo TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto successo e nome ${description?.methodName} e nome ${javaClass.simpleName} e package ${ javaClass.`package`?.name}")
        println("nuovo TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto successo e nome classe ${description?.className}")
 */

open class SingleTestWatcher: TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)

        //vengono modificati i nomi correnti delle classi per ogni file
        val context= InstrumentationRegistry.getInstrumentation().targetContext
        val packName = context.packageName
        val localClassName = description?.className?.replace("$packName.", "")
        //Log.d("giuseppeNome", "nome classe test ${description?.className} package: $packName e classe filtrata $localClassName")
        GlobalTWClass.actualClass = localClassName!!
        GlobalTWClass.pckgDenomination = packName
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.SUCCESSFUL) }
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.FAILED, e.toString()) }
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    override fun skipped(e: AssumptionViolatedException?, description: Description?) {
        super.skipped(e, description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.FAILED, e.toString()) }
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    override fun finished(description: Description?) {
        super.finished(description)
    }

}