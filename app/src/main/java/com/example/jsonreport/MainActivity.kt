package com.example.jsonreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.*
import java.lang.Exception
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //DBExtra.main()
//         Runtime.getRuntime().exec("ls\n")
//        Log.d("giuseppecomando", "instanziato comando")
//        val out = BufferedWriter(OutputStreamWriter(nativeProc.getOutputStream()))
//        val `in` = BufferedReader(InputStreamReader(nativeProc.getInputStream()))
//        var line: String? = null

//        while (`in`.readLine().also { line = it } != null) {
//            full = full.toString() + "\n" + line
//        }
    }
}
object DBExtra {
    @Throws(IOException::class)
    @JvmStatic
    fun main(/*args: Array<String>*/) {
        Log.d("giuseppecomando", "ENTRATO DENTRO CUSTOM CLASS")
        var process:Process? = null
        try {
            process = Runtime.getRuntime().exec("adb shell ls")
            Log.d("giuseppecomando", "instanziato comando")
            println("instanziato un comando")
        }catch (exception: Exception) {
            Log.d("giuseppecomando", "NON instanziato comando")
            println("NON instanziato un comando")
        }
        PrintWriter(
            BufferedWriter(
                OutputStreamWriter(
                    process?.getOutputStream(),
                    StandardCharsets.UTF_8
                )
            )
        ).use { pw ->
            Log.d("giuseppecomando", "ENTRATO DENTRO CUSTOM CLASS2")
            pw.println("ls")
//            pw.println("run-as com.sk.shaft")
//            pw.println("cd files")
//            pw.println("cp file.db /sdcard/download/sample.db3")
//            pw.println("exit")
            pw.println("exit")
        }
        process = Runtime.getRuntime().exec("adb devices")
    }
}