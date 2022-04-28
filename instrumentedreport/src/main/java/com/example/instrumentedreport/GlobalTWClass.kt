package com.example.instrumentedreport

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule
import java.io.*

class GlobalTWClass {

    //because some methods are static
    companion object {
        //variabile della lista dei tests
        var listaSingleTests = mutableListOf<SingleTest>()
        var globalTestObject: CompositeTestClass? = null

        var packageDenom: String = ""
        var actualClass: String = ""

        @BeforeClass
        @JvmStatic
        fun beforeClass(){
            listaSingleTests = mutableListOf()
            //verifica se per caso esiste già un file json con i risultati
            //globalTestObject = verifyPresence()
        }

        //Si devono mettere come static!!!
        @AfterClass
        @JvmStatic
        fun afterClass() {
            Log.d("giuseppeRisultati", " nome Classe: ${javaClass.canonicalName}")
            Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!!")
            println("terminata la classe!!!!!! e successi $testResultsStatus")
            //funzione creata da Giuseppe alla conclusione
            createJson()
        }

        //if a globalTestReport already exists return a global file to the companion object corresponding variable
        fun verifyPresence()/*: CompositeTestClass*/ {
            //check if exists already a file
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            val file = File(path, "globalTestReport.txt")

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

        }

        //metodo che aggiunge i successi a oggetto Json da salvare
        fun addSingleTest(singleTest: SingleTest){
            listaSingleTests.add(singleTest)
        }

        //funzione che crea il file Json
        fun createJson(){

            //verifica se esiste o meno
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")

            //val file = File(getApplicationContext<Context>().filesDir, "whatever.txt")

            //Verifica se esiste e modifica ramo
            if (file.exists()) {
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

            }
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
            val path2 = "src/test"
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
            writer.close()

            //un test singolo (con la data class)
            var elementoSingolo = SingleTest("primo Test", "Success")
            var secondoElemento = SingleTest("secondo test", "Success")
            var classeAttuale = SingleClass(mutableListOf(elementoSingolo, secondoElemento))

            var jsonString = Gson().toJson(elementoSingolo)
            var jsonStringLista = Gson().toJson(classeAttuale)
            var testRipreso = Gson().fromJson<SingleClass>(jsonStringLista, SingleClass::class.java)
            Log.d("giuseppeJson", "elemento jsonString $jsonString e lista $classeAttuale")
            Log.d("giuseppeJson", "elemento json $classeAttuale")
            Log.d("giuseppeJson", " e rintracciare un elemento da nested ${testRipreso.listaTests[1].nameTest}")

            /*val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")*/
            val stream = FileOutputStream(file2)
            stream.use { stream ->
                stream.write(jsonStringLista.toByteArray())
            }
        }

        //si recupera un oggetto Json da un file!!!
        fun leggiJson (file: File): SingleClass? {
            var txt: String? = null
            var singleClass:SingleClass? = null

            try {
                val reader = FileReader(file)
                txt = reader.readText()
                Log.d("giuseppeLettura", "stringa letta $txt")
                reader.close()
            } catch (e: IOException) {
                // Exception
                e.printStackTrace()
            }
            //se text diverso da null convertilo in oggetto Json (altrimenti restituira' null come singleClass)
            txt?.let {
                singleClass = Gson().fromJson<SingleClass>(it, SingleClass::class.java)
            }

            return singleClass
        }

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

    }

    @get:Rule
    public val watchman: TestRule? = SingleTestWatcher()
}