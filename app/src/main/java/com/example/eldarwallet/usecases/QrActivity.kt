package com.example.eldarwallet.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.eldarwallet.usecases.ui.theme.EldarWalletTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class QrActivity : ComponentActivity() {
    private var textRresult = mutableStateOf("")

    private val barcodeLauncher = registerForActivityResult(ScanContract()){
        result ->
        if(result.contents == null){
            Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
        }else {
            textRresult.value = result.contents
        }
    }

    private fun showCamera(){
        val option = ScanOptions()
        option.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        option.setPrompt("Scanea tu codigo")
        option.setCameraId(0)
        option.setBeepEnabled(false)
        option.setOrientationLocked(false)

        barcodeLauncher.launch(option)
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
            isGranted ->
            if(isGranted){
                showCamera()
            }
        }

    private fun checkCameraPermission(context: Context){
        if(ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(context, "es necesaria la camara", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EldarWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = {
                                checkCameraPermission(this@QrActivity)
                            }
                        ) {
                            Text(text = "Leer qr")
                        }

                        Text(text = textRresult.value)
                    }
                }
            }
        }
    }
}