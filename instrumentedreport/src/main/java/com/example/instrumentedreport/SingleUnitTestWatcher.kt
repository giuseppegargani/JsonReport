package com.example.instrumentedreport

import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description

open class SingleUnitTestWatcher: TestWatcher() {

    //da qui prende alcuni dati per le variabili perche' gli altri sono metodi statici
    override fun starting(description: Description?) {
        super.starting(description)

        //we find the packagename from the classname of the test and using Regex
        val workName = description?.className?.substringAfter('.')?.substringAfter('.')?.substringAfter('.')
        val classNme = description?.className?.substringAfterLast('.')
        var subprojectName = ""
        //if the length of the intermediateName is bigger than the simple classname and it contains a dot
        if ((workName?.length!! > classNme?.length!!)&&(workName.contains('.'))) { subprojectName = workName?.substringBefore('.') }
        val pckgName = description?.className?.replace(".$workName", "")

        //da togliere dopo (per debug)
        println("Ecco il package ${description?.testClass} e altro ${workName} e $pckgName  ${description?.className} e $classNme e subproject $subprojectName ")
        GlobalUTClass.actualClass = workName
        GlobalUTClass.pckgDenomination = pckgName!!
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.SUCCESSFUL) }
        GlobalUTClass.addSingleTest(singleTest!!)
        println("DENTRO TESTWATCHER E RISULTATO: $singleTest")
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.FAILED, e.toString()) }
        GlobalUTClass.addSingleTest(singleTest!!)
    }

    override fun skipped(e: AssumptionViolatedException?, description: Description?) {
        super.skipped(e, description)
        val singleTest = description?.let { SingleTest(it.methodName!!, TestResultStatus.FAILED, e.toString()) }
        GlobalUTClass.addSingleTest(singleTest!!)
    }

    override fun finished(description: Description?) {
        super.finished(description)
    }
}