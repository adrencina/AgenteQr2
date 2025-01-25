package com.example.agenteqr2.presentation.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.agenteqr2.R
import com.example.agenteqr2.databinding.FragmentAuthBinding
import com.example.agenteqr2.core.Config
import com.example.agenteqr2.core.UIState
import com.example.agenteqr2.core.showIf
import com.example.agenteqr2.presentation.auth.AuthViewModel
import com.example.agenteqr2.presentation.auth.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", 0)
        val accessToken = sharedPreferences.getString("accessToken", null)

        if (!accessToken.isNullOrEmpty()) {
            // Si el token ya existe, redirige a HomeFragment
            findNavController().navigate(R.id.homeFragment)
            return
        }

        // Configuración del botón de inicio de sesión
        binding.btnAuthenticate.setOnClickListener {
            startAuthorizationFlow()
        }

        observeViewModel()
    }

    private fun startAuthorizationFlow() {
        val dominioTienda = binding.domainEditText.text.toString().trim()

        if (dominioTienda.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingresa el dominio de tu tienda.", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", 0)
        sharedPreferences.edit().putString("domain", dominioTienda).apply()

        val authUrl = "https://${dominioTienda}.mitiendanube.com/admin/v2/apps/${Config.CLIENT_ID}/authorize"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
        startActivity(intent)
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        binding.loadingProgressBar.showIf(true)
                        binding.btnAuthenticate.showIf(false)
                    }
                    is UIState.Success -> {
                        binding.loadingProgressBar.showIf(false)
                        binding.btnAuthenticate.showIf(true)
                        findNavController().navigate(R.id.homeFragment)
                    }
                    is UIState.Error -> {
                        binding.loadingProgressBar.showIf(false)
                        binding.btnAuthenticate.showIf(true)
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnAuthenticate.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}