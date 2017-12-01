package it.gopher.mayo_demo.providers

import it.gopher.mayo.kotlin.enums.Method
import it.gopher.mayo.kotlin.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Singleton of Github API.
 * @author Alex Nguyen
 */

/**
 * Prepares the Network Logger Plugin for Mayo.
 */
fun makeLogger(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY;
    return logging
}

/**
 * This creates a GitHub Provider with a sample HttpLoggingInterceptor.
 */
val GitHubProvider = MayoProvider<GitHubApi>(
        plugins = arrayOf(makeLogger()))

/**
 * The implementation of the the GitHub API using [MayoProvider]
 * @author Alex Nguyen
 */
public sealed class GitHubApi : TargetType {

    /**
     * Endpoint for the Github's Zen Api.
     * <p>
     *     url : https://api.github.com/zen
     */
    class zen() : GitHubApi()

    /**
     * Endpoint for the Github's User's Profile Api. This retrieves all the user's information (ie Profile Info).
     * <p>
     *     url : https://api.github.com/users/$name
     */
    class userProfile(val name: String) : GitHubApi()

    /**
     * Endpoint for the Github's User Repositories Api. This endpoint retrieves all of a user's repositories.
     * <p>
     *     url : https://api.github.com/zen
     */
    class userRespository(val name: String) : GitHubApi()

    override val baseUrl: String
        get() = "https://api.github.com"

    override val path: String
        get() = when (this) {
            is zen -> "/zen"
            is userProfile -> "/users/$name"
            is userRespository -> "/users/$name/repos"
        }
    override val method: Method
        get() = Method.GET

    override val sampleData: Data
        get() = when (this) {
            is zen -> ResponseBody.create(MediaType.parse("text/plain"), "Non-blocking is better than blocking.")
            is userProfile -> ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"login\": " +
                    "\"$name\", \"id\": 100}")
            is userRespository -> ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                    "[{\"name\": \"Repo Name\"}]")
        }

    override val task: Task
        get() = Task.requestPlain()

    override val headers: HTTPHeaderFields?
        get() = null
}