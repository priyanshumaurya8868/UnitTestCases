package com.priyanshumaurya8868.unittests.others

import android.content.Context

class ResourceComparer {

    fun  isEqual(context : Context, resID : Int, string :String): Boolean {
        return context.getString(resID).equals(string)
    }

}