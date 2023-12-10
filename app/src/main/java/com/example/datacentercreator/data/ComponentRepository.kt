package com.example.datacentercreator.data

import com.example.datacentercreator.model.Component
import kotlinx.coroutines.flow.Flow

interface ComponentRepository {
    fun getAllComponents(filterText: String?): Flow<List<Component>>
    fun postComponent(component: Component): Flow<List<Component>>
    fun getComponent(id: String): Flow<Component>
}