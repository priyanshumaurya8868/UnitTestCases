package com.priyanshumaurya8868.unittests.data.remote.response

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)