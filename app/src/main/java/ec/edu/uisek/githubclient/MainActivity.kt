package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.screens.RepoForm
import ec.edu.uisek.githubclient.ui.screens.RepoList
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                val viewModel: RepoListViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("list") }

                if (currentScreen == "list") {
                    RepoList(
                        onFabClick = { currentScreen = "form" },
                        viewModel = viewModel
                    )
                } else {
                    RepoForm(
                        onBackClick = { currentScreen = "list" },
                        onSaveClick = { name, description ->
                            viewModel.createRepo(name, description)
                            currentScreen = "list"
                        }
                    )
                }
            }
        }
    }
}