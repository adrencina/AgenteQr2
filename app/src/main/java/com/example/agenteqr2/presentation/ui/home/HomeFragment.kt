package com.example.agenteqr2.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenteqr2.databinding.FragmentHomeBinding
import com.example.agenteqr2.presentation.products.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductsViewModel by viewModels()

    private val productsAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        // Simula la carga de productos (reemplaza con tu lógica real)
        val sharedPreferences = requireContext().getSharedPreferences("AppPrefs", 0)
        val accessToken = sharedPreferences.getString("accessToken", "")
        val userId = "5499360" // Reemplaza con el userId correcto

        if (!accessToken.isNullOrEmpty()) {
            viewModel.loadProducts(userId, accessToken)
        } else {
            Toast.makeText(requireContext(), "Token de acceso no encontrado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
    }

    private fun observeViewModel() {
//        viewModel.products.observe(viewLifecycleOwner) { products ->
//            productsAdapter.submitList(products)
//        }
//
//        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}