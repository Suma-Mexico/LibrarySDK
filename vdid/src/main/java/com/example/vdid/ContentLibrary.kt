package com.example.vdid

import androidx.fragment.app.Fragment

//import com.innovatrics.dot.core.DotSdk

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

    fun onHello(): String {
        val test = "Este mensaje de prueba proviene de la libreria"
        return test
    }

    fun continueDetection() {
        currentFragment?.start()
    }

    fun stopDetection() {
        currentFragment?.start()
        //To do: verificar la función de esta propiedad para liberar recursos una vez se termine el proceso de captura
        //DotSdk.deinitialize();
    }
}