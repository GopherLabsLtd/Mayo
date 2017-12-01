package it.gopher.mayo_demo.providers

import it.gopher.mayo.kotlin.*
import it.gopher.mayo.kotlin.enums.Method
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * This creates a GitHub Provider with a sample HttpLoggingInterceptor.
 */
val HttpBinProvider = MayoProvider<HttpBinApi>(
        plugins = arrayOf(makeLogger()))

/**
 * The implementation of the the GitHub API using [MayoProvider]
 * @author Alex Nguyen
 */
public sealed class HttpBinApi : TargetType {

    /**
     * Endpoint for the Github's Zen Api.
     * <p>
     *     url : "https://httpbin.org/post"
     */
    class post(val name: String, val phoneNumber: String) : HttpBinApi()

    override val baseUrl: String
        get() = "https://httpbin.org"

    override val path: String
        get() = when (this) {
            is post -> "/post"
        }
    override val method: Method
        get() = Method.POST

    override val sampleData: Data
        get() = when (this) {
            is post -> ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "{}")
        }

    override val task: Task
        get() = when (this) {
            is post -> Task.requestData(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\n" +
                    "    \"name\": \"$name\",\n" +
                    "    \"phonenumber\": \"$phoneNumber\"\n" +
                    "}"))
        }

    override val headers: HTTPHeaderFields?
        get() = null
}