package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.components.RepoItem
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

@Composable
fun RepoList(
    onFabClick: () -> Unit, // Acción para abrir RepoForm
    viewModel: RepoListViewModel = viewModel()
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMsg?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            }
            if (!isLoading && errorMsg == null) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(repos.size) { i ->
                        RepoItem(repos[i])
                    }
                }
            }
        }
    }
}