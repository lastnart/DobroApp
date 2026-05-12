package com.example.dobroapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.UserRole

private val Primary = Color(0xFFAD2C00)
private val PrimaryContainer = Color(0xFFD34011)
private val OnPrimary = Color(0xFFFFFFFF)
private val Surface = Color(0xFFF3FAFF)
private val SurfaceContainer = Color(0xFFDBF1FE)
private val SurfaceContainerHigh = Color(0xFFD5ECF8)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val OnSurface = Color(0xFF071E27)
private val OnSurfaceVariant = Color(0xFF5A413A)
private val Secondary = Color(0xFF1B6D24)
private val SecondaryContainer = Color(0xFFA0F399)
private val TertiaryContainer = Color(0xFF0B79BF)
private val PrimaryFixedDim = Color(0xFFFFB5A0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val profile by vm.profile.collectAsState()

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
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = OnSurface)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Выйти", tint = Primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(alpha = 0.85f))
            )
        }
    ) { padding ->
        if (profile == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Загрузка...", color = OnSurfaceVariant, fontSize = 16.sp)
            }
            return@Scaffold
        }

        val p = profile!!
        val isVolunteer = p.role == UserRole.Volunteer

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(Brush.linearGradient(colors = listOf(Primary, PrimaryContainer)))
                        .padding(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                brush = Brush.radialGradient(colors = listOf(PrimaryFixedDim.copy(alpha = 0.3f), Color.Transparent)),
                                shape = CircleShape
                            )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(OnPrimary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp), tint = OnPrimary)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(p.fullName, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = OnPrimary)
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(OnPrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isVolunteer) "Волонтёр" else "Пенсионер",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Stats
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.HourglassEmpty,
                        iconBackground = TertiaryContainer.copy(alpha = 0.2f),
                        iconTint = TertiaryContainer,
                        value = p.activeRequests.toString(),
                        label = if (isVolunteer) "Активных заявок" else "Открытых заявок"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CheckCircle,
                        iconBackground = SecondaryContainer.copy(alpha = 0.6f),
                        iconTint = Secondary,
                        value = p.completedRequests.toString(),
                        label = "Выполнено"
                    )
                }
            }

            // Achievements (volunteers only)
            if (isVolunteer) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(SurfaceContainerLowest)
                            .padding(20.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Text("Достижения", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                            AchievementRow(
                                icon = Icons.Default.EmojiEvents,
                                iconBackground = Color(0xFFFAEEDA),
                                iconTint = Color(0xFFBA7517),
                                title = "Новичок добра",
                                subtitle = "Начальный уровень"
                            )
                            AchievementRow(
                                icon = Icons.Default.Star,
                                iconBackground = SecondaryContainer.copy(alpha = 0.6f),
                                iconTint = Secondary,
                                title = "Активный участник",
                                subtitle = "За первые 5 заявок"
                            )
                        }
                    }
                }
            }

            // Info
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(SurfaceContainer)
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Информация", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        InfoRow("Имя", p.fullName)
                        InfoRow("Роль", if (isVolunteer) "Волонтёр" else "Пенсионер")
                        InfoRow("Район", "Центральный")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    value: String,
    label: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = iconTint)
            }
            Text(value, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = OnSurface)
            Text(label, fontSize = 13.sp, color = OnSurfaceVariant, lineHeight = 18.sp)
        }
    }
}

@Composable
private fun AchievementRow(
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color,
    title: String,
    subtitle: String
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(
            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(22.dp), tint = iconTint)
        }
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurface)
            Text(subtitle, fontSize = 12.sp, color = OnSurfaceVariant)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp, color = OnSurfaceVariant)
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
    }
}