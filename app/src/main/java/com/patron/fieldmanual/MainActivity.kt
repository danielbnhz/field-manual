package com.patron.fieldmanual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.patron.fieldmanual.data.sampleManualDocuments
import com.patron.fieldmanual.ui.library.ManualLibraryScreen
import com.patron.fieldmanual.ui.theme.FieldManualTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FieldManualTheme {
                ManualLibraryScreen(
                    documents = sampleManualDocuments,
                )
            }
        }
    }
}