package it.gopher.mayo.kotlin

import it.gopher.mayo.kotlin.enums.Method
import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * A sealed class used for stubbing responses.
 * @author Eliad Moosavi
 */
public sealed class EndpointSampleResponse {
    class networkResponse(val statusCode: kotlin.Int) : EndpointSampleResponse()
    class response(val response: Response, val data: ResponseBody) : EndpointSampleResponse()
    class networkError(val call: Call, val exception: IOException) : EndpointSampleResponse()
}

/**
 * Model class for defining an Endpoint to use for [MayoProvider].
 *
 * @param url the URL of the endpoint.
 * @param sampleResponseClosure the response closure for the endpoint.
 * @param method the type of [Method] for the particular endpoint.
 * @param httpHeaderFields Any [HTTPHeaderFields] associated with the endpoint request.
 */
public class EndPoint<Target>(
        url: String,
        sampleResponseClosure: SampleResponseClosure,
        method: Method,
        httpHeaderFields: HTTPHeaderFields
) {
    val url = url
    val sampleResponseClosure = sampleResponseClosure
    val method = method
    val httpHeaderFields = httpHeaderFields

    /**
     * Convenience method for creating a new `Endpoint` with the same properties as the receiver, but with added HTTP header fields.
     */
    public fun addingNewHttpHeaderFields(newHTTPHeaderFields: HTTPHeaderFields): EndPoint<Target> {
        return EndPoint<Target>(url, sampleResponseClosure, method, newHTTPHeaderFields)
    }

}