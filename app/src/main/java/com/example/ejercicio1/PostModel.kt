package com.example.ejercicio1

import com.google.gson.annotations.SerializedName

// Modelo de datos que representa un post.
data class PostModel(
    @SerializedName("userId") val userId: Int, // ID del usuario que creó el post.
    @SerializedName("id") val id: Int, // ID único del post.
    @SerializedName("title") val title: String, // Título del post.
    @SerializedName("body") val body: String, // Cuerpo o contenido del post.
    @SerializedName("tags") val tags: List<String>, // Lista de etiquetas asociadas al post.
    @SerializedName("reactions") val reactions: Reactions, // Reacciones (me gusta/no me gusta) del post.
    @SerializedName("views") val views: Int // Número de vistas del post.
)

// Modelo de datos que representa las reacciones de un post.
data class Reactions(
    @SerializedName("likes") val likes: Int, // Número de "me gusta" del post.
    @SerializedName("dislikes") val dislikes: Int // Número de "no me gusta" del post.
)

// Modelo de datos que representa la respuesta de la API al solicitar posts.
data class PostResponse(
    @SerializedName("posts") val posts: List<PostModel> // Lista de posts devueltos por la API.
)
