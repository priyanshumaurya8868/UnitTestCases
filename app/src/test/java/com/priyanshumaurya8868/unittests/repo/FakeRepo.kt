package com.priyanshumaurya8868.unittests.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.priyanshumaurya8868.unittests.data.local.ShoppingItem
import com.priyanshumaurya8868.unittests.others.Resource
import com.priyanshumaurya8868.unittests.data.remote.response.ImageResponse

class FakeRepo : ShoppingRepository{

    private val  shoppingItems  = mutableListOf<ShoppingItem>()

    private val  observableShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(shoppingItems.sumByDouble{ it.price.toDouble()}.toFloat())
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
      shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
      shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
       return observableShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
       return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }


}