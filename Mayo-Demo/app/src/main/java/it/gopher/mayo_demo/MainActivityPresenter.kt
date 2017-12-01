package it.gopher.mayo_demo

import android.graphics.BitmapFactory
import com.google.gson.reflect.TypeToken
import it.gopher.mayo.kotlin.HttpCallback
import it.gopher.mayo.kotlin.MayoProvider
import it.gopher.mayo_demo.models.GitHubRepository
import it.gopher.mayo_demo.providers.FileApi
import it.gopher.mayo_demo.providers.GitHubApi
import it.gopher.mayo_demo.providers.HttpBinApi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * This class contains all the business logic needed for [MainActivity].
 */
class MainActivityPresenter(val githubProvider: MayoProvider<GitHubApi>,
                            val fileProvider: MayoProvider<FileApi>,
                            val httpBinProvider: MayoProvider<HttpBinApi>,
                            val view: MainActivityView) {
    /**
     * Searches for all the repos for a given username.
     */
    fun searchUsernameRepo(username: String) {
        val type = object : TypeToken<Array<GitHubRepository>>() {}.type
        githubProvider.request(GitHubApi.userRespository(username), type, object : HttpCallback<Array<GitHubRepository>> {
            override fun onFailure(call: Call?, exception: Exception?) {
                view.displayError(exception.toString())
            }

            override fun onSuccess(call: Call?, response: Array<GitHubRepository>?) {
                view.reloadRepositories(response)
            }
        })
    }

    /**
     * Makes a call to the GitHub Zen Api.
     */
    fun showGitHubZen() {
        githubProvider.request(GitHubApi.zen(), object : Callback {
            override fun onFailure(call: Call?, exception: IOException?) {
                view.displayError(exception.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                view.displayToast(response?.body()?.string() ?: "")
            }
        })
    }

    /**
     * Downloads a random image.
     */
    fun downloadRandomImage() {
        val randomWidth = (400..800).random()
        val randomHeight = (400..800).random()
        fileProvider.request(FileApi.downloadImage(randomWidth, randomHeight), object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val inputStream = response?.body()?.byteStream();
                val bitmap = BitmapFactory.decodeStream(inputStream)
                view.displayImage(bitmap)
            }

            override fun onFailure(call: Call?, e: IOException?) {

            }
        })
    }

    /**
     * Send a post message to HttpBin.
     */
    fun sendSamplePostRequest() {
        httpBinProvider.request(HttpBinApi.post("test", "234234234234"), object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                view.displayError(e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                print(response?.body()?.string())
            }

        })
    }
}