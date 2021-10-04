package com.priyanshumaurya8868.unittests.others

import com.google.common.truth.Truth.assertThat
import com.priyanshumaurya8868.unittests.others.RegisterationUtils
import org.junit.Test


class RegisterationUtilsTest {

    /**
     * the input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     * ...the confirmed password is not the same as the real password
     * ...the password contains less than 2 digits
     */

    @Test
    fun `username and password are notEmpty  returns false`() {
        val result = RegisterationUtils.register(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun  `username already taken returns  false`(){
        val  res = RegisterationUtils.register(
            "Ram",
            "adse",
            "adse"
        )
        assertThat(res).isFalse()
    }
    @Test
    fun `correctly  repeat password returns  false`() {
        val res = RegisterationUtils.register(
            "Priyanshu",
            "adse",
            "adse"
        )
        assertThat(res).isFalse()
    }

    @Test
    fun  `password  contain  more than 2 digits  return  false`(){
        val res= RegisterationUtils.register(
            "Solitudinarian",
            "1jlk",
            "1jlk"
        )
        assertThat(res).isFalse()
    }


}