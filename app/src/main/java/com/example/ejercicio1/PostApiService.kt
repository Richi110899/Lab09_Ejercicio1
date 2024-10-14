package com.example.ejercicio1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz que define los métodos para acceder a la API de posts.
interface PostApiService {

    // Método para obtener la lista de posts.
    @GET("posts") // Esta anotación indica que se realizará una solicitud GET a la ruta "posts".
    suspend fun getUserPosts(): Response<PostResponse> // Método suspendido que devuelve un objeto Response con la lista de posts.

    // Método para obtener un post específico por su ID.
    @GET("posts/{id}") // Esta anotación indica que se realizará una solicitud GET a la ruta "posts/{id}".
    suspend fun getUserPostById(@Path("id") id: Int): Response<PostModel> // Método suspendido que devuelve un objeto Response con el post correspondiente al ID proporcionado.
}
