package cr.ac.baselab

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.documentfile.provider.DocumentFile
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        var OPEN_DIRECTORY_REQUEST_CODE = 1
    }

    var mediaPlayer = MediaPlayer()
    var contador1 : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        val botonPlay: Button = findViewById(R.id.buttonPlay)
        val nombre = findViewById<TextView>(R.id.titulo)
        botonPlay.setOnClickListener {
            if (requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    var directoryUri = data?.data
                    Log.e("directorio", directoryUri.toString())
                    val rootTree = directoryUri?.let { it1 -> DocumentFile.fromTreeUri(this, it1) }

                    for (file in rootTree!!.listFiles()) {

                        try {
                            file.name?.let { Log.e("Archivo", it) }
                            mediaPlayer = MediaPlayer()
                            nombre?.text = file.name.toString()
                            mediaPlayer.setDataSource(this, file.uri)
                            mediaPlayer.prepare()
                            mediaPlayer.start()
                            break
                        } catch (e: Exception) {
                            Log.e("Error", "No se pudo ejecutar")
                        }


                    }
                }
            }
        }
        val botonStop: Button = findViewById(R.id.buttonStop)
        botonStop.setOnClickListener {
            mediaPlayer.stop()

            if (requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    var directoryUri = data?.data
                    Log.e("directorio", directoryUri.toString())
                    val rootTree = directoryUri?.let { it1 -> DocumentFile.fromTreeUri(this, it1) }

                    for (file in rootTree!!.listFiles()) {
                        try {
                            file.name?.let { Log.e("Archivo", it) }
                            mediaPlayer.setDataSource(this, file.uri)
                            mediaPlayer = MediaPlayer.create(this, file.uri)
                        } catch (e: Exception) {
                            Log.e("Error", "No se pudo ejecutar")
                        }


                    }
                }
            }

        }
        val botonPrev: Button = findViewById(R.id.buttonPrev)
        botonPrev.setOnClickListener {
            contador1 -=1

            var media = MediaPlayer()
            var contador2 : Int = 0
            var directoryUri = data?.data
            Log.e("directorio", directoryUri.toString())
            val rootTree = directoryUri?.let { it1 -> DocumentFile.fromTreeUri(this, it1) }
            for (file in rootTree!!.listFiles()) {
                try {
                    if (contador2 == contador1)
                    file.name?.let { Log.e("Archivo", it) }
                    mediaPlayer.setDataSource(this, file.uri)
                    mediaPlayer = MediaPlayer.create(this, file.uri)
                } catch (e: Exception) {
                    Log.e("Error", "No se pudo ejecutar")
                }


            }

        }
    }
}