package com.example.vdid

import android.graphics.Bitmap
import android.os.Bundle
//import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.innovatrics.dot.camera.CameraFacing
import com.innovatrics.dot.camera.CameraPreviewScaleType
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureDetection
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.document.autocapture.MrzValidation
import com.innovatrics.dot.document.autocapture.PlaceholderType
import com.innovatrics.dot.document.autocapture.QualityAttributeThresholds
import com.innovatrics.dot.document.autocapture.ValidationMode
import com.innovatrics.dot.image.BgraRawImage
import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

internal class BasicDocumentAutoCaptureFragment(
    private val listener: OnProcessListener
) : DocumentAutoCaptureFragment() {
    private val dotSdkViewModel: DotSdkViewModel by activityViewModels {
        DotSdkViewModelFactory(
            requireActivity().application
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
    }

    override fun provideConfiguration(): Configuration {
        return Configuration(
            cameraFacing = CameraFacing.BACK,
            isTorchEnabled = false,
            cameraPreviewScaleType = CameraPreviewScaleType.FILL,
            validationMode = ValidationMode.STRICT,
            placeholderType = PlaceholderType.CORNERS_ONLY,
            isDetectionLayerVisible = false,
            mrzValidation = MrzValidation.NONE,
            qualityAttributeThresholds = QualityAttributeThresholds(
                minConfidence = 0.95, minSharpness = 0.6, maxHotspotsScore = 0.6
            )
        )
    }

    private fun setupDotSdkViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collect { state ->
                    if (state.isInitialized) {
                        start()
                    }
                    state.errorMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        dotSdkViewModel.notifyErrorMessageShown()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    /*private fun imageToBase64(bgraRawImage: BgraRawImage): String {
        // Convert BgraRawImage to Bitmap
        val bitmap = BitmapFactory.create(bgraRawImage)

        //Convert Bitmap to base64
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }*/


    private fun bgraRawImageToByteArray(bgraRawImage: BgraRawImage): ByteArray {
        val bitmap = BitmapFactory.create(bgraRawImage)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    override fun onNoCameraPermission() {
        //Toast.makeText(context, "No camera permission.", Toast.LENGTH_SHORT).show()
        listener.onCaptureFailed("No camera pemission")
    }

    override fun onCaptured(result: DocumentAutoCaptureResult) {
        //val img = imageToBase64(result.bgraRawImage)
        val img = bgraRawImageToByteArray(result.bgraRawImage)
        //println("Result image base64 : $img")
        listener.onDocumentCaptured(img)
    }

    override fun onProcessed(detection: DocumentAutoCaptureDetection) {
    }
}