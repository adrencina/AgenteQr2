package com.example.agenteqr2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
                val token = it.getQueryParameter("token")
                if (!token.isNullOrEmpty()) {
                    // Guardar el token en SharedPreferences o pasarlo a una pantalla de bienvenida
                    val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                    sharedPreferences.edit().putString("accessToken", token).apply()

                    // Navegar a la pantalla principal de la app
                    navigateToHomeScreen()
                } else {
                    Toast.makeText(this, "Token no encontrado en el callback.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToHomeScreen() {
        // Reemplaza esta navegación con la lógica de tu app (e.g., NavigationComponent)
        Toast.makeText(this, "¡Autenticación exitosa! Navegando a la pantalla principal.", Toast.LENGTH_SHORT).show()
    }

}