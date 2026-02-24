package com.example.myrestaurant.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrestaurant.data.MockData
import com.example.myrestaurant.data.Transaction
import com.example.myrestaurant.data.TransactionStatus
import com.example.myrestaurant.data.TransactionType
import com.example.myrestaurant.resort.DeepOlive
import com.example.myrestaurant.resort.LuxuryBeige
import com.example.myrestaurant.resort.DarkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialReportsDashboard(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val report = remember { MockData.getFinancialReport() }
    var selectedPeriod by remember { mutableStateOf("This Month") }
    
    val periods = listOf("Today", "This Week", "This Month", "This Year")
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepOlive)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Financial Reports",
                color = LuxuryBeige,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = LuxuryBeige)
            ) {
                Text("Back", color = DarkText)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Period Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            periods.forEach { period ->
                FilterChip(
                    onClick = { selectedPeriod = period },
                    label = { Text(period, fontSize = 12.sp) },
                    selected = selectedPeriod == period,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LuxuryBeige,
                        selectedLabelColor = DarkText,
                        containerColor = Color.DarkGray,
                        labelColor = Color.White
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Revenue Overview Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RevenueCard(
                title = "Total Revenue",
                value = "$${String.format("%.2f", report.totalRevenue)}",
                icon = Icons.Default.AttachMoney,
                color = Color.Green,
                modifier = Modifier.weight(1f)
            )
            RevenueCard(
                title = "Monthly Revenue",
                value = "$${String.format("%.2f", report.monthlyRevenue)}",
                icon = Icons.Default.TrendingUp,
                color = Color.Blue,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RevenueCard(
                title = "Daily Revenue",
                value = "$${String.format("%.2f", report.dailyRevenue)}",
                icon = Icons.Default.Today,
                color = Color.Cyan,
                modifier = Modifier.weight(1f)
            )
            RevenueCard(
                title = "Occupancy Rate",
                value = "${String.format("%.1f", report.occupancyRate)}%",
                icon = Icons.Default.Hotel,
                color = Color.Magenta,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Booking Statistics
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Booking Statistics",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatisticItem(
                        label = "Total Bookings",
                        value = report.totalBookings.toString(),
                        color = LuxuryBeige
                    )
                    StatisticItem(
                        label = "Cancelled",
                        value = report.cancelledBookings.toString(),
                        color = Color.Red
                    )
                    StatisticItem(
                        label = "Avg. Value",
                        value = "$${String.format("%.0f", report.averageBookingValue)}",
                        color = Color.Green
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Revenue by Room Type
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Revenue by Room Type",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                report.revenueByRoomType.forEach { (roomType, revenue) ->
                    RevenueByRoomTypeItem(
                        roomType = roomType,
                        revenue = revenue,
                        totalRevenue = report.totalRevenue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Recent Transactions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Recent Transactions",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(MockData.transactions.take(5)) { transaction ->
                        TransactionItem(transaction = transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun RevenueCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun StatisticItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RevenueByRoomTypeItem(roomType: String, revenue: Double, totalRevenue: Double) {
    val percentage = if (totalRevenue > 0) (revenue / totalRevenue) * 100 else 0.0
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = roomType,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$${String.format("%.2f", revenue)}",
                color = LuxuryBeige,
                fontSize = 12.sp
            )
        }
        
        // Progress bar
        LinearProgressIndicator(
            progress = (percentage / 100).toFloat(),
            modifier = Modifier
                .weight(2f)
                .height(8.dp)
                .padding(horizontal = 8.dp),
            color = LuxuryBeige,
            trackColor = Color.Gray
        )
        
        Text(
            text = "${String.format("%.1f", percentage)}%",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.width(40.dp)
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val (icon, color) = when (transaction.type) {
                TransactionType.PAYMENT -> Icons.Default.AttachMoney to Color.Green
                TransactionType.REFUND -> Icons.Default.Replay to Color.Red
                TransactionType.DEPOSIT -> Icons.Default.AccountBalanceWallet to Color.Blue
            }
            
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = transaction.guestName,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${transaction.type.name} • ${transaction.paymentMethod}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$${String.format("%.2f", transaction.amount)}",
                color = when (transaction.type) {
                    TransactionType.PAYMENT -> Color.Green
                    TransactionType.REFUND -> Color.Red
                    TransactionType.DEPOSIT -> Color.Blue
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            TransactionStatusChip(status = transaction.status)
        }
    }
}

@Composable
fun TransactionStatusChip(status: TransactionStatus) {
    val (backgroundColor, textColor) = when (status) {
        TransactionStatus.COMPLETED -> Color.Green to Color.White
        TransactionStatus.PENDING -> Color.Yellow to Color.Black
        TransactionStatus.FAILED -> Color.Red to Color.White
        TransactionStatus.REFUNDED -> Color.Gray to Color.White
    }
    
    Surface(
        modifier = Modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
