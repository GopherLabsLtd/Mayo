package it.gopher.mayo_demo.providers

import it.gopher.mayo.kotlin.*
import it.gopher.mayo.kotlin.enums.Method
import okhttp3.MediaType
import okhttp3.ResponseBody

/**
 * A Singleton instance of a File Provider.
 * @author Eliad Moosavi
 */
val FileProvider = MayoProvider<FileApi>()

public sealed class FileApi : TargetType {
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
    class downloadImage(val width: Int, val height: Int) : FileApi()

    override val baseUrl: String
        get() = "https://picsum.photos"

    override val path: String
        get() = when (this) {
            is downloadImage -> "/$width/$height"
        }
    override val method: Method
        get() = Method.GET

    override val sampleData: Data
        get() = ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                "{\"data\":{\"id\":\"your_new_gif_id\"},\"meta\":{\"status\":200,\"msg\":\"OK\"}}")

    override val task: Task
        get() = Task.requestPlain()

    override val headers: HTTPHeaderFields?
        get() = null
}
