package it.gopher.mayo_demo.models

import com.google.gson.annotations.SerializedName

/**
 * This class represents a model class for a typical repository for the GitHub API. [GitHubApi]
 * @author Alex Nguyen
 * @property name
 *      The name of the repository
 * @property url
 *      The url of the repository.
 * @constructor Creates a user with a login name.
 */
data class GitHubRepository(
        @SerializedName("name") val name: String,
        @SerializedName("html_url") val url: String
)