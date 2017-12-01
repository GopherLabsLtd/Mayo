package it.gopher.mayo.utils

import it.gopher.mayo.kotlin.*
import it.gopher.mayo.kotlin.enums.Method
import okhttp3.MediaType
import okhttp3.ResponseBody

/**
 * Definition for a basic provider to be used in Testing.
 */
public sealed class TestProvider : TargetType {
    /**
     * Endpoint for downloading a random image.
     *
     * url : https://picsum.photos/$width/$height
     *
     * @param width
     *      The desired image width
     * @param height
     *      The desire height.
     */
    class downloadImage(val width: Int, val height: Int) : TestProvider()

    override val baseUrl: String
        get() = "https://picsum.photos"

    override val path: String
        get() = when (this) {
            is TestProvider.downloadImage -> "/$width/$height"
        }
    override val method: Method
        get() = Method.GET

    override val sampleData: Data
        get() = ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                "{\"test\": \"test string\"}")

    override val task: Task
        get() = Task.requestPlain()

    override val headers: HTTPHeaderFields?
        get() = null
}
