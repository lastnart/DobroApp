package com.example.dobroapp.presentation.volunteer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.HelpRequest
import com.example.dobroapp.domain.model.HelpType
import com.example.dobroapp.domain.model.RequestStatus
import com.example.dobroapp.presentation.requests.RequestsViewModel

// Design System Colors
private val Primary = Color(0xFFAD2C00)
private val PrimaryContainer = Color(0xFFD34011)
private val OnPrimary = Color(0xFFFFFFFF)
private val Surface = Color(0xFFF3FAFF)
private val SurfaceBright = Color(0xFFF3FAFF)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerLow = Color(0xFFE6F6FF)
private val SurfaceContainerHigh = Color(0xFFD5ECF8)
private val SurfaceContainerHighest = Color(0xFFCFE6F2)
private val OnSurface = Color(0xFF071E27)
private val OnSurfaceVariant = Color(0xFF5A413A)
private val SecondaryContainer = Color(0xFFA0F399)
private val OnSecondaryContainer = Color(0xFF217128)
private val Secondary = Color(0xFF1B6D24)
private val TertiaryContainer = Color(0xFF0B79BF)
private val OnTertiaryContainer = Color(0xFFFDFCFF)
private val Tertiary = Color(0xFF00609A)
private val TertiaryFixed = Color(0xFFCFE5FF)
private val OnTertiaryFixed = Color(0xFF001D34)
private val PrimaryFixedDim = Color(0xFFFFB5A0)

@Composable
fun VolunteerScreen(
    vm: RequestsViewModel,
    onBack: () -> Unit,
    onWallet: () -> Unit,
    onRewards: () -> Unit,
    onLeaderboard: () -> Unit,
    onProfile: () -> Unit
) {
    val openRequests by vm.openRequests.collectAsState()
    val acceptedRequests by vm.acceptedByMe.collectAsState()
    val districts = listOf("Все районы", "Центральный", "Заречный", "Ленинский")
    var selectedDistrict by rememberSaveable { mutableStateOf(districts.first()) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        containerColor = Surface,
        bottomBar = {
            DobroBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    when (tab) {
                        2 -> onWallet()
                        3 -> onProfile()
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Header section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceBright)
                ) {
                    // Decorative blob
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        PrimaryFixedDim.copy(alpha = 0.3f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )
                    Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 8.dp)) {
                        Text(
                            text = "Чем мы можем\nпомочь сегодня?",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = OnSurface,
                            lineHeight = 36.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Новые запросы в вашем районе",
                            fontSize = 15.sp,
                            color = OnSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))
                        // District filter chips
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 4.dp)
                        ) {
                            items(districts) { district ->
                                val isSelected = selectedDistrict == district
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedDistrict = district
                                        vm.setDistrictFilter(
                                            if (district == districts.first()) null else district
                                        )
                                    },
                                    label = {
                                        Text(
                                            district,
                                            fontSize = 13.sp,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = TertiaryContainer,
                                        selectedLabelColor = OnTertiaryContainer,
                                        containerColor = SurfaceContainerHighest,
                                        labelColor = OnSurface
                                    ),
                                    border = null,
                                    shape = CircleShape
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }

            // Feed
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Accepted requests
                        if (acceptedRequests.isNotEmpty()) {
                            Text(
                                text = "Мои заявки",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            acceptedRequests.forEach { request ->
                                RequestCard(
                                    request = request,
                                    primaryLabel = when (request.status) {
                                        RequestStatus.Accepted -> "Начать"
                                        else -> null
                                    },
                                    onPrimaryClick = {
                                        if (request.status == RequestStatus.Accepted) {
                                            vm.startRequest(request.id)
                                        }
                                    }
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                        }

                        // Open requests feed
                        Text(
                            text = "Открытые заявки",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        if (openRequests.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Нет открытых заявок",
                                    color = OnSurfaceVariant,
                                    fontSize = 15.sp
                                )
                            }
                        } else {
                            openRequests.forEach { request ->
                                RequestCard(
                                    request = request,
                                    primaryLabel = "Откликнуться",
                                    onPrimaryClick = { vm.acceptRequest(request.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RequestCard(
    request: HelpRequest,
    primaryLabel: String?,
    onPrimaryClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Icon circle
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(helpTypeBackground(request.helpType)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = helpTypeIcon(request.helpType),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = helpTypeIconTint(request.helpType)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = request.helpType.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = OnSurfaceVariant
                            )
                            Text(
                                text = "${request.district} р-н",
                                fontSize = 13.sp,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
                // Coins badge
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Primary, PrimaryContainer)
                            )
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+${request.rewardCoins} DC",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnPrimary
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // Comment
            if (request.comment.isNotBlank()) {
                Text(
                    text = request.comment,
                    fontSize = 15.sp,
                    color = OnSurface,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(14.dp))
            }

            // Footer row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Tertiary
                    )
                    Text(
                        text = request.time,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = OnSurface
                    )
                }
                if (!primaryLabel.isNullOrBlank()) {
                    Button(
                        onClick = onPrimaryClick,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryContainer,
                            contentColor = OnSecondaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = primaryLabel,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DobroBottomNav(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.95f),
        tonalElevation = 0.dp,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
    ) {
        val tabs = listOf(
            Triple("Лента", Icons.Default.Favorite, 0),
            Triple("Кошелек", Icons.Default.AccountBalanceWallet, 2),
            Triple("Профиль", Icons.Default.Person, 3)
        )
        tabs.forEach { (label, icon, index) ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF7C3400),
                    selectedTextColor = Color(0xFF7C3400),
                    indicatorColor = Color(0xFFFFE0D0),
                    unselectedIconColor = Color(0xFF8A9BA8),
                    unselectedTextColor = Color(0xFF8A9BA8)
                )
            )
        }
    }
}

private fun helpTypeIcon(helpType: HelpType): ImageVector = when (helpType) {
    HelpType.Groceries -> Icons.Default.ShoppingCart
    HelpType.Pharmacy -> Icons.Default.LocalPharmacy
    HelpType.Housework -> Icons.Default.Home
    HelpType.Walk -> Icons.Default.DirectionsWalk
    HelpType.Other -> Icons.Default.MoreHoriz
}

private fun helpTypeBackground(helpType: HelpType): Color = when (helpType) {
    HelpType.Groceries -> Color(0xFFA0F399)
    HelpType.Pharmacy -> Color(0xFFCFE5FF)
    HelpType.Housework -> Color(0xFFFFDBD1)
    HelpType.Walk -> Color(0xFFCFE6F2)
    HelpType.Other -> Color(0xFFE3BEB5)
}

private fun helpTypeIconTint(helpType: HelpType): Color = when (helpType) {
    HelpType.Groceries -> Color(0xFF217128)
    HelpType.Pharmacy -> Color(0xFF004A78)
    HelpType.Housework -> Color(0xFF872000)
    HelpType.Walk -> Color(0xFF00609A)
    HelpType.Other -> Color(0xFF5A413A)
}