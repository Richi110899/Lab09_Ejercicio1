package com.example.ejercicio1

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {
    // Estado que mantiene la lista de posts
    val listaPosts: SnapshotStateList<PostModel> = remember { mutableStateListOf() }

    // Efecto que se ejecuta al componer la pantalla
    LaunchedEffect(Unit) {
        val response = servicio.getUserPosts() // Llama a la API para obtener los posts
        if (response.isSuccessful) {
            response.body()?.posts?.let { listaPosts.addAll(it) } // Agrega los posts a la lista
        } else {
            Log.e("ScreenPosts", "Error fetching posts: ${response.code()}") // Manejo de errores
        }
    }

    // Muestra una lista de posts
    LazyColumn {
        items(listaPosts) { item ->
            // Cada elemento de la lista se muestra en una tarjeta
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    // Muestra el ID del post
                    Text(
                        text = item.id.toString(),
                        modifier = Modifier.weight(0.1f),
                        textAlign = TextAlign.End
                    )
                    Spacer(Modifier.width(8.dp)) // Espaciador entre ID y título
                    // Muestra el título del post
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(0.7f),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    // Botón para navegar a la vista del post
                    IconButton(
                        onClick = {
                            navController.navigate("postsVer/${item.id}") // Navega a la vista del post
                            Log.e("POSTS", "ID = ${item.id}") // Log para depuración
                        },
                        modifier = Modifier.weight(0.2f)
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Ver", tint = Color.White) // Cambiar color del icono
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenPost(navController: NavHostController, servicio: PostApiService, id: Int) {
    // Estado que mantiene el post actual
    val post = remember { mutableStateOf<PostModel?>(null) }

    // Efecto que se ejecuta al componer la pantalla
    LaunchedEffect(id) {
        val response = servicio.getUserPostById(id) // Llama a la API para obtener el post por ID
        if (response.isSuccessful) {
            post.value = response.body() // Guarda el post en el estado
        } else {
            Log.e("ScreenPost", "Error fetching post: ${response.code()}") // Manejo de errores
        }
    }

    // Contenedor para el post
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Muestra los detalles del post si están disponibles
        post.value?.let { currentPost ->
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = currentPost.id.toString(),
                        onValueChange = {},
                        label = { Text("ID") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = currentPost.userId.toString(),
                        onValueChange = {},
                        label = { Text("User ID") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = currentPost.title,
                        onValueChange = {},
                        label = { Text("Title") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = currentPost.body,
                        onValueChange = {},
                        label = { Text("Body") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = currentPost.tags.joinToString(", "),
                        onValueChange = {},
                        label = { Text("Tags") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = "Likes: ${currentPost.reactions.likes}, Dislikes: ${currentPost.reactions.dislikes}",
                        onValueChange = {},
                        label = { Text("Reactions") },
                        readOnly = true // Campo de solo lectura
                    )
                    OutlinedTextField(
                        value = "Views: ${currentPost.views}",
                        onValueChange = {},
                        label = { Text("Views") },
                        readOnly = true // Campo de solo lectura
                    )
                }
            }
        }
    }
}
