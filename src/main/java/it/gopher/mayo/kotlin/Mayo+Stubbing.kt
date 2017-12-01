package it.gopher.mayo.kotlin

import it.gopher.mayo.kotlin.enums.StubBehaviour
import okhttp3.Callback
import okhttp3.Request
import okhttp3.mock.Rule

/**
 * The closure is convience closure that will stub all networking requests.
 */
val alwaysStubClosure: StubClosure<TargetType> = {
    StubBehaviour.immediate
}

fun <Target : TargetType> MayoProvider<Target>.makeStubOkHttpRequest(targetType: Target,
                                                                     request: Request,
                                                                     callback: Callback) {
    mockInterceptor.addRule(Rule.Builder()
            .get()
            .url(targetType.baseUrl + targetType.path)
            .respond(targetType.sampleData))
    val call = client.newCall(request)
    call.enqueue(callback)
}
