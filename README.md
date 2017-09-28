# Mayo

You're a smart developer. You probably use [okHttp](http://square.github.io/okhttp/) to abstract away access to
`HttpUrlConnection` and all those nasty details you don't really care about. But then,
like lots of smart developers, you write ad hoc network abstraction layers. They
are probably called "APIManager" or "NetworkModel", and they always end in tears.

Ad hoc network layers are common in Android apps. They're bad for a few reasons:

- Makes it hard to write new apps ("where do I begin?")
- Makes it hard to maintain existing apps ("oh my god, this mess...")
- Makes it hard to write unit tests ("how do I do this again?")

So the basic idea of Moya is that we want some network abstraction layer that
sufficiently encapsulates actually calling okHttp directly. It should be simple
enough that common things are easy, but comprehensive enough that complicated things
are also easy.

> If you use okHttp to abstract away `HttpUrlConnection`, why not use something
to abstract away the nitty gritty of URLs, parameters, etc?

Some awesome features of Mayo:

- Compile-time checking for correct API endpoint accesses.
- Lets you define a clear usage of different endpoints with associated sealed class values.
- Treats test stubs as first-class citizens so unit testing is super-easy.

## Sample Project

There's a sample project in the `Mayo-Demo` directory. To use it, open the project and press run. 

## Project Status

This project is actively under development. A release will come shortly!

## Installation

Coming Soon

## Usage

After [some setup](docs/Examples/Basic.md), using Mayo is really simple. You can access an API like this:

```kotlin
provider = MayoProvider<GitHubApi>()
provider.request(GitHubApi.zen(),
        object : HttpCallback<String> {
            override fun onFailure(call: Call?, exception: Exception?) {
                // this means there was a network failure - either the request
                // wasn't sent (connectivity), or no response was received (server
                // timed out).  If the server responds with a 4xx or 5xx error, that
                // will be sent as a onSuccess response.
            }

            override fun onSuccess(call: Call?, response: String?) {
                // do something with the response data or statusCode
            }
        })
```

That's a basic example. Many API requests need parameters. Mayo encodes these
into the sealed class you use to access the endpoint, like this:

```kotlin
provider = MayoProvider<GitHubApi>()
provider.request(GitHubApi.userProfile("GopherLabsLtd"),
          object : HttpCallback<GitHubUser> {
              override fun onFailure(call: Call?, exception: Exception?) {
                  // Handle Error
              }

              override fun onSuccess(call: Call?, response: String?) {
                  // do something with the response data or statusCode
              }
          })
```

No more typos in URLs. No more missing parameter values. No more messing with
parameter encoding.

## Reactive Extensions

On the roadmap... 

### ReactiveSwift

On the roadmap... 

### RxSwift
On the roadmap... 

## License

Mayo is released under an MIT license. See [License.md](License.md) for more information.
