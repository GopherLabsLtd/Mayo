package it.gopher.mayo_demo.providers

import it.gopher.mayo.kotlin.enums.Method
import it.gopher.mayo.kotlin.*
import okhttp3.*

/**
 * A Singleton instance of a Giphy Provider.
 * @author Eliad Moosavi
 */
val GiphyProvider = MayoProvider<GiphyApi>()

/**
 * The implementation of the the Giphy API using [MayoProvider]
 * @author Eliad Moosavi
 */
sealed class GiphyApi : TargetType {
    /**
     * Endpoint to upload an Image to Giphy.
     */
    class upload(val gif: RequestBody) : GiphyApi()

    override val baseUrl: String
        get() = "https://upload.giphy.com"

    override val path: String
        get() = when (this) {
            is upload -> "/v1/gifs"
        }
    override val method: Method
        get() = when (this) {
            is upload -> Method.POST
        }
    override val sampleData: Data
        get() = when (this) {
            is upload -> ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                    "{\"data\":{\"id\":\"your_new_gif_id\"},\"meta\":{\"status\":200,\"msg\":\"OK\"}}")
        }

    override val task: Task
        get() = when (this) {
            is upload -> Task.uploadCompositeMultipart(MultipartBody.Builder()
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""), gif)
                    .build())
        }

    override val headers: HTTPHeaderFields?
        get() = null
}