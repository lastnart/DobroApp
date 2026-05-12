package com.example.dobroapp.presentation.pensioner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.HelpType
import com.example.dobroapp.presentation.requests.RequestsViewModel

// Design System Colors
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
private val Secondary = Color(0xFF1B6D24)
private val SecondaryContainer = Color(0xFFA0F399)
private val OnSecondaryContainer = Color(0xFF217128)
private val TertiaryContainer = Color(0xFF0B79BF)
private val OnTertiaryContainer = Color(0xFFFDFCFF)
private val PrimaryFixed = Color(0xFFFFDBD1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PensionerCreateRequestScreen(
    vm: RequestsViewModel,
    onBack: () -> Unit
) {
    var selectedType by rememberSaveable { mutableStateOf(HelpType.Groceries) }
    var district by rememberSaveable { mutableStateOf("Центральный") }
    var address by rememberSaveable { mutableStateOf("") }
    var comment by rememberSaveable { mutableStateOf("") }
    var selectedTime by rememberSaveable { mutableStateOf("Любое время") }
    var selectedReward by rememberSaveable { mutableIntStateOf(100) }

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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Назад",
                            tint = OnSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White.copy(alpha = 0.85f)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Создать запрос",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Расскажите, с чем вам нужно помочь. Соседи увидят вашу просьбу и откликнутся.",
                        fontSize = 16.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 24.sp
                    )
                }
            }

            // Step 1: Help type
            item {
                SectionCard(stepNumber = 1, title = "Какая помощь нужна?") {
                    HelpTypeGrid(
                        selectedType = selectedType,
                        onTypeSelected = { selectedType = it }
                    )
                }
            }

            // Step 2: Location
            item {
                SectionCard(stepNumber = 2, title = "Где вы находитесь?") {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        DesignTextField(
                            value = district,
                            onValueChange = { district = it },
                            label = "Район",
                            placeholder = "Центральный район"
                        )
                        DesignTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = "Точный адрес (улица, дом, квартира)",
                            placeholder = "Например: ул. Ленина, д. 10, кв. 42"
                        )
                    }
                }
            }

            // Step 3: Time
            item {
                SectionCard(stepNumber = 3, title = "Когда нужна помощь?") {
                    TimeSelector(
                        selectedTime = selectedTime,
                        onTimeSelected = { selectedTime = it }
                    )
                }
            }

            // Step 4: Comment
            item {
                SectionCard(stepNumber = 4, title = "Подробности") {
                    DesignTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        label = "Что именно нужно сделать?",
                        placeholder = "Напишите здесь всё, что поможет волонтёру лучше выполнить вашу просьбу...",
                        minLines = 4
                    )
                }
            }

            // Step 5: Reward
            item {
                SectionCard(stepNumber = 5, title = "Благодарность") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Укажите количество ДоброКоинов, которые получит помощник.",
                            fontSize = 15.sp,
                            color = OnSurfaceVariant,
                            lineHeight = 22.sp
                        )
                        RewardSelector(
                            selectedReward = selectedReward,
                            onRewardSelected = { selectedReward = it }
                        )
                    }
                }
            }

            // Submit button
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            vm.createRequest(
                                title = selectedType.title,
                                rewardCoins = selectedReward,
                                district = district,
                                address = address,
                                time = selectedTime,
                                comment = comment,
                                helpType = selectedType
                            )
                            onBack()
                        },
                        enabled = address.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary,
                            disabledContainerColor = SurfaceContainerHigh,
                            disabledContentColor = OnSurfaceVariant
                        )
                    ) {
                        Text(
                            text = "Опубликовать запрос",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "Нажимая кнопку, вы подтверждаете создание запроса.",
                        fontSize = 13.sp,
                        color = OnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionCard(
    stepNumber: Int,
    title: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceContainerLowest)
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                }
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
            }
            content()
        }
    }
}

@Composable
private fun HelpTypeGrid(
    selectedType: HelpType,
    onTypeSelected: (HelpType) -> Unit
) {
    val types = listOf(
        Triple(HelpType.Groceries, Icons.Default.ShoppingCart, "Сходить за продуктами"),
        Triple(HelpType.Pharmacy, Icons.Default.MedicalServices, "Купить лекарства"),
        Triple(HelpType.Housework, Icons.Default.Home, "Помощь по дому"),
        Triple(HelpType.Walk, Icons.Default.DirectionsRun, "Выгул питомца"),
        Triple(HelpType.Other, Icons.Default.MoreVert, "Другое")
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        types.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { (type, icon, label) ->
                    val isSelected = selectedType == type
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) Primary else SurfaceContainer)
                            .clickable { onTypeSelected(type) }
                            .padding(vertical = 18.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.size(34.dp),
                                tint = if (isSelected) OnPrimary else OnSurface
                            )
                            Text(
                                text = label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) OnPrimary else OnSurface,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
                // Fill empty slot if odd number
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun DesignTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    minLines: Int = 1
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = OnSurfaceVariant
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = OnSurfaceVariant.copy(alpha = 0.5f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = SurfaceContainerHighest,
                unfocusedContainerColor = SurfaceContainerHighest,
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = OnSurface
            )
        )
    }
}

@Composable
private fun TimeSelector(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val times = listOf(
        "Любое время",
        "Утром (9:00 - 12:00)",
        "Днём (12:00 - 16:00)",
        "Вечером (16:00 - 20:00)"
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        times.forEach { time ->
            val isSelected = selectedTime == time
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) SecondaryContainer else SurfaceContainerHighest)
                    .clickable { onTimeSelected(time) }
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = time,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) OnSecondaryContainer else OnSurface
                )
            }
        }
    }
}

@Composable
private fun RewardSelector(
    selectedReward: Int,
    onRewardSelected: (Int) -> Unit
) {
    val rewards = listOf(50, 100, 150)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rewards.forEach { reward ->
            val isSelected = selectedReward == reward
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) SecondaryContainer else SurfaceContainer)
                    .then(
                        if (isSelected) Modifier.border(2.dp, Secondary, RoundedCornerShape(14.dp))
                        else Modifier
                    )
                    .clickable { onRewardSelected(reward) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$reward",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) OnSecondaryContainer else OnSurface
                )
            }
        }
    }
}