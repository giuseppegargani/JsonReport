package com.example.instrumentedreport

data class CompositeTestClass (var testClasses: MutableList<SingleClass>)

data class SingleClass(var singleTests: MutableList<SingleTest>)

data class SingleTest ( var testName: String, var outcome: TestResultStatus, var otherData: String? = null )

enum class TestResultStatus {
    SUCCESSFUL, ABORTED, FAILED, DISABLED
}