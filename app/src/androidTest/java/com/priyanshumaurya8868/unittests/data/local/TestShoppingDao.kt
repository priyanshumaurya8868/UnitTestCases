package com.priyanshumaurya8868.unittests.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.priyanshumaurya8868.unittests.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TestShoppingDao {


//    InstantTaskExecutorRule swaps the background executor used by the Architecture Components
//    with a different one which executes each task synchronously.
//    So when you need it especially when writing a unit test, you need to implement this dependency;

    @get:Rule
    val  instantTaskExecutorRule  = InstantTaskExecutorRule()

    lateinit var db: ShoppingItemDatabase
    lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries() //avoiding race condition
            .build()

        dao  = db.shoppingDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun insertingShoppingItem()= runBlockingTest {
        val  shoppingItem = ShoppingItem("Maggie", 1 , 10.00f, "maggie.png",1)
        dao.insertShoppingItem(shoppingItem)

        //we now that livedata return data asynchronously but her we want data  to be synchronously
        // so will  use and an extension that'll throw an exception if it takes more that 2 second in returning data
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }


    //runBlockingtest is optimised for unit tests  as removed  unnecessary delays in the code

    @Test
    fun deletingShoppingItem() = runBlockingTest {
        val  shoppingItem = ShoppingItem("Maggie", 1 , 10.00f, "maggie.png",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalAmount() = runBlockingTest {
        val  shoppingItem1 = ShoppingItem("Maggie", 1 , 10.00f, "abc.png",1)
        val  shoppingItem2 = ShoppingItem("Melody", 4 , 2.3f, "acd.png",2)
        val  shoppingItem3 = ShoppingItem("Pasta", 0 , 22.50f, "acd.png",3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val  totalAmount = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalAmount).isEqualTo(1*10f + 4*2.3f)
    }

}