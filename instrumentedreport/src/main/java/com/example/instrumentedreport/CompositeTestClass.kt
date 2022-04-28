package com.example.instrumentedreport

//I need to add the documentation for every class

data class CompositeTestClass (var testClasses: MutableList<SingleClass>)

data class SingleClass(var testClassName: MutableList<SingleTest>)

data class SingleTest ( var testName: String, var outcome: TestResultStatus, var otherData: String? = null )

enum class TestResultStatus {
    SUCCESSFUL, ABORTED, FAILED, DISABLED
}