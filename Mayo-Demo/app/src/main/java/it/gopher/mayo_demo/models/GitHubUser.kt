package it.gopher.mayo_demo.models

import com.google.gson.annotations.SerializedName
import it.gopher.mayo_demo.providers.GitHubApi

/**
 * This class represents a model class for a Typical user for the GitHub API. [GitHubApi]
 *
 * @author Alex Nguyen
 *
 * @param login
 *      The GitHub Username for the user.
 *
 * @constructor Creates a user with a login name.
 */
data class GitHubUser(@SerializedName("login")
                      val login: String)