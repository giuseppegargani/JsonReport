package com.example.instrumentedreport

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject
import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/* Alcuni appunti
    Log.d("giuseppeRisultati", "nuovo TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto successo e nome ${description?.methodName} e nome ${javaClass.simpleName} e package ${ javaClass.`package`?.name}")
        println("nuovo TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto successo e nome classe ${description?.className}")
 */

// Qui semplicemente si creano dei singoli eventi di base atomici che poi vengono inviati ad un metodo in una altra classe (in un elenco di eventi singoli)
//Tutto questo per una cosa che puo' essere espressa in pochissime righe
//Si deve vedere se questo e' l'approccio migliore (istanze di questa classe vengonoo lanciate all'interno della classe piu' generale
//per quanto riguarda il controllo sulla tipizzazione qui si DEVE verificare che i singoli eventi abbiano valori di base e controllo di tipi!!!
//All'interno dei singoli eventi vengono anche registrati e modificati i campi relativi a pacchetto e nome classe rispettivamente!!! (c'e' bisogno di una istanza di Context)
//Per la istanza di context si usa InstrumentationRegistry!!!!! (nome classe filtrata con Regex)

/* CONTROLLO TIPI:
    Nome del metodo (sempre presente) e sempre una stringa (quindi si puo' forzare con il Double Bang
    CustomSuccessWord (e Failure) hanno comunque dei valori di default
 */

open class SingleTestWatcher(var customSuccessWord: String = TestResultStatus.SUCCESS.toString(), var customFailureWord: String = TestResultStatus.SUCCESS.toString()): TestWatcher() {

    init {
        Log.d("giuseppeCustom", " INIT customSuccessWord $customSuccessWord e $customFailureWord")
    }

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
        Log.d("giuseppeCustom", " customSuccessWord $customSuccessWord")
        //val singleTest = description?.let { SingleTest(it.methodName!!, customSuccessWord) }
        val singleTest: LinkedHashMap<String, String>? = description?.let { SingleTest("Nome del test", it.methodName!!, "Risultato", customSuccessWord).rendiMappa()}
        val singleObject = JSONObject()
        //json.putAll( data );
        //singleObject.put( )
        GlobalTWClass.addSingleTest(singleTest!!)
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        //val singleTest = description?.let { SingleTest(it.methodName!!, customFailureWord /*, e.toString()*/) }
        val singleTest: LinkedHashMap<String, String>? = description?.let {
            SingleTest("Nome del test", it.methodName!!, "Risultato", customFailureWord).rendiMappa() }
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