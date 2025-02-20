package com.example.vdid

import android.content.Context
import android.content.res.Resources
import com.innovatrics.dot.core.DotSdk
import com.innovatrics.dot.core.DotSdkConfiguration
import com.innovatrics.dot.document.DotDocumentLibrary
import java.io.InputStream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDotSdkUseCase(
    private val dotSdk: DotSdk = DotSdk,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(context: Context) = withContext(dispatcher) {
        val configuration = createDotSdkConfiguration(context)
        dotSdk.initialize(configuration)
    }

    private fun createDotSdkConfiguration(context: Context) = DotSdkConfiguration(
        context = context,
        licenseBytes = readLicenseBytes(context.resources),
        libraries = listOf(
            DotDocumentLibrary(),
        ),
    )

    private fun readLicenseBytes(resources: Resources) =
        resources.openRawResource(R.raw.dot_license).use(InputStream::readBytes)

}