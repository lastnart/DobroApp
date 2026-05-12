package com.example.dobroapp.presentation.wallet

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dobroapp.domain.model.CoinTransaction

// Design System Colors
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
private val Tertiary = Color(0xFF00609A)
private val TertiaryContainer = Color(0xFF0B79BF)
private val PrimaryFixedDim = Color(0xFFFFB5A0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    vm: WalletViewModel,
    onBack: () -> Unit
) {
    val balance by vm.balance.collectAsState()
    val rank by vm.rank.collectAsState()
    val transactions by vm.transactions.collectAsState()

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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Hero balance card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Primary, PrimaryContainer)
                            )
                        )
                        .padding(28.dp)
                ) {
                    // Decorative blob
                    Box(
                        modifier = Modifier
                            .size(180.dp)
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
                    Column {
                        Text(
                            text = "Ваш баланс",
                            fontSize = 15.sp,
                            color = OnPrimary.copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$balance",
                                fontSize = 52.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = OnPrimary,
                                letterSpacing = (-1).sp
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "ДК",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = OnPrimary.copy(alpha = 0.9f),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = rank,
                            fontSize = 14.sp,
                            color = OnPrimary.copy(alpha = 0.8f)
                        )
                        Spacer(Modifier.height(24.dp))
                        // Action buttons
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = Primary
                                    )
                                    Text(
                                        text = "Перевести",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Primary
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(OnPrimary.copy(alpha = 0.2f))
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.QrCodeScanner,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = OnPrimary
                                    )
                                    Text(
                                        text = "Оплатить",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Rewards section
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Награды партнёров",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        TextButton(onClick = {}) {
                            Text(
                                text = "Все награды",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TertiaryContainer
                            )
                        }
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        item {
                            RewardCard(
                                title = "Кофейня «Зерно»",
                                description = "Скидка на любой напиток при предъявлении промокода",
                                discount = "-20% Скидка",
                                cost = 150
                            )
                        }
                        item {
                            RewardCard(
                                title = "Кинотеатр «Мир»",
                                description = "Бесплатный билет на любой утренний сеанс в будние дни",
                                discount = "Бесплатно",
                                cost = 500
                            )
                        }
                    }
                }
            }

            // Transactions section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(SurfaceContainer)
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "История операций",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        if (transactions.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Операций пока нет",
                                    color = OnSurfaceVariant,
                                    fontSize = 15.sp
                                )
                            }
                        } else {
                            transactions.forEach { tx ->
                                TransactionItem(tx)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RewardCard(
    title: String,
    description: String,
    discount: String,
    cost: Int
) {
    Card(
        modifier = Modifier.width(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerHigh)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = discount,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = OnSurface.copy(alpha = 0.7f),
                lineHeight = 17.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$cost ДК",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Primary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Забрать",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(tx: CoinTransaction) {
    val isPositive = tx.amount > 0
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (isPositive) SecondaryContainer.copy(alpha = 0.6f)
                        else SurfaceContainerHigh
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPositive) Icons.Default.ShoppingBasket else Icons.Default.LocalCafe,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = if (isPositive) Secondary else Tertiary
                )
            }
            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.reason,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = tx.createdAt,
                    fontSize = 12.sp,
                    color = OnSurface.copy(alpha = 0.6f)
                )
            }
            // Amount
            Text(
                text = "${if (isPositive) "+" else ""}${tx.amount} ДК",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (isPositive) Secondary else OnSurface
            )
        }
    }
}