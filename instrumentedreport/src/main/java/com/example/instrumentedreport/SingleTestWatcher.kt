package com.example.instrumentedreport

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description

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
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.SUCCESS) }
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.FAILURE/*, e.toString()*/) }
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    //VIENE SKIPPATO SE UNA ASSUNZIONE NON SI REALIZZA
    override fun skipped(e: AssumptionViolatedException?, description: Description?) {
        super.skipped(e, description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
    }

}