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

        binding.loginButton.setOnClickListener {
            startAuthorizationFlow()
        }

        observeViewModel()
    }

    private fun startAuthorizationFlow() {
        val authUrl = "${Config.BASE_URL}/apps/authorize" +
                "?client_id=${Config.CLIENT_ID}" +
                "&response_type=code" +
                "&redirect_uri=${Config.REDIRECT_URI}"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
        startActivity(intent)
    }

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
        binding.loginButton.isEnabled = !isLoading
    }

    fun exchangeAuthCodeForToken(authCode: String) {
        viewModel.exchangeAuthCodeForToken(authCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}