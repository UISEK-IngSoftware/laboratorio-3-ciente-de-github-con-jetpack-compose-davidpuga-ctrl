package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Clase para el cuerpo del POST
data class RepoRequest(
    val name: String,
    val description: String,
    val private: Boolean = false
)

interface ApiService {
    @GET("user/repos")
    suspend fun getRepository(
        @Query("sort") sort: String = "created",
        @Query("direction") direction: String = "desc",
        @Query("per_page") perPage: Int = 100
    ): List<Repository>

    @POST("user/repos")
    suspend fun createRepository(
        @Body repo: RepoRequest
    ): Response<Repository>
}