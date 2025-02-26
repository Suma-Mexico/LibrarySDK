package com.example.vdid

interface OnProcessListener {
    fun onDocumentCaptured(image: String)
    fun onCaptureFailed(message: String)
}