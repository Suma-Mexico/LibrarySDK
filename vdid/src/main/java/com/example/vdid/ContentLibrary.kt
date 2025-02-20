package com.example.vdid

import androidx.fragment.app.Fragment

object ContentLibrary {
    fun newInstance(): Fragment {
        return MyFragment()
    }

    fun newFragment(): Fragment {
        return BasicDocumentAutoCaptureFragment()
    }
}