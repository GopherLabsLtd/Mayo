package it.gopher.mayo.kotlin

import android.os.Looper
import it.gopher.mayo.kotlin.enums.Method
import it.gopher.mayo.kotlin.enums.StubBehaviour
import okhttp3.*
import okhttp3.mock.MockInterceptor
import java.io.IOException
import java.lang.reflect.Type

/**
 * Interface for a Typical MayoProvider
 * @author Alex Nguyen
 */
interface MayoProviderType<T : TargetType> {
    fun request(targetType: T, callback: Callback)
    fun <ModelType> request(targetType: T, type: Type, callback: HttpCallback<ModelType>)
}

/**
 * This is the implementation of a Mayo Provider to make requests using Mayo.
 * @author Alex Nguyen
 * @param requestBuilder
 *      Optional - Default will create a new request builder. A custom one can be injected if needed.
 * @param clientBuilder
 *      Optional - Can be used to inject a client
 * @param stubClosure
 *      Optional - A closure to use if stubbing is required. Otherwise never stub will be selected.
 * @param plugins
 *      Optional - An array of interceptors for networking.
 */
public class MayoProvider<Target : TargetType>(
        val requestBuilder: Request.Builder = Request.Builder(),
        clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
        val stubClosure: StubClosure<Target> = { target: Target ->
            StubBehaviour.never
        },
        plugins: Array<Interceptor> = arrayOf(),
        val mainThread: Looper? = Looper.getMainLooper()
) : MayoProviderType<Target> {

    /**
     * Represents the networking client.
     */
    val client: OkHttpClient
    val mockInterceptor = MockInterceptor()

    init {
        for (plugin in plugins) {
            clientBuilder.addInterceptor(plugin)
        }
        clientBuilder.addInterceptor(mockInterceptor)
        client = clientBuilder.build()
    }

    /**
     * Makes a Request and returns a specific type.
     * @param targetType
     *      The specfic API TargetType to make request.
     * @param type
     *      If the response can be converted to a Java or Kotlin object, specify it here.
     * @param callback
     *      The callback for the asynchronous networking request.
     */
    override fun <ModelType> request(targetType: Target, type: Type, callback: HttpCallback<ModelType>) {
        var requestBuilder = this.requestBuilder
                .url(targetType.baseUrl.plus(targetType.path))

        requestBuilder = buildRequestBody(targetType, requestBuilder)
        var requestCallback = callback
        if (mainThread != null) {
            requestCallback = object : HttpCallback<ModelType> {
                override fun onFailure(call: Call?, exception: Exception?) {
                    val mainHandler = android.os.Handler(mainThread)
                    mainHandler.post({
                        callback.onFailure(call, exception)
                    })
                }

                override fun onSuccess(call: Call?, response: ModelType?) {
                    val mainHandler = android.os.Handler(Looper.getMainLooper())
                    mainHandler.post({
                        callback.onSuccess(call, response)
                    })
                }

            }
        }
        if (stubClosure(targetType) == StubBehaviour.immediate) {
            makeStubOkHttpRequest(targetType, requestBuilder.build(), ServiceHttpCallBack<ModelType>(type, requestCallback))
        } else {
            makeOkHttpRequest(requestBuilder.build(), ServiceHttpCallBack<ModelType>(type, requestCallback))
        }
    }

    /**
     * Enqueues a request with default OkHttp Callback.
     */
    override fun request(targetType: Target, callback: Callback) {
        var requestBuilder = this.requestBuilder
                .url(targetType.baseUrl.plus(targetType.path))

        requestBuilder = buildRequestBody(targetType, requestBuilder)
        var requestCallback = callback
        if (mainThread != null) {
            requestCallback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val mainHandler = android.os.Handler(Looper.getMainLooper())
                    mainHandler.post({
                        callback.onFailure(call, e)
                    })
                }

                override fun onResponse(call: Call, response: Response) {
                    val mainHandler = android.os.Handler(mainThread)
                    mainHandler.post({
                        callback.onResponse(call, response)
                    })
                }
            }
        }
        if (stubClosure(targetType) == StubBehaviour.immediate) {
            makeStubOkHttpRequest(targetType, requestBuilder.build(), requestCallback)
        } else {
            makeOkHttpRequest(requestBuilder.build(), requestCallback)
        }
    }

    /**
     * Helper function to build the request body.
     */
    private fun buildRequestBody(targetType: Target, builder: Request.Builder): Request.Builder {
        // Detect HTTP Method Type
        when (targetType.method) {
            Method.POST -> {
                return parseRequestBodyForPost(targetType, builder)
            }
            Method.GET -> {
                builder.get()
            }
            Method.PUT -> {
                return parseRequestBodyForPut(targetType, builder)
            }
            Method.DELETE -> {
                return parseRequestBodyForDelete(targetType, builder)
            }
        }
        return builder
    }

    private fun parseRequestBodyForDelete(targetType: Target, builder: Request.Builder): Request.Builder {
        val task = targetType.task
        when (task) {
            is Task.requestPlain -> {
                return builder
            }

            is Task.requestParameters -> {
                return builder
            }

            is Task.requestCompositeData -> {
                return builder
            }

            is Task.requestData -> {
                return builder.delete(task.data)
            }
        }
        return builder
    }

    private fun parseRequestBodyForPut(targetType: Target, builder: Request.Builder): Request.Builder {
        val task = targetType.task
        when (task) {
            is Task.requestPlain -> {
                return builder
            }

            is Task.requestParameters -> {
                return builder
            }

            is Task.requestCompositeData -> {
                return builder
            }

            is Task.requestData -> {
                return builder.put(task.data)
            }
        }
        return builder
    }

    private fun parseRequestBodyForPost(targetType: Target, builder: Request.Builder): Request.Builder {
        val task = targetType.task
        when (task) {
            is Task.requestPlain -> {
                return builder
            }

            is Task.requestParameters -> {
                return builder
            }

            is Task.requestCompositeData -> {
                return builder
            }

            is Task.requestData -> {
                return builder.post(task.data)
            }
        }
        return builder
    }
}