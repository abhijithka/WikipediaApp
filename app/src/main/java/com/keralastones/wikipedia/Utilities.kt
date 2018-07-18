package com.keralastones.wikipedia

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun makeRequest(url: String, responseListener: Response.Listener<String>, context: Context) {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(Request.Method.GET, url, responseListener, Response.ErrorListener { })
    queue.add(stringRequest)
}


fun navigateToFragment(fragment: Fragment, containerId: Int, fragmentManager: FragmentManager,
                       addToBackStack: Boolean) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (addToBackStack) {
        fragmentTransaction.addToBackStack(null)
    }
    fragmentTransaction.replace(containerId, fragment).commit()
}