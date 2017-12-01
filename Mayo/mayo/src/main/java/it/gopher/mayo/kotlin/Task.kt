package it.gopher.mayo.kotlin

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * Represents an HTTP Task.
 * @author Eliad Moosavi
 */
public sealed class Task {
    /**
     * A request with no additional data.
     */
    class requestPlain() : Task()

    /**
     * A requests body set with data.
     */
    class requestData(val data: RequestBody) : Task()

    /**
     * A requests body set with encoded parameters
     * @param parameters
     *      The necessary parameters for the request.
     * @return A new configured Task.
     */
    class requestParameters(val parameters: Parameters) : Task()

    /**
     * A requests body set with multipart form data.
     * @param multipartBody
     *      the data for uploading.
     * @return A new configured Task.
     */
    class uploadCompositeMultipart(val multipartBody: MultipartBody) : Task()

    /**
     *  A requests body set with data, combined with url parameters.
     * @param bodyData
     *          The request body in the form of a JSONObject.
     * @param urlParams
     *          Any URL parameters that are used in the request.
     * @return A new configured Task.
     */
    class requestCompositeData(val bodyData: JSONObject, val urlParams: HashMap<String, Any>) : Task()

}