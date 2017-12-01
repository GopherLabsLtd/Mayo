package it.gopher.mayo.kotlin

import okhttp3.Call

/**
 * Wrapper class for okHttp CallBack to return a Java Model Object.
 * @author Chamu Rajasekera
 * @param <T> Generic type response.
 */
public interface HttpCallback<T> {
    /**
     * Callback for a failed networking call.
     */
    fun onFailure(call: Call?, exception: Exception?)

    /**
     * Callback for a successful networking call.
     * @param call The okHttp Call associcated with the request
     * @param response The sucessful response.
     */
    fun onSuccess(call: Call?, response: T?)
}