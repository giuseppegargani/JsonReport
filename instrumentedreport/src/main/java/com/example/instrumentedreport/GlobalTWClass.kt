package com.example.instrumentedreport

/* CODICE PRECEDENTE SPIEGATO:

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

 */

/* ALTRO CODICE:
    //da file o Uri
        /*fun ottieniLista(uri: Uri): MutableList<String> {

            var listaRighe: MutableList<String> = mutableListOf()

            //var fileDirectory =  getExternalFilesDir("Documents")
            //var externalFile = File(fileDirectory, "salvataggioPaziente.txt")
            //Log.d("giuseppeLettura", "percorso ${externalFile}")

            //val bufferedInputStream = BufferedInputStream( resources.openRawResource(R.raw.filename))
            val inputStream = getInputStreamByUri(context,uri)
            val bufferedInputStream = BufferedInputStream( inputStream  )

            //val bufferedReader = new BufferedReader(Paths.get("/resources/students.csv"));
            //val bufferedInputStream1 = BufferedInputStream( Paths.get(/resources/))
            val bufferedReader = BufferedReader(InputStreamReader(bufferedInputStream))
            try {
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    listaRighe.add(line!!)
                    //Log.d("giuseppeLettura", "valore linea ${line!!}\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return listaRighe
        }*/

 */

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

open class GlobalTWClass(var customSuccess: String = TestResultStatus.SUCCESS.toString(), var customFailure: String = TestResultStatus.FAILURE.toString()) {

    init {
        Log.d("giuseppeCustom", " INIT Global customSuccessWord $customSuccess e $customFailure")
    }

    //because some methods are static
    companion object {

        //queste sono le variabili custom
        var customPath = ""
        var customName = "beppeReportJson"

        //variabile della lista dei singoli tests (data class SingleTest)
        var listaSingleTests = mutableListOf<Map<String, String>>()
        //questo e' l'oggetto che viene letto (se il file e' gia' presente)
        var globalTestObject: Map<Any, Any>? = null

        var pckgDenomination: String = ""
        var actualClass: String = ""

        @BeforeClass
        @JvmStatic
        fun beforeClass(){
            //Log.d("giuseppeCheck", "valore di globalTestObject $globalTestObject")
            //Log.d("giuseppeCostruttore", "Costruttore secondario e'")
            listaSingleTests = mutableListOf()

            //verifica se per caso esiste già un file json con i risultati
            globalTestObject = verifyPresence()
        }

        //Si devono mettere come static!!!
        @AfterClass
        @JvmStatic
        fun afterClass() {
            createJson()
        }

        //if a globalTestReport already exists return a global file to the companion object corresponding variable
        //se il file esiste lo carica come oggetto (peer modificarlo)
        fun verifyPresence(): Map<Any, Any>? {
            //local variable
            var localObject: Map<Any, Any>? = null

            //check if exists already a file
            val context= InstrumentationRegistry.getInstrumentation().targetContext

            //val path: File = context.getExternalFilesDir(null)!!
            //val path: File = File(context.getExternalFilesDir(null)!!.toString()+ customPath)
            val path: File = File(context.getExternalFilesDir(null)!!.toString()+customPath)

            //we'll be able to change the fileName
            val file = File(path, "$customName.json")

            Log.d("giuseppeDyn", "PATH: $path e file: $file")

            if(file.exists()){
                //if exists assign to the global variable
                localObject=leggiJson(file)
                Log.d("giuseppeJson", "il file esiste gia'  $localObject")
            }
            else { Log.d("giuseppeJson", "il file NON esiste gia'") }

            return localObject
        }

        //add singleTest to the local list!!!!
        fun addSingleTest(singleTest: Map<String,String>){
            listaSingleTests.add(singleTest)
        }

        //Write the json file!!!
        fun createJson(){

            var actualGlobalClass: Map<Any, Any>? = null

            //verifica se esiste o meno
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            //val path: File = context.getExternalFilesDir(null)!!

            val path: File = File(context.getExternalFilesDir(null)!!.toString()+customPath)
            if(!path.exists()) { path.mkdir()}

            Log.d("giuseppeFile", "path $path")
            val file = File(path, "$customName.json")

            //qui si deve comporre il risultato (lista locale e globale!! (verifica se esiste una classe con il solito nome (se il file globale e' null allora scrivi da zero altrimenti aggiungi)
            //se null vuol dire che non esisteva prima e quindi si crea velocemente!!! come? classe
            if(globalTestObject==null) {
                //si puo' anche eliminare e fare direttamente una mappa (con valori CUSTOM)
                val actualTestClass = SingleClass("NomeClasse", actualClass, "Tests List", listaSingleTests).rendiMappa()
                actualGlobalClass = CompositeTestClass("Nome pacchetto", pckgDenomination, "Test Classes", mutableListOf(actualTestClass)).rendiMappa()
                Log.d("giuseppeJson", "globalObject e' null !!!!!!!!!!!!!! e $actualGlobalClass")
            }
            else {
                /*Log.d("giuseppeJson", "globalObject non e' null !!!!!!!!!!!!!! e $globalTestObject")
                //check if the test class already exists (otherwise simply add)
                if(globalTestObject!!.test_classes_list!!.any{ it.test_class_name== actualClass }) {
                    globalTestObject!!.test_classes_list!!.filter { it.test_class_name== actualClass }.forEach { it.tests_list= listaSingleTests }
                    actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, globalTestObject!!.test_classes_list)
                }
                else {
                    //we create a new SingleClass (adding also the classname)
                    val actualTestClass = SingleClass(actualClass, listaSingleTests)
                    //we create a new list of test classes
                    val newClassesList = globalTestObject!!.test_classes_list
                    //and we add a new element
                    newClassesList.add(actualTestClass)
                    //we create a new CompositeTestClass
                    actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, newClassesList)
                }*/
            }

            //convert in Json
            var finalJsonString = Gson().toJson(actualGlobalClass)

            Log.d("giuseppeDyn", "file: $file FINALJSON $finalJsonString e tipo: ${finalJsonString is String}")

            //writing to file
            val stream = FileOutputStream(file)
            stream.use { stream ->
                stream.write(finalJsonString.toByteArray())
            }
        }

        //si recupera un oggetto Json da un file!!!
        fun leggiJson (file: File): Map<Any, Any>? {
            var txt: String? = null
            var compositeTestClass:Map<Any, Any>? = null

            try {
                val reader = FileReader(file)
                txt = reader.readText()
                reader.close()
            } catch (e: IOException) {
                // Exception
                e.printStackTrace()
            }
            //se text diverso da null convertilo in oggetto Json (altrimenti restituira' null come singleClass)
            txt?.let {
                compositeTestClass = Gson().fromJson<Map<Any, Any>>(it, Map::class.java)
            }

            Log.d("giuseppeDyn", "Valore di Json: $compositeTestClass")

            return compositeTestClass
        }

    }

    @get:Rule
    public val watchman: TestRule? = SingleTestWatcher(customSuccess, customFailure)

}
