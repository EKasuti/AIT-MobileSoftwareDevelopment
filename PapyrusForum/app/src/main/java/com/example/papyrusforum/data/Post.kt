package com.example.papyrusforum.data

data class Post(
    var uid: String = "",
    var author: String = "",
    var postDate: String = "",
    var postTitle: String = "",
    var postBody: String = "",
    var imgUrl: String = ""
)

data class PostWithId(
    var postId: String = "",
    var post: Post
)