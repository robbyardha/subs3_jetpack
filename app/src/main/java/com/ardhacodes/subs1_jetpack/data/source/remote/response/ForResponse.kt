package com.ardhacodes.subs1_jetpack.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ForResponse<T>(
        @SerializedName("status_message")
        val statusMessage: String? = null,

        @SerializedName("status_code")
        val statusCode: Int? = null,

        @SerializedName("results")
        val result: List<T>? = null,
)