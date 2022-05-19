package com.example.instrumentedreport

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

@RunWith(AndroidJUnit4::class)
open class GlobalUTClass: TestWatcher() {

    companion object {
        //variabile della lista dei tests
        var listaSingleTests = mutableListOf<SingleTest>()
        //questo e' l'oggetto che viene letto (se il file e' gia' presente)
        var globalTestObject: CompositeTestClass? = null

        var pckgDenomination: String = ""
        var actualClass: String = ""

        @BeforeClass
        @JvmStatic
        fun beforeClass(){
           println("valore di globalTestObject ${globalTestObject}")
            listaSingleTests = mutableListOf()
            //verifica se per caso esiste già un file json con i risultati
            //globalTestObject = verifyPresence()   //rimettere!!!!!!!!!!!!!!!!!!!!!!!!
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            //cerchiamo di prendere il context
            //createJson()      //rimettere!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
            //val appContext = ApplicationProvider.getApplicationContext<Context>()
            //val path1: File = otherContext.getExternalStorageDirectory()
            //val appContext = InstrumentationRegistry.getInstrumentation().context
            //val otherContext  = RuntimeEnvironment.getApplication().applicationContext
            val otherContext: Context = ApplicationProvider.getApplicationContext()
            val path1: File? = otherContext.applicationContext.getExternalFilesDir(null)
            val path: File = otherContext.getExternalFilesDir(null)!!
            //val directoryFile = otherContext.getDir(Environment.DIRECTORY_DOCUMENTS, Context.MODE_PRIVATE)
            println("Dopo il test!!!!!!! $actualClass $pckgDenomination e file: ${path} e ${otherContext.filesDir} ")
        }

        //if a globalTestReport already exists return a global file to the companion object corresponding variable
        fun verifyPresence(): CompositeTestClass? {
            //local variable
            var localObject: CompositeTestClass? = null
            //check if exists already a file
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            //we'll be able to change the fileName
            val file = File(path, "JsonTestReport.json")

            //Verifica se esiste e modifica ramo
            /*if (file.exists()) {
                Log.d("giuseppeJson", "Il file esiste ed e' ")
                var classeSingola=leggiJson(file)
                Log.d("giuseppeJson", " Oggetto json letto da file ${classeSingola?.listaTests?.get(0)?.nameTest}")

                //si deve aggiungere o modificare (verifica che sia presente un elemento della lista!!)
                //si crea un elemento inventato (singolo elemento)
                val singoloModifica = SingleTest("primo Test","Failure")
                val singoloAggiuuntivo = SingleTest("Terzo Test", "Success" )

                //SE ESATTAMENTE LO STESSO lascia invariato, altrimenti modifica, oppure se il nome diverso aggiungi
                //classeSingola?.listaTests?.map { if(it.nameTest == singoloModifica.nameTest) { it=singoloModifica } }
                //classeSingola= classeSingola?.listaTests?.map { if (it.nameTest==singoloModifica.nameTest) { it=singoloModifica} }
                classeSingola?.listaTests?.map { if(it.nameTest==singoloModifica.nameTest) { Log.d("giuseppeJson", "trovato un elemento che non si chiama come elemento aggiuntivo ma ${it.nameTest}");
                    it.outcome=singoloModifica.outcome; it.altriElementi=singoloModifica.altriElementi } }
                Log.d("giuseppeJson", "elemento modificato ${classeSingola?.listaTests?.get(0)}")

                // e aggiunge
                classeSingola?.listaTests?.add(singoloAggiuuntivo)
                Log.d("giuseppeJson", "Lista aggiornata ${classeSingola}")

            } else {
                //se non esiste crealo ex-novo
                //si prende il nome della classe e si scompone (si toglie il nome del modulo dalla classe)
                Log.d("giuseppeJson", "il file non esiste e va creato")

            }*/

            if(file.exists()){
                Log.d("giuseppeJson", "il file esiste gia' ")
                //if exists assign to the global variable
                localObject= leggiJson(file)
                println("Oggetto json letto da file ${localObject?.test_classes_list?.get(0)?.tests_list}")
            }
            else { println("file NON esiste gia'") }

            println("$localObject")
            return localObject
        }

        //add singleTest to the local list!!!!
        fun addSingleTest(singleTest: SingleTest){
            listaSingleTests.add(singleTest)
            println("lista: ${listaSingleTests} e nome: ${pckgDenomination} e ${actualClass}")
        }


        //Write the json file!!!
        fun createJson(){

            println("Object in entrata di create Json ${globalTestObject}")

            var actualGlobalClass: CompositeTestClass? = null

            //verifica se esiste o meno
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            println("nome directory $path")
            val file = File(path, "JsonTestReport.json")

            //val file = File(getApplicationContext<Context>().filesDir, "whatever.txt")

            //Verifica se esiste e modifica ramo
            /*if (file.exists()) {
                //si deve leggere ed assegnare a Json e leggere json
                Log.d("giuseppeJson", "Il file esiste ed e' ")
                var classeSingola=leggiJson(file)
                Log.d("giuseppeJson", " Oggetto json letto da file ${classeSingola?.listaTests?.get(0)?.nameTest}")

                //si deve aggiungere o modificare (verifica che sia presente un elemento della lista!!)
                //si crea un elemento inventato (singolo elemento)
                val singoloModifica = SingleTest("primo Test","Failure")
                val singoloAggiuuntivo = SingleTest("Terzo Test", "Success" )

                //SE ESATTAMENTE LO STESSO lascia invariato, altrimenti modifica, oppure se il nome diverso aggiungi
                //classeSingola?.listaTests?.map { if(it.nameTest == singoloModifica.nameTest) { it=singoloModifica } }
                //classeSingola= classeSingola?.listaTests?.map { if (it.nameTest==singoloModifica.nameTest) { it=singoloModifica} }
                classeSingola?.listaTests?.map { if(it.nameTest==singoloModifica.nameTest) { Log.d("giuseppeJson", "trovato un elemento che non si chiama come elemento aggiuntivo ma ${it.nameTest}");
                    it.outcome=singoloModifica.outcome; it.altriElementi=singoloModifica.altriElementi } }
                Log.d("giuseppeJson", "elemento modificato ${classeSingola?.listaTests?.get(0)}")

                // e aggiunge
                classeSingola?.listaTests?.add(singoloAggiuuntivo)
                Log.d("giuseppeJson", "Lista aggiornata ${classeSingola}")

            } else {
                //se non esiste crealo ex-novo
                //si prende il nome della classe e si scompone (si toglie il nome del modulo dalla classe)
                Log.d("giuseppeJson", "il file non esiste e va creato")

            }*/
            //se il file esiste modificalo altrimenti crealo di nuovo!!!

            //la lista nel nostro caso potrebbe essere una lista di elementi della Data class
            /*val list = listOf("String1", "String2")
            val gson = Gson()
            //da una stringa json ad un oggetto Json attraverso la serializzazione!!!!
            val jsonString = gson.toJson(list)
            val sType = object : TypeToken<List<String>>() { }.type
            val otherList = gson.fromJson<List<String>>(jsonString, sType)
            Log.d("giuseppeJson", "otherList $otherList")*/

            //VERIFICA CHE VEDE LA DIRECTORY Esterna di salvataggio dati
            /*val path2 = "src/test"
            val fileDirectory = File(path2)
            val files = fileDirectory.listFiles()
            files.map { Log.d("giuseppeLista", "ecco un file $it") }
            //Log.d("giuseppeLista", "lista dei files ${files.map {  }}")
            val file2 = File(path2, "risultati.txt")
            val absolutePath = file2.absolutePath
            Log.d("giuseppeJson", "ECCO LA PATH: $file2 e absolute $absolutePath")
            println(absolutePath)

            val file3 = File("src/resources/run.txt")
            file.parentFile.mkdirs()
            val writer = FileWriter(file3)
            writer.append("this is a test")
            writer.close()*/

            //un test singolo (con la data class)
            /*var elementoSingolo = SingleTest("primo Test", "Success")
            var secondoElemento = SingleTest("secondo test", "Success")
            var classeAttuale = SingleClass(mutableListOf(elementoSingolo, secondoElemento))

            var jsonString = Gson().toJson(elementoSingolo)
            var jsonStringLista = Gson().toJson(classeAttuale)
            var testRipreso = Gson().fromJson<SingleClass>(jsonStringLista, SingleClass::class.java)
            Log.d("giuseppeJson", "elemento jsonString $jsonString e lista $classeAttuale")
            Log.d("giuseppeJson", "elemento json $classeAttuale")
            Log.d("giuseppeJson", " e rintracciare un elemento da nested ${testRipreso.listaTests[1].nameTest}")*/

            /*val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")*/

            //qui si deve comporre il risultato (lista locale e globale!! (verifica se esiste una classe con il solito nome (se il file globale e' null allora scrivi da zero altrimenti aggiungi)
            if(globalTestObject ==null) {
                val actualTestClass = SingleClass(actualClass, listaSingleTests)
                actualGlobalClass = CompositeTestClass(pckgDenomination, mutableListOf(actualTestClass))
                println("globalObject e' null !!!!!!!!!!!!!! e $actualGlobalClass")
            }
            else {
                println("globalObject non e' null !!!!!!!!!!!!!! e ${globalTestObject}")
                //check if the test class already exists (otherwise simply add)
                //if(actualGlobalClass!!.test_classes_list!!.map{ if(it.test_class_name== actualClass) } )
                if(GlobalTWClass.globalTestObject!!.test_classes_list!!.any{ it.test_class_name== actualClass }) {
                    println(" gia' effettuato test di questa classe")
                    GlobalTWClass.globalTestObject!!.test_classes_list!!.filter { it.test_class_name== actualClass }.forEach { it.tests_list= listaSingleTests
                    }
                    actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, globalTestObject!!.test_classes_list)
                }
                else {
                    println("File esiste ma non gia' effettuato test di questa classe")
                    //we create a new SingleClass (adding also the classname)
                    val actualTestClass = SingleClass(actualClass, listaSingleTests)
                    //we create a new list of test classes
                    val newClassesList = globalTestObject!!.test_classes_list
                    //and we add a new element
                    newClassesList.add(actualTestClass)
                    //we create a new CompositeTestClass
                    actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, newClassesList)
                }

            }

            //convert in Json
            var finalJsonString = Gson().toJson(actualGlobalClass)

            //writing to file
            val stream = FileOutputStream(file)
            stream.use { stream ->
                stream.write(finalJsonString.toByteArray())
            }
        }

        //si recupera un oggetto Json da un file!!!
        fun leggiJson (file: File): CompositeTestClass? {
            var txt: String? = null
            var compositeTestClass:CompositeTestClass? = null

            try {
                val reader = FileReader(file)
                txt = reader.readText()
                println("stringa letta $txt e file $file")
                reader.close()
            } catch (e: IOException) {
                // Exception
                e.printStackTrace()
            }
            //se text diverso da null convertilo in oggetto Json (altrimenti restituira' null come singleClass)
            txt?.let {
                compositeTestClass = Gson().fromJson<CompositeTestClass>(it, CompositeTestClass::class.java)
            }

            return compositeTestClass
        }

    }

    @get:Rule
    public val watchman: TestRule? = SingleUnitTestWatcher()
}