package com.example.datacentercreator.data

import com.example.datacentercreator.model.Component
import com.example.datacentercreator.network.DatacenterCreatorApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ComponentRepositoryImpl : ComponentRepository {
    override fun getAllComponents(filterText: String?): Flow<List<Component>> =
        callbackFlow {
            trySendBlocking(
                DatacenterCreatorApi.api.getAllComponents(filterText)
            )
            awaitClose()
        }

    override fun postComponent(component: Component): Flow<List<Component>> =
        callbackFlow {
            trySendBlocking(
                DatacenterCreatorApi.api.postComponent(component)
            )
            awaitClose()
        }
}
