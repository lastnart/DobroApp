package com.example.dobroapp.presentation.pensioner

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.HelpRequest
import com.example.dobroapp.domain.model.RequestStatus
import com.example.dobroapp.presentation.requests.RequestsViewModel

// Design System Colors
private val Primary = Color(0xFFAD2C00)
private val PrimaryContainer = Color(0xFFD34011)
private val OnPrimary = Color(0xFFFFFFFF)
private val Surface = Color(0xFFF3FAFF)
private val SurfaceBright = Color(0xFFF3FAFF)
private val SurfaceContainerLow = Color(0xFFE6F6FF)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerHigh = Color(0xFFD5ECF8)
private val SurfaceContainerHighest = Color(0xFFCFE6F2)
private val OnSurface = Color(0xFF071E27)
private val OnSurfaceVariant = Color(0xFF5A413A)
private val Secondary = Color(0xFF1B6D24)
private val SecondaryContainer = Color(0xFFA0F399)
private val OnSecondaryContainer = Color(0xFF217128)
private val Tertiary = Color(0xFF00609A)
private val PrimaryFixedDim = Color(0xFFFFB5A0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PensionerScreen(
    vm: RequestsViewModel,
    userName: String,
    onCreateRequest: () -> Unit,
    onProfile: () -> Unit,
    onRate: (String) -> Unit
) {
    val requests by vm.myRequests.collectAsState()
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ДоброРядом",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF7C3400)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White.copy(alpha = 0.85f)
                )
            )
        },
        /*
        bottomBar = {
            NavigationBar(
                containerColor = Color.White.copy(alpha = 0.95f),
                tonalElevation = 0.dp,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.List, contentDescription = "Мои заявки", modifier = Modifier.size(24.dp)) },
                    label = { Text("Мои заявки", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF7C3400),
                        selectedTextColor = Color(0xFF7C3400),
                        indicatorColor = Color(0xFFFFE0D0),
                        unselectedIconColor = Color(0xFF8A9BA8),
                        unselectedTextColor = Color(0xFF8A9BA8)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onProfile()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Профиль", modifier = Modifier.size(24.dp)) },
                    label = { Text("Профиль", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
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
         */
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceBright)
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(PrimaryFixedDim.copy(alpha = 0.3f), Color.Transparent)
                                ),
                                shape = CircleShape
                            )
                    )
                    Column(
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "Добро пожаловать,",
                            fontSize = 16.sp,
                            color = OnSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = userName,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = OnSurface,
                            lineHeight = 36.sp
                        )
                        Spacer(Modifier.height(16.dp))

                        // Stats row
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.HourglassEmpty,
                                value = requests.count { it.status != RequestStatus.Completed }.toString(),
                                label = "Активных",
                                color = Primary
                            )
                            MiniStatCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.CheckCircle,
                                value = requests.count { it.status == RequestStatus.Completed }.toString(),
                                label = "Выполнено",
                                color = Secondary
                            )
                        }
                        Spacer(Modifier.height(20.dp))

                        // Create button
                        Button(
                            onClick = onCreateRequest,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = OnPrimary
                            )
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Создать новый запрос",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        // Profile button
                        Button(
                            onClick = onProfile,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SecondaryContainer,
                                contentColor = OnSecondaryContainer
                            )
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Профиль",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Requests list
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Мои заявки",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                }
            }

            if (requests.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerLow)
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = OnSurfaceVariant.copy(alpha = 0.4f)
                            )
                            Text(
                                text = "Заявок пока нет",
                                fontSize = 18.sp,
                                color = OnSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Нажмите «Создать новый запрос»\nчтобы попросить о помощи",
                                fontSize = 16.sp,
                                color = OnSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            } else {
                items(requests) { request ->
                    Box(modifier = Modifier.background(SurfaceContainerLow).padding(horizontal = 16.dp, vertical = 6.dp)) {
                        PensionerRequestCard(
                            request = request,
                            onComplete = { onRate(request.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniStatCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = color)
            }
            Column {
                Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = OnSurface)
                Text(label, fontSize = 12.sp, color = OnSurfaceVariant)
            }
        }
    }
}

@Composable
private fun PensionerRequestCard(
    request: HelpRequest,
    onComplete: () -> Unit
) {
    val statusColor = when (request.status) {
        RequestStatus.Open -> Tertiary
        RequestStatus.Accepted -> Color(0xFFBA7517)
        RequestStatus.InProgress -> Secondary
        RequestStatus.Completed -> OnSurfaceVariant
    }
    val statusLabel = when (request.status) {
        RequestStatus.Open -> "Ожидает волонтёра"
        RequestStatus.Accepted -> "Волонтёр найден"
        RequestStatus.InProgress -> "В работе"
        RequestStatus.Completed -> "Завершена"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Title + status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = request.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(statusColor.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = statusColor
                    )
                }
            }

            // Location + time
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = OnSurfaceVariant)
                    Text(request.district, fontSize = 13.sp, color = OnSurfaceVariant)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = OnSurfaceVariant)
                    Text(request.time, fontSize = 13.sp, color = OnSurfaceVariant)
                }
            }

            // Volunteer name if assigned
            if (!request.volunteerName.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(14.dp), tint = OnSecondaryContainer)
                    }
                    Text(
                        text = "Волонтёр: ${request.volunteerName}",
                        fontSize = 13.sp,
                        color = Secondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Complete button
            if (request.status == RequestStatus.InProgress) {
                Button(
                    onClick = onComplete,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryContainer,
                        contentColor = OnSecondaryContainer
                    )
                ) {
                    Text("Подтвердить выполнение", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}