# Basic Usage

So how do you use this library? Well, it's pretty easy. Just follow this
template. First, set up an `sealed class` with all of your API targets. Note that you
can include information as part of your enum. Let's look at a common example. First we create a new file named `MyService.kt`:

```kotlin
sealed class GitHubApi : TargetType {
    class zen() : GitHubApi()
    class userProfile(val name: String) : GitHubApi()
    class userRespository(val name: String) : GitHubApi()
}
```

This sealed class is used to make sure that you provide implementation details for each
target (at compile time). You can see that parameters needed for requests can be defined as per the sealed class parameters. The sealed class *must* additionally conform to the `TargetType` interface. Let's get this done by extending the class.

```kotlin
sealed class GitHubApi : TargetType {
    class zen() : GitHubApi()
    class userProfile(val name: String) : GitHubApi()
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
        get() = JSONObject()

    override val task: Task
        get() = Task.requestPlain()

    override val headers: HTTPHeaderFields?
        get() = null
}
```

You can see that the `TargetType` interface makes sure that each value of the enum translates into a full request. Each full request is split up into the `baseURL`, the `path` specifying the subpath of the request, the `method` which defines the HTTP method and `task` with options to specify parameters to be added to the request.

Note that at this point you have added enough information for a basic API networking layer to work. By default Mayo will combine all the given parts into a full request:

```kotlin
   GithubProvider.request(GitHubApi.zen(), object : Callback {
            override fun onFailure(call: Call?, exception: IOException?) {
                view.displayError(exception.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                view.displayToast(response?.body()?.string() ?: "")
            }
        })
```
