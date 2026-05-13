package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.services.RepoRequest
import ec.edu.uisek.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel() {
    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val result = RetrofitClient.apiService.getRepository()
                _repos.value = result
            } catch (e: Exception) {
                _errorMsg.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // FUNCIÓN PARA EL MÉTODO POST
    fun createRepo(name: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = RepoRequest(name = name, description = description)
                val response = RetrofitClient.apiService.createRepository(request)

                if (response.isSuccessful) {
                    // Si se creó con éxito, refrescamos la lista
                    fetchRepos()
                } else {
                    _errorMsg.value = "Error al crear: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMsg.value = "Fallo de red: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}