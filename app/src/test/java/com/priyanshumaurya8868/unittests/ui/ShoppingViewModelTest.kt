package com.priyanshumaurya8868.unittests.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.priyanshumaurya8868.unittests.others.Constant
import com.priyanshumaurya8868.unittests.others.Status
import com.priyanshumaurya8868.unittests.repo.FakeRepo
import com.priyanshumaurya8868.unittests.utils.MainCoroutineRule
import com.priyanshumaurya8868.unittests.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest{

    @get:Rule
    val instantTaskExecutorRule  = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: ShoppingViewModel

    @Before
    fun  setup(){
        viewModel = ShoppingViewModel(FakeRepo())
    }

    @Test
    fun `insert shopping item  with empty fields, returns error`(){
        viewModel.insertShoppingItem("name", "23","")

        val  res = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(res.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item  with too long name,return error`(){

        val str = buildString{
            for (i in 1..Constant.MAX_NAME_LENGTH+1)
                append("$i")
        }
        viewModel.insertShoppingItem(str, "23","")

        val  res = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(res.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item  with too long price,return error`(){
        val str = buildString{
            for (i in 1..Constant.MAX_PRICE_LENGTH+1)
                append("$i")
        }
        viewModel.insertShoppingItem("name", "23",str)

        val  res = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(res.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item  with too high amount,return error`(){

        viewModel.insertShoppingItem("name", "9999999999999999999","23")

        val  res = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(res.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item, return success`(){

        viewModel.insertShoppingItem("name", "23","10")

        val  res = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(res.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}