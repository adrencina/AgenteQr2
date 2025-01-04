package com.example.agenteqr2

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
    }
}