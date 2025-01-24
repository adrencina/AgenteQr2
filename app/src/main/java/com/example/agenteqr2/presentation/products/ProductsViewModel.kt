package com.example.agenteqr2.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenteqr2.data.repository.ProductsRepository
import com.example.agenteqr2.data.remote.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts(userId: String, accessToken: String) {
        viewModelScope.launch {
            try {
                val productList = productsRepository.getProducts(userId, accessToken)
                _products.value = productList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
