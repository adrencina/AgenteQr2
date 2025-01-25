package com.example.agenteqr2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.agenteqr2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

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
                    val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                    sharedPreferences.edit().putString("accessToken", token).apply()
                    navController.navigate(R.id.homeFragment)
                }
            }
        }
    }
}