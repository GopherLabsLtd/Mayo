package it.gopher.mayo.kotlin

import okhttp3.Callback
import okhttp3.Request

/**
 * This file contains all the functions that Mayo Interacts with OkHttp.
 */

/**
 * Creates an OkHttp networking request with a new client call.
 * @author Chamu Rajasekera
 * @param request
 *      the request for OkHttp to enqueue.
 * @param
 *      The okHttp success and failure callback action.
 */
fun <Target : TargetType> MayoProvider<Target>.makeOkHttpRequest(request: Request,
                                                                 callback: Callback) {
    val call = client.newCall(request)
    call.enqueue(callback)
}