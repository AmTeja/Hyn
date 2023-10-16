package com.hyn.hyn.domain.model

data class User(
    var name: String = "",
    var username: String = "",
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var profilePictureUrl: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPosts: Int = 0,
    var bio: String = ""
 )
