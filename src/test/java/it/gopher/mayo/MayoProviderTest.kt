package it.gopher.mayo

import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import it.gopher.mayo.kotlin.HttpCallback
import it.gopher.mayo.kotlin.MayoProvider
import it.gopher.mayo.kotlin.alwaysStubClosure
import it.gopher.mayo.utils.TestModel
import it.gopher.mayo.utils.TestProvider
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MayoProviderTest {
    val lock = CountDownLatch(1)

    @Test
    fun `Test simple GET Request`() {
        /* Given */
        val sut = MayoProvider<TestProvider>(mainThread = null, stubClosure = alwaysStubClosure)
        val callback = mock<Callback>()

        /* When */
        sut.request(TestProvider.downloadImage(200, 200), callback)
        lock.await(500, TimeUnit.MILLISECONDS)

        /* Then */
        verify(callback, times(1)).onResponse(any<Call>(), any<Response>())
    }

    @Test
    fun `Test simple GET Request Parsed`() {
        /* Given */
        val sut = MayoProvider<TestProvider>(mainThread = null, stubClosure = alwaysStubClosure)
        val type = object : TypeToken<TestModel>() {}.type
        val callback = mock<HttpCallback<TestModel>>()

        /* When */
        sut.request(TestProvider.downloadImage(200, 200), type, callback)
        lock.await(500, TimeUnit.MILLISECONDS)

        /* Then */
        verify(callback, times(1)).onSuccess(any<Call>(), any<TestModel>())
    }

}