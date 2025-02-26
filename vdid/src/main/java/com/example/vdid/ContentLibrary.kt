package com.example.vdid

import androidx.fragment.app.Fragment

object ContentLibrary {
    fun testFragment(): Fragment {
        return MyFragment()
    }

    fun autocaptureFragment(listener: OnProcessListener): Fragment {
        return BasicDocumentAutoCaptureFragment(listener)
    }
}