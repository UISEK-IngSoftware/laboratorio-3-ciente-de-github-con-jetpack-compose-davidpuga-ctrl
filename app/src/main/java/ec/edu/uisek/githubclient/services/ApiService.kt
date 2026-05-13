package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("user/repos")
    suspend fun getRepository(
        @Query("sort") sort: String = "created",
        @Query("direction") direction: String = "desc",
        @Query("affiliation") affiliation: String = "owner,collaborator,organization_member",
        @Query("per_page") perPage: Int = 100,
        @Query("t") t: String = "${System.currentTimeMillis()}"
    ): List<Repository>
}