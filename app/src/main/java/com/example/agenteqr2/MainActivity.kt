package com.example.agenteqr2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.agenteqr2.presentation.ui.auth.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Carga el AuthFragment como pantalla inicial
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentContainer, AuthFragment())
            }
        }

        // Manejar el deep link si la app se abre con uno
        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        val data: Uri? = intent.data
        data?.let {
            if (it.scheme == "agenteqr2" && it.host == "callback") {
                val authCode = it.getQueryParameter("code")
                if (!authCode.isNullOrEmpty()) {
                    // Llamar al ViewModel para intercambiar el authorization code por un token
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                    if (fragment is AuthFragment) {
                        fragment.viewModel.exchangeAuthCodeForToken(authCode)
                    }
                }
            }
        }
    }
}