package it.gopher.mayo_demo

import android.graphics.Bitmap
import it.gopher.mayo_demo.models.GitHubRepository

/**
 * Represents the 'View' layer in the MVP Design Pattern.
 */
interface MainActivityView {

    /**
     * Notifies the view to display an error with a specfic message.
     *
     * @param message
     *      Error Message to Display.
     */
    fun displayError(message: String)

    /**
     * Notifies the view to display a Toast.
     *
     * @param message
     *      desired message to display.
     */
    fun displayToast(message: String)

    /**
     * Notifies the view to display a particular image.
     */
    fun displayImage(image: Bitmap)

    /**
     * Notifies the view to reload the repository list.
     */
    fun reloadRepositories(repos: Array<GitHubRepository>?)
}