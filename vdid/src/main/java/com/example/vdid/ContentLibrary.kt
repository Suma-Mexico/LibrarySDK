package com.example.vdid

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import com.innovatrics.dot.core.DotSdk
import com.innovatrics.dot.image.BgraRawImage
import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

object ContentLibrary {
    private var currentFragment: BasicDocumentAutoCaptureFragment? = null

    fun testFragment(): Fragment {
        return MyFragment()
    }

    fun autocaptureFragment(listener: OnProcessListener): Fragment {
        val fragment = BasicDocumentAutoCaptureFragment(listener)
        currentFragment = fragment
        return fragment
    }

    /**
     * Función de prueba del SDK
     *
     * @return Mensaje de prueba
     */
    fun onHello(): String {
        val test = "Este mensaje de prueba proviene de la libreria"
        return test
    }

    /**
     * Continuá con el proceso de detección si el SDK esta inicializado y hay un fragmento activo.
     * Si el SDK fue desinicializado, no hace nada
     */
    fun continueDetection() {
        if (!DotSdk.isInitialized()) {
            Log.w(
                "ContentLibrary", "No se puede continuar: El SDK está desiniacializado"
            )
            return
        }

        currentFragment?.start() ?: Log.w(
            "ContentLibrary", "No hay fragmento activo para continuar la detección"
        )
    }

    /**
     * Detiene el proceso de detección de manera segura y asincrona
     */
    fun stopDetection(onStopped: (String) -> Unit) {
        currentFragment?.stopAsync {
            if (currentFragment?.isAdded == true) {
                onStopped("Se detuvo correctamente")
            } else {
                onStopped("El fragmento ya no esta disponible")
            }
        }
    }

    /**
     * Obtiene el Application ID de la aplicación
     * @param context Contexto de la aplicación
     * @return ID de la apliación
     */
    fun getApplicationId(context: Context): String {
        return DotSdk.getApplicationId(context)
    }

    /**
     * Libera los recursos del SDK si ya no se necesita
     * Se recomienda no llama esta funcion si el SDK aún está en uso
     */
    fun releaseResources() {
        if (!DotSdk.isInitialized()) {
            Log.w(
                "ContentLibrary",
                "El SDK ya esta desinicializado. No es necesario liberarlo nuevamente"
            )
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("ContentLibrary", "Liberando recursos del DOT SDK")
            DotSdk.deinitialize()
            Log.d("ContentLibrary", "Recursos liberados correctamente")
        }
    }

    /**
     * Convertir la imagen captura en formato JPEG a base64
     * @param bgraRawImage Imagen captura en el proceso
     * @return Imagen en formato base64
     */
    fun imageToBase64(bgraRawImage: BgraRawImage): String {
        // Convert BgraRawImage to Bitmap
        val bitmap = BitmapFactory.create(bgraRawImage)

        //Convert Bitmap to base64
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }


    /**
     * Conocer el estado del componente (inicializado o desinicializado)
     */
    /*fun stateComponent(): Boolean {
        return DotSdk.isInitialized()
    }*/

    //To do: verificar si se necesita la función de inicialización
}