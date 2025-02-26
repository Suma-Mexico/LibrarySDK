package com.example.vdid

interface OnProcessListener {
    fun onDocumentCaptured(image: String)
    fun onTest(text: String)
}