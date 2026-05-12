package com.example.dobroapp.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.UserRole

// Design System Colors (from Figma)
private val Primary = Color(0xFFAD2C00)
private val PrimaryContainer = Color(0xFFD34011)
private val OnPrimary = Color(0xFFFFFFFF)
private val Surface = Color(0xFFF3FAFF)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainer = Color(0xFFDBF1FE)
private val SurfaceContainerHigh = Color(0xFFD5ECF8)
private val SurfaceContainerHighest = Color(0xFFCFE6F2)
private val OnSurface = Color(0xFF071E27)
private val OnSurfaceVariant = Color(0xFF5A413A)
private val TertiaryContainer = Color(0xFF0B79BF)
private val OnTertiaryContainer = Color(0xFFFDFCFF)
private val SecondaryContainer = Color(0xFFA0F399)
private val OnSecondaryContainer = Color(0xFF217128)
private val PrimaryFixed = Color(0xFFFFDBD1)

@Composable
fun RoleScreen(
    onRoleSelected: (UserRole, String) -> Unit,
    sessionRole: UserRole?,
    isLoading: Boolean,
    errorMessage: String?,
    onOpenDashboard: (UserRole) -> Unit
) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var selectedRole by rememberSaveable { mutableStateOf<UserRole?>(null) }

    LaunchedEffect(sessionRole) {
        sessionRole?.let(onOpenDashboard)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        PrimaryFixed.copy(alpha = 0.5f),
                        Surface,
                        Color(0xFFA3F69C).copy(alpha = 0.3f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(SurfaceContainerLowest)
                .padding(horizontal = 28.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ДоброРядом",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Primary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Сообщество заботы и поддержки",
                    fontSize = 16.sp,
                    color = OnSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // Name input
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Как к вам обращаться?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = {
                        Text(
                            "Введите ваше имя",
                            color = OnSurfaceVariant.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerHighest,
                    ),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        color = OnSurface
                    )
                )
            }

            // Role selector
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Укажите вашу роль",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RoleCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Person,
                        label = "Мне нужна\nпомощь",
                        selected = selectedRole == UserRole.Pensioner,
                        selectedBackground = TertiaryContainer,
                        selectedContentColor = OnTertiaryContainer,
                        onClick = { selectedRole = UserRole.Pensioner }
                    )
                    RoleCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Favorite,
                        label = "Я хочу\nпомогать",
                        selected = selectedRole == UserRole.Volunteer,
                        selectedBackground = SecondaryContainer,
                        selectedContentColor = OnSecondaryContainer,
                        onClick = { selectedRole = UserRole.Volunteer }
                    )
                }
            }

            // Error
            if (!errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage,
                    color = Color(0xFFBA1A1A),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Submit button
            Button(
                onClick = {
                    selectedRole?.let { role ->
                        onRoleSelected(role, fullName)
                    }
                },
                enabled = !isLoading && fullName.isNotBlank() && selectedRole != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary,
                    disabledContainerColor = SurfaceContainerHigh,
                    disabledContentColor = OnSurfaceVariant
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = OnPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Войти",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.size(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun RoleCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    selected: Boolean,
    selectedBackground: Color,
    selectedContentColor: Color,
    onClick: () -> Unit
) {
    val background = if (selected) selectedBackground else SurfaceContainer
    val contentColor = if (selected) selectedContentColor else OnSurface

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = contentColor
        )
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = contentColor,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}