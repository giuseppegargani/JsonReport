package com.example.instrumentedreport

//I need to add the documentation for every class

data class CompositeTestClass (var package_name:String, var test_classes_list: MutableList<SingleClass>)

data class SingleClass(var test_class_name:String,  var tests_list: MutableList<SingleTest>)

data class SingleTest ( var testName: String, var outcome: String, var otherData: String? = null )

enum class TestResultStatus {
    SUCCESS, ABORTED, FAILURE, SKIPPED
}


