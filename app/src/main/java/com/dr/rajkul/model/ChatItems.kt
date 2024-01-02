package com.dr.rajkul.model

data class ChatItems(
    var userId: Long? = null,
    var message: String? = null,
    var contentType: String? = null,
    var contentUrl: String? = null,
    var time: String? = null,
    var timestamp: Long? = null,
    var isSeen: Boolean? = null,
    var isDelivered: Boolean? = null
)