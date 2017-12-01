package it.gopher.mayo.kotlin

import it.gopher.mayo.kotlin.enums.StubBehaviour
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * This file contains all the useful TypeAlias helpers used for Mayo.
 * @author Eliad Moosavi
 */

typealias SampleResponseClosure = () -> EndpointSampleResponse
typealias Data = ResponseBody;
typealias Parameters = HashMap<String, JSONObject>
typealias HTTPHeaderFields = HashMap<String, String>
typealias StubClosure<Target> = (Target) -> StubBehaviour