package it.gopher.mayo.kotlin

import it.gopher.mayo.kotlin.enums.Method

/**
 * The protocol used to define the specifications necessary for a `MayoProvider`.
 * @author Eliad Moosavi
 */
interface TargetType {
    /**
     * The base URI for the service.
     */
    val baseUrl: String

    /**
     * The path to be appended to `baseURL` to form the full `URL`.
     */
    val path: String

    /**
     * The HTTP method used in the request.
     */
    val method: Method

    /**
     * Provides stub data for use in testing.
     */
    val sampleData: Data

    /**
     * Provides what type of Task needs to be done with endpoint.
     */
    val task: Task

    /**
     * The headers for each request.
     */
    val headers: HTTPHeaderFields?
}