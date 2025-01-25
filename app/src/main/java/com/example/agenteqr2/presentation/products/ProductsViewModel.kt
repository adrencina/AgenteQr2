package com.example.agenteqr2.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenteqr2.core.UIState
import com.example.agenteqr2.data.remote.model.Product
import com.example.agenteqr2.data.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UIState<List<Product>>>(UIState.Loading)
    val state: StateFlow<UIState<List<Product>>> = _state

    fun loadProducts(userId: String, accessToken: String) {
        viewModelScope.launch {
            _state.value = UIState.Loading
            try {
                val productList = productsRepository.getProducts(userId, accessToken)
                _state.value = UIState.Success(productList)
            } catch (e: Exception) {
                _state.value = UIState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }
}