package com.example.instrumentedreport

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//I need to add the documentation for every class

const val eccomi = "campo"

data class CompositeTestClass (var package_name:String, var test_classes_list: MutableList<SingleClass>)

data class SingleClass( var test_class_name:String,  var tests_list: MutableList<SingleTest>)

@Serializable
data class SingleTest (@SerializedName(eccomi) var testName: String, @SerializedName("Risultato") var outcome: String, var otherData: String? = null )

enum class TestResultStatus {
    SUCCESS, ABORTED, FAILURE, SKIPPED
}

@Serializable
data class MyClass(@SerialName(eccomi) val s1: Int)