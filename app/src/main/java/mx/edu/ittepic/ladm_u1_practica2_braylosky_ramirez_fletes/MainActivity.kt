package mx.edu.ittepic.ladm_u1_practica2_braylosky_ramirez_fletes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            if (editText.text.isEmpty() || editText2.text.isEmpty()) {
                Toast.makeText(this,"Cuidado faltan campos por rellenar", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(radioButton2.isChecked == true && radioButton3.isChecked == false){
                guardarArchivoInterno()

            }else if(radioButton2.isChecked == false && radioButton3.isChecked == true){
            guardarArchivoSD()

            }
        }
        button2.setOnClickListener {
            if (editText2.text.isEmpty()) {
                Toast.makeText(this,"El Campo NOMBRE DEL ARCHIVO esta vacio", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(radioButton2.isChecked == true && radioButton3.isChecked == false){
                leerArchivoInterno()


            }else if(radioButton2.isChecked == false && radioButton3.isChecked == true) {
                leerArchivoSD()
            }
        }

        radioButton2.setOnClickListener() {
            radioButton3.setChecked(false)
        }
        radioButton3.setOnClickListener() {
            radioButton2.setChecked(false)
        }
    }//On Create


    fun noSD():Boolean{
        var estado = Environment.getExternalStorageState()

        if (estado != Environment.MEDIA_MOUNTED) {
            return true
        }
        return false
    }//noSD


    fun leerArchivoSD(){
        if(noSD()){
            mensaje("NO HAY MEMORIA EXTERNA")
            return
        }

        var rutaSD = Environment.getExternalStorageDirectory()
        var datosArchivo = File(rutaSD.absolutePath,editText2.getText().toString())
        try {
            var flujoEntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))

            var data = flujoEntrada.readLine()
            var vector = data.split("&")

            ponerTextos(vector[0])
            flujoEntrada.close()

        }catch (error: IOException){
            mensaje(error.message.toString())
        }

    }


    fun guardarArchivoSD(){
        if(noSD()){
            mensaje("NO HAY MEMORIA EXTERNA")
            return
        }

        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath,editText2.getText().toString())

            var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))

            var data = editText.text.toString()+"&"


            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("EXITO! Se guardo correctamente en la SD")

            ponerTextos("")

        }catch (error:IOException ){
            mensaje(error.message.toString())
        }
    }

    fun guardarArchivoInterno(){
        try {
            var flujoSalida = OutputStreamWriter(openFileOutput(editText2.getText().toString(), Context.MODE_PRIVATE))
            var data = editText.text.toString()+"&"


            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("EXITO! Se guardo correctamente")

            editText.setText("")



        }catch (error:IOException ){
            mensaje(error.message.toString())
        }
    }//guardar

    private fun leerArchivoInterno(){
        try {
            var flujoEntrada = BufferedReader(InputStreamReader(openFileInput(editText2.getText().toString())))

            var data = flujoEntrada.readLine()
            var vector = data.split("&")

            ponerTextos(vector[0])
            flujoEntrada.close()

        }catch (error:IOException){
            mensaje(error.message.toString())
        }
    }//leer

    fun mensaje(m:String){
        AlertDialog.Builder(this).setTitle("Atencion").setMessage(m).setPositiveButton("OK"){ d, i->}.show()
    }

    fun ponerTextos (t1:String){
        editText.setText(t1)


    }



}//Main
