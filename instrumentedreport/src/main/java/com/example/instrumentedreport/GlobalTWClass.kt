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

/* GlobalTestObject viene caricato (dopo aver verificato la presenza) e corrisponde alla mappa completa
    Altre tre variabili: (lista singoli test, nome pacchetto, e cone classe Locale)
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
        var customName = "JsonTestReport"

        //variabile della lista dei singoli tests (data class SingleTest)
        var listaSingleTests = mutableListOf<LinkedHashMap<String, String>>()
        //questo e' l'oggetto che viene letto (se il file e' gia' presente)
        var globalTestObject: LinkedHashMap<Any, Any>? = null

        var pckgDenomination: String = ""
        var actualClass: String = ""

        @BeforeClass
        @JvmStatic
        fun beforeClass(){
            listaSingleTests = mutableListOf()
            //verifica se per caso esiste già un file json con i risultati (VERIFICA E PRENDE IL VALORE)
            //Se NON ESISTE GIA' il suo valore sara' null!!!!! NULLABLE  (un primo controllo) ma ci vogliono altri controlli aggiunti SULLA STRUTTURA!!
            globalTestObject = verifyPresence()

            /*globalTestObject?.let {
                //verifica che puo' leggere il valore di alcuni campi (LE SEGUENTI RIGHE SONO SOLO PER IL DEBUG!!) Deserializzazione!!!
                Log.d("giuseppeDyn", "ELENCOCLASSI prima di conversione ${globalTestObject!!.getValue("Test Classes")}")
                val elencoClassi: MutableList<LinkedHashMap<Any,Any>> = globalTestObject!!.getValue("Test Classes") as MutableList<LinkedHashMap<Any, Any>>
                Log.d("giuseppeDyn", "valore di un singolo campo: ${elencoClassi}")
                if(elencoClassi.size>0) { elencoClassi.forEach {
                        cl-> if(cl.getValue("NomeClasse")=="ExampleInstrumentedTest") { Log.d("giuseppeDyn", "Corrisponde alla classe ExampleInstrumedTest e il valore ${cl.getValue("Tests List")}")} }
                }
            }*/
        }

        //Si devono mettere come static!!!
        @AfterClass
        @JvmStatic
        fun afterClass() {
            createJson()
        }

        /*if a globalTestReport already exists return a global file to the companion object corresponding variable
        se il file esiste lo carica come oggetto (per modificarlo)
        Restituisce una mappa di Any!!!!!!!
        Viene lanciato prima della classe dei test HASHMAP*/
        fun verifyPresence(): LinkedHashMap<Any, Any>? {
            //Questa e' una variabile locale solo per questa funzione!!!
            var localObject: LinkedHashMap<Any, Any>? = null

            //check if exists already a file
            val context= InstrumentationRegistry.getInstrumentation().targetContext

            //val path: File = context.getExternalFilesDir(null)!!
            //val path: File = File(context.getExternalFilesDir(null)!!.toString()+ customPath)
            val path: File = File(context.getExternalFilesDir(null)!!.toString()+customPath)

            //we'll be able to change the fileName
            val file = File(path, "$customName.json")

            Log.d("giuseppeDyn", "PATH in VERIFICA PRESENZA: $path e file: $file")

            if(file.exists()){
                //if exists assign to the global variable
                localObject=leggiJson(file)
                Log.d("giuseppeDyn", "il file esiste gia'  $localObject")
            }
            else { Log.d("giuseppeDyn", "il file NON esiste gia'") }

            return localObject
        }

        //add singleTest to the local list!!!!
        //Aggiunge singleTest alla lista locale di singleTest (che sono di tipo Map<String,String>
        fun addSingleTest(singleTest: LinkedHashMap<String,String>){
            listaSingleTests.add(singleTest)
        }

        //Write the json file!!!
        fun createJson(){

            var actualGlobalClass: LinkedHashMap<Any, Any>?

            //verifica se esiste o meno
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            //val path: File = context.getExternalFilesDir(null)!!

            //Se non esiste provvedere a creare anche la directory!!!!
            val path: File = File(context.getExternalFilesDir(null)!!.toString()+customPath)
            if(!path.exists()) { path.mkdir()}   //se la path non esiste si deve creare la cartellina!! (da customPath!!!) DA VERIFICARE!! path e name

            Log.d("giuseppeFile", "path $path e customName $customName")
            val file = File(path, "$customName.json")

            //qui si deve comporre il risultato (lista locale e globale!! (verifica se esiste una classe con il solito nome (se il file globale e' null allora scrivi da zero altrimenti aggiungi)
            //se null vuol dire che non esisteva prima e quindi si crea velocemente!!! come classeo anche senza classe
            //
            if(globalTestObject==null) {
                //si puo' anche eliminare e fare direttamente una mappa (con valori CUSTOM)
                    //e in questo caso nella classe si controllano alcuni tipi (in entrata)
                //val actualTestClass = SingleClass("NomeClasse", actualClass, "Tests List", listaSingleTests).rendiMappa()
                val actualTestClass: LinkedHashMap<Any, Any> = linkedMapOf("Nome Classe" to actualClass, "Tests List" to listaSingleTests)
                actualGlobalClass = linkedMapOf("Nome Pacchetto" to pckgDenomination, "Test Classes" to mutableListOf(actualTestClass))
                //actualGlobalClass = CompositeTestClass("Nome pacchetto", pckgDenomination, "Test Classes", mutableListOf(actualTestClass)).rendiMappa()
                Log.d("giuseppeJson", "globalObject e' null !!!!!!!!!!!!!! e $actualGlobalClass")
            }
            else {
                Log.d("giuseppeJson", "globalObject non e' null !!!!!!!!!!!!!! e $globalTestObject")
                //check if the test class already exists (otherwise simply add)

                val elencoClassi: MutableList<LinkedHashMap<Any,Any>> = globalTestObject!!.getValue("Test Classes") as MutableList<LinkedHashMap<Any, Any>>
                //Gson().fromJson<LinkedHashMap<Any, Any>>(it, LinkedHashMap::class.java)
                //val elencoCl: MutableList<LinkedHashMap<Any,Any>> = Gson().fromJson<LinkedHashMap<Any, Any>>((globalTestObject!!.getValue("Test Classes")), MutableList::class.java)
                val listaClassi: MutableList<Any> = globalTestObject!!.getValue("Test Classes") as MutableList<Any>
                //val listaClassiDef: MutableList<LinkedHashMap<Any,Any>> = listaClassi.forEach { cl-> cl as LinkedHashMap<Any,Any> }

                Log.d("beppe", "numero elenco classi ${elencoClassi.size} e $elencoClassi")
                //se la lista delle classi e' maggiore di zero (debugabile come size) e per ognuna verifica il nome della classe
                Log.d("beppe", "primo elemento classe ${elencoClassi[0]}")
                /*for (i in 0..elencoClassi.size) {
                    Log.d("beppe", "VALORE DENTRO ${elencoClassi[i].getValue("NomeClasse")}")
                }*/
                //vediamo se si puo' sostituire con forEach!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                for (i in 0..elencoClassi.size) {
                    Log.d("beppe", "DENTRO CONDIZIONE CUSTOM")
                    if(elencoClassi[i].getValue("NomeClasse")== actualClass) { Log.d("giuseppeDyn", "Corrisponde alla classe beppe ${actualClass} e il valore ${elencoClassi[i].getValue("Tests List")}")
                        //cl.getValue("Tests List") = listaSingleTests
                        elencoClassi[i]["Test List"] = listaSingleTests
                    }
                    else {
                        //we create a new SingleClass (adding also the classname)
                        //val actualTestClass = SingleClass(actualClass, listaSingleTests)
                        val actualTestClass: LinkedHashMap<Any, Any> = linkedMapOf("Nome Classe" to actualClass, "Tests List" to listaSingleTests)
                        //we create a new list of test classes
                        val newClassesList: MutableList<LinkedHashMap<Any,Any>> = globalTestObject!!.getValue("Test Classes") as MutableList<LinkedHashMap<Any, Any>>
                        //and we add a new element
                        newClassesList.add(actualTestClass)
                        //we create a new CompositeTestClass
                        //actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, newClassesList)
                        //actualGlobalClass= linkedMapOf("Nome Pacchetto" to pckgDenomination, "Test Classes" to mutableListOf(newClassesList))
                    }

                    Log.d("beppe", "DOPO CAMBIAMENTO ${elencoClassi[i]}")
                }

                /*elencoClassi.forEach { cl->
                    Log.d("beppe", "DENTRO CONDIZIONE CUSTOM")
                    if(cl.getValue("NomeClasse")== actualClass) { Log.d("giuseppeDyn", "Corrisponde alla classe beppe ${actualClass} e il valore ${cl.getValue("Tests List")}")
                    //cl.getValue("Tests List") = listaSingleTests
                    cl["Test List"] = listaSingleTests
                    }}*/
                //actualGlobalClass = CompositeTestClass("Nome pacchetto", pckgDenomination, "Test Classes", elencoClassi).rendiMappa()
                actualGlobalClass = linkedMapOf("Nome Pacchetto" to pckgDenomination, "Test Classes" to elencoClassi)
                Log.d("beppe", "actualGlobalClass $actualGlobalClass")

               /* if(globalTestObject!!.getValue("") !!.any{ it.test_class_name== actualClass }) {
                    globalTestObject!!.test_classes_list!!.filter { it.test_class_name== actualClass }.forEach { it.tests_list= listaSingleTests }
                    actualGlobalClass= CompositeTestClass(globalTestObject!!.package_name, globalTestObject!!.test_classes_list)
                }*/
                /*else {
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
        fun leggiJson (file: File): LinkedHashMap<Any, Any>? {
            var txt: String? = null
            var compositeTestClass:LinkedHashMap<Any, Any>? = null

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
                compositeTestClass = Gson().fromJson<LinkedHashMap<Any, Any>>(it, LinkedHashMap::class.java)
                val map: LinkedHashMap<*, *> = Gson().fromJson(it, LinkedHashMap::class.java)

                //Log.d("giuseppeDyn", "Valore di Json: $map   ${compositeTestClass.getValue("Test Classes")}")
                //Log.d("giuseppeDyn", "VERIFICA TIPO: ${compositeTestClass.getValue("Test Classes") is MutableList<LinkedHashMap<Any,Any>>}")
            }

            return compositeTestClass
        }

    }

    @get:Rule
    public val watchman: TestRule? = SingleTestWatcher(customSuccess, customFailure)

}
