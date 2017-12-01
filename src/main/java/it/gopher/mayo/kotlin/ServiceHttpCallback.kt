package it.gopher.mayo.kotlin

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type

/**
 * A Service Callback that converts a JSON response to a Kotlin/Java Object.
 *
 * @author Alex Nguyen
 */
public class ServiceHttpCallBack<T>(
        val type: Type,
        val callback: HttpCallback<T>
) : Callback {

    /**
     * Creates a response object from String.
     * @param httpBodyResponse
     * The response from the server for particular call.
     */
    private fun parsedBodyResponse(httpBodyResponse: String?): T {
        val gson = Gson()
        return gson.fromJson<T>(httpBodyResponse, type)
    }

    /**
     * Call when a successful response has been obtain from OkHttp Networking Client.
     * @param call
     *      The call from OkHttp.
     * @param response
     *      The response generated from OkHttp.
     */
    override fun onResponse(call: Call?, response: Response?) {
        try {
            callback.onSuccess(call, parsedBodyResponse(response?.body()?.string()))
        } catch (exception: Exception) {
            callback.onFailure(call, exception)
        }
    }

    /**
     * Call when a failure response has been obtain from OkHttp Networking Client.
     * @param call
     *      The call from OkHttp.
     * @param response
     *      The response generated from OkHttp.
     */
    override fun onFailure(call: Call?, e: IOException?) {
        callback.onFailure(call, e)
    }

}