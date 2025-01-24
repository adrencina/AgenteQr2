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
import com.example.agenteqr2.databinding.FragmentAuthBinding
import com.example.agenteqr2.core.Config
import com.example.agenteqr2.presentation.auth.AuthViewModel
import com.example.agenteqr2.presentation.auth.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    val viewModel: AuthViewModel by viewModels()

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
        val savedDomain = sharedPreferences.getString("domain", "")

        binding.domainEditText.setText(savedDomain)

        // Configuración del botón de inicio de sesión
        binding.btnAuthenticate.setOnClickListener {
            startAuthorizationFlow()
        }

        // Observa los cambios en el estado de la autenticación
        observeViewModel()
    }

    private fun startAuthorizationFlow() {
        // Obtener el dominio ingresado por el cliente
        val dominioTienda = binding.domainEditText.text.toString().trim()

        if (dominioTienda.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingresa el dominio de tu tienda.", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar el dominio en SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", 0)
        sharedPreferences.edit().putString("domain", dominioTienda).apply()

        // Construir la URL de autorización
        val authUrl = "https://${dominioTienda}.mitiendanube.com/admin/v2/apps/${Config.CLIENT_ID}/authorize"

        // Redirigir al navegador para que el cliente autorice la instalación
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
        startActivity(intent)
    }





//    private fun startAuthorizationFlow() {
//        val authUrl = "https://www.tiendanube.com/apps/authorize" +
//                "?client_id=${Config.CLIENT_ID}" +
//                "&response_type=code" +
//                "&redirect_uri=${Config.REDIRECT_URI}" +
//                "&scope=read_products"
//
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
//        startActivity(intent)
//    }

    private fun observeViewModel() {
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.Loading -> showLoading(true)
                is AuthState.Success -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                    // Navegar a la siguiente pantalla
                }
                is AuthState.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnAuthenticate.isEnabled = !isLoading
    }

    fun handleAuthCode(authCode: String) {
        viewModel.exchangeAuthCodeForToken(authCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}