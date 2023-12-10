package com.example.datacentercreator.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datacentercreator.data.ComponentRepositoryImpl
import com.example.datacentercreator.model.Component
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ComponentViewModel(private val componentRepository: ComponentRepositoryImpl = ComponentRepositoryImpl()) :
    ViewModel() {
    private val _componentUiState = MutableStateFlow(emptyList<Component>())
    val componentUiState = _componentUiState.asStateFlow()

    fun getAllComponents(filterText: String? = null) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }) {
            componentRepository.getAllComponents(filterText)
                .collect { components ->
                    _componentUiState.value = components
                }
        }
    }

    fun postComponent(component: Component) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }) {
            componentRepository.postComponent(component)
                .collect { components ->
                    _componentUiState.value = components
                }
        }
    }
}