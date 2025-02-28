package com.example.vdid

interface OnProcessListener {
    fun onDocumentCaptured(image: ByteArray)
    fun onCaptureFailed(message: String)
}