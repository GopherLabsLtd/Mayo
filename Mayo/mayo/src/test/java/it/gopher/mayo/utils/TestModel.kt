package it.gopher.mayo.utils

import com.google.gson.annotations.SerializedName

/**
 * Generic Model for use in testing.
 */
data class TestModel(@SerializedName("test")
                     val test: String)