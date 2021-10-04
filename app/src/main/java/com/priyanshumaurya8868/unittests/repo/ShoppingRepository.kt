package com.priyanshumaurya8868.unittests.repo

import androidx.lifecycle.LiveData
import com.priyanshumaurya8868.unittests.data.local.ShoppingItem
import com.priyanshumaurya8868.unittests.others.Resource
import com.priyanshumaurya8868.unittests.data.remote.response.ImageResponse

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}