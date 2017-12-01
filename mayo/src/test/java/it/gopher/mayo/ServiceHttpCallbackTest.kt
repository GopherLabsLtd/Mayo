package it.gopher.mayo

import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.*
import it.gopher.mayo.kotlin.HttpCallback
import it.gopher.mayo.kotlin.ServiceHttpCallBack
import it.gopher.mayo.utils.TestModel
import okhttp3.*
import org.junit.Test

/**
 * This tests the [ServiceHttpCallBack] returns the proper parsed response.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ServiceHttpCallbackTest {
    @Test
    fun `test parse and return JSON model`() {
        /* Given */
        val callback = mock<HttpCallback<TestModel>>()
        val type = object : TypeToken<TestModel>() {}.type
        val sut = ServiceHttpCallBack<TestModel>(type, callback)
        val response = Response.Builder()
                .request(Request.Builder().url("https://gopher.it").build())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("test message")
                .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"test\": \"test string\"}"))
                .build()
        val call = mock<Call>();

        /* When */
        sut.onResponse(call, response)

        /* Then */
        verify(callback, times(1)).onSuccess(any<Call>(), any<TestModel>())
        verify(callback, times(0)).onFailure(any<Call>(), any<Exception>())
    }

    @Test
    fun `test failure to parse json model`() {
        /* Given */
        val callback = mock<HttpCallback<TestModel>>()
        val type = object : TypeToken<Array<TestModel>>() {}.type
        val sut = ServiceHttpCallBack<TestModel>(type, callback)
        val response = Response.Builder()
                .request(Request.Builder().url("https://gopher.it").build())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("test message")
                .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"test\": \"test string\"}"))
                .build()
        val call = mock<Call>();

        /* When */
        sut.onResponse(call, response)

        /* Then */
        verify(callback, times(0)).onSuccess(any<Call>(), any<TestModel>())
        verify(callback, times(1)).onFailure(any<Call>(), any<JsonSyntaxException>())
    }
}
