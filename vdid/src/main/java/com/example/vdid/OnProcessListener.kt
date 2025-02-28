package com.example.vdid

import com.innovatrics.dot.image.BgraRawImage

interface OnProcessListener {
    fun onDocumentCaptured(image: BgraRawImage)
    fun onCaptureFailed(message: String)
}