package com.example.dobroapp.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dobroapp.R
import com.example.dobroapp.domain.model.HelpType
import com.example.dobroapp.domain.model.RequestStatus
import com.example.dobroapp.domain.model.UserRole
import com.example.dobroapp.presentation.auth.AuthViewModel
import com.example.dobroapp.presentation.auth.RoleScreen
import com.example.dobroapp.presentation.leaderboard.LeaderboardViewModel
import com.example.dobroapp.presentation.profile.ProfileViewModel
import com.example.dobroapp.presentation.requests.RequestsViewModel
import com.example.dobroapp.presentation.rewards.RewardsViewModel
import com.example.dobroapp.presentation.volunteer.VolunteerScreen
import com.example.dobroapp.presentation.volunteer.DobroBottomNav
import com.example.dobroapp.presentation.wallet.WalletViewModel
import com.example.dobroapp.presentation.wallet.WalletScreen
import org.koin.androidx.compose.koinViewModel
import com.example.dobroapp.presentation.pensioner.PensionerCreateRequestScreen

private object Routes {
    const val Role = "role"
    const val Pensioner = "pensioner/dashboard"
    const val Volunteer = "volunteer/dashboard"
    const val Wallet = "wallet"
    const val Rewards = "rewards"
    const val Leaderboard = "leaderboard"
    const val Profile = "profile"
    const val Rate = "rate/{requestId}"
    const val RateTemplate = "rate/%s"
    const val CreateRequest = "pensioner/create"
}

@Composable
fun DobroAppRoot() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val requestsViewModel: RequestsViewModel = koinViewModel()
    val walletViewModel: WalletViewModel = koinViewModel()
    val rewardsViewModel: RewardsViewModel = koinViewModel()
    val leaderboardViewModel: LeaderboardViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = koinViewModel()
    val session by authViewModel.session.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val authError by authViewModel.errorMessage.collectAsState()

    LaunchedEffect(session) {
        session?.let {
            requestsViewModel.bindSession(it.role, it.userId, it.fullName)
            walletViewModel.bindUser(it.userId)
            rewardsViewModel.refresh()
            leaderboardViewModel.refresh()
            profileViewModel.load(it.role, it.userId)
        }
    }

    // Получаем текущий маршрут для отображения нижнего бара
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Проверяем, является ли текущий экран частью раздела волонтера
    val isVolunteerSection = session?.role == UserRole.Volunteer && currentRoute in listOf(
        Routes.Volunteer, Routes.Wallet, Routes.Profile
    )

    Scaffold(
        bottomBar = {
            if (isVolunteerSection) {
                // Используем существующий DobroBottomNav
                // Используем существующий DobroBottomNav
                var selectedTab by rememberSaveable { mutableStateOf(0) }

// Определяем выбранную вкладку по текущему маршруту
                selectedTab = when (currentRoute) {
                    Routes.Volunteer -> 0
                    Routes.Wallet -> 1   // Индекс 2 для кошелька
                    Routes.Profile -> 2  // Индекс 3 для профиля
                    else -> 0
                }

                DobroBottomNav(
                    selectedTab = selectedTab,
                    onTabSelected = { tabIndex ->
                        when (tabIndex) {
                            0 -> {
                                navController.navigate(Routes.Volunteer) {
                                    popUpTo(Routes.Volunteer) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                            1 -> {  // Кошелек - индекс 2
                                navController.navigate(Routes.Wallet) {
                                    popUpTo(Routes.Volunteer) { saveState = true }
                                    launchSingleTop = true
                                }
                            }
                            2 -> {  // Профиль - индекс 3
                                navController.navigate(Routes.Profile) {
                                    popUpTo(Routes.Volunteer) { saveState = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Role,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Role) {
                RoleScreen(
                    onRoleSelected = { role, fullName -> authViewModel.signIn(role, fullName) },
                    sessionRole = session?.role,
                    isLoading = isLoading,
                    errorMessage = authError
                ) { role ->
                    navController.navigate(if (role == UserRole.Pensioner) Routes.Pensioner else Routes.Volunteer) {
                        launchSingleTop = true
                        popUpTo(Routes.Role) { inclusive = false }
                    }
                }
            }

            composable(Routes.Pensioner) {
                com.example.dobroapp.presentation.pensioner.PensionerScreen(
                    vm = requestsViewModel,
                    userName = session?.fullName ?: "",
                    onCreateRequest = { navController.navigate(Routes.CreateRequest) },
                    onProfile = { navController.navigate(Routes.Profile) },
                    onRate = { requestId -> navController.navigate(Routes.RateTemplate.format(requestId)) }
                )
            }

            composable(Routes.Volunteer) {
                VolunteerScreen(
                    vm = requestsViewModel,
                    onBack = { /* Не используется */ },
                    onWallet = { /* Удалено - навигация через нижний бар */ },
                    onRewards = { /* Удалено - навигация через нижний бар */ },
                    onLeaderboard = { /* Удалено - навигация через нижний бар */ },
                    onProfile = { /* Удалено - навигация через нижний бар */ }
                )
            }

            composable(Routes.Wallet) {
                WalletScreen(
                    vm = walletViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.Rewards) {
                RewardsScreen(rewardsViewModel, navController)
            }

            composable(Routes.Leaderboard) {
                LeaderboardScreen(leaderboardViewModel, navController)
            }

            composable(Routes.Profile) {
                com.example.dobroapp.presentation.profile.ProfileScreen(
                    vm = profileViewModel,
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.signOut()
                        navController.navigate(Routes.Role) {
                            launchSingleTop = true
                            popUpTo(Routes.Role) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Routes.Rate,
                arguments = listOf(navArgument("requestId") { type = NavType.StringType })
            ) { entry ->
                val requestId = entry.arguments?.getString("requestId").orEmpty()
                RateDialog(
                    onDismiss = { navController.popBackStack() },
                    onConfirm = { rating ->
                        requestsViewModel.completeRequest(requestId, rating)
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.CreateRequest) {
                PensionerCreateRequestScreen(
                    vm = requestsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RewardsScreen(vm: RewardsViewModel, navController: NavHostController) {
    val rewards by vm.rewards.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(rewards) { reward ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(reward.title, style = MaterialTheme.typography.titleSmall)
                    Text("${reward.category} • ${reward.cost} монет")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeaderboardScreen(vm: LeaderboardViewModel, navController: NavHostController) {
    val items by vm.items.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) { entry ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(entry.volunteerName, style = MaterialTheme.typography.titleSmall)
                    Text("${entry.district} • ${entry.coins} монет")
                    Text(entry.rankTitle)
                }
            }
        }
    }
}

@Composable
private fun RateDialog(onDismiss: () -> Unit, onConfirm: (Int) -> Unit) {
    var rating by rememberSaveable { mutableIntStateOf(5) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.title_rate_request)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Выберите оценку выполнения: $rating")
                Slider(value = rating.toFloat(), onValueChange = { rating = it.toInt().coerceIn(1, 5) }, valueRange = 1f..5f, steps = 3)
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(rating) }) { Text(stringResource(R.string.btn_send_rating)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.btn_back)) } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(title = { Text(title) }, navigationIcon = { IconButton(onClick = onBack) { Text("<") } })
}

private fun RequestStatus.toRuStatus(): String = when (this) {
    RequestStatus.Open -> "Открыта"
    RequestStatus.Accepted -> "Принята"
    RequestStatus.InProgress -> "В работе"
    RequestStatus.Completed -> "Завершена"
}