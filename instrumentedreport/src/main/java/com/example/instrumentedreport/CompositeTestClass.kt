package com.example.instrumentedreport

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//I need to add the documentation for every class

const val eccomi = "campo"

data class CompositeTestClass ()

/*//val nameClassKey: String = "Class Name", val nameClassValue: String, val eventsTestKey: String = "Tests Lists", val eventsTestValue: MutableList<Map<String,String>>
data class CompositeTestClass (var packageNameKey: String = "Package Name",  var packageNameValue:String, var classesListKey: String = "test classes list", var classesListValue: MutableList<LinkedHashMap<Any, Any>>){

    fun rendiMappa(): LinkedHashMap<Any, Any> {
        val listaFinale = linkedMapOf<Any,Any>(packageNameKey to packageNameValue, classesListKey to classesListValue )
        return listaFinale
    }

}

//data class SingleClass( var test_class_name:String,  var tests_list: MutableList<Map<String, String>>)

//Verifica in ingresso i tipi dei parametri!!! (tra cui lista di mappe Stringa, Stringa)
class SingleClass ( val nameClassKey: String = "Class Name", val nameClassValue: String, val eventsTestKey: String = "Tests Lists", val eventsTestValue: MutableList<LinkedHashMap<String,String>> ) {

    fun rendiMappa(): LinkedHashMap<Any, Any> {
        val listaFinale = linkedMapOf<Any,Any>(nameClassKey to nameClassValue, eventsTestKey to eventsTestValue )
        return listaFinale
    }
}

@Serializable
//data class SingleTest (@SerializedName(eccomi) var testName: String, @SerializedName("Risultato") var outcome: String, var otherData: String? = null )

//Corrispondente al singolo test e customizabile e il metodo restituisce una mappa (per json)
//verifica di tipo dei parametri (PARAGONE NON NECESSARIO tra elementi)
class SingleTest ( val nameTestKey: String = "Test Name", val nameTestValue: String, val eventTestKey: String = "Outcome", val eventTestValue: String  *//* AGGIUNTA ALTRI DATI*//*  ) {

    fun rendiMappa(): LinkedHashMap<String, String>  {
        val listaFinale = linkedMapOf<String, String>(nameTestKey to nameTestValue, eventTestKey to eventTestValue)
        return listaFinale
    }
}*/

enum class TestResultStatus {
    SUCCESS, ABORTED, FAILURE, SKIPPED
}

@Serializable
data class MyClass(@SerialName(eccomi) val s1: Int)