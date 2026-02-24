package com.example.myrestaurant.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrestaurant.data.Booking
import com.example.myrestaurant.data.BookingStatus
import com.example.myrestaurant.data.MockData
import com.example.myrestaurant.resort.DeepOlive
import com.example.myrestaurant.resort.LuxuryBeige
import com.example.myrestaurant.resort.DarkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsDashboard(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<BookingStatus?>(null) }
    
    val filteredBookings = MockData.bookings.filter { booking ->
        val matchesSearch = searchQuery.isEmpty() || 
            booking.guestName.contains(searchQuery, ignoreCase = true) ||
            booking.roomNumber.contains(searchQuery, ignoreCase = true) ||
            booking.id.contains(searchQuery, ignoreCase = true)
        
        val matchesStatus = selectedStatus == null || booking.status == selectedStatus
        
        matchesSearch && matchesStatus
    }
    
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
                text = "Bookings Management",
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
        
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search bookings...", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LuxuryBeige,
                unfocusedBorderColor = Color.Gray,
                cursorColor = LuxuryBeige,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Status Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BookingStatus.values().forEach { status ->
                FilterChip(
                    onClick = { 
                        selectedStatus = if (selectedStatus == status) null else status
                    },
                    label = { Text(status.name.replace("_", " "), fontSize = 12.sp) },
                    selected = selectedStatus == status,
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
        
        // Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                title = "Total Bookings",
                value = MockData.bookings.size.toString(),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Active",
                value = MockData.bookings.count { 
                    it.status == BookingStatus.CHECKED_IN || it.status == BookingStatus.CONFIRMED 
                }.toString(),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Cancelled",
                value = MockData.bookings.count { it.status == BookingStatus.CANCELLED }.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Bookings List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredBookings) { booking ->
                BookingCard(booking = booking)
            }
        }
    }
}

@Composable
fun BookingCard(booking: Booking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = booking.guestName,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Booking ID: ${booking.id}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                BookingStatusChip(status = booking.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${booking.roomType} - ${booking.roomNumber}",
                        color = LuxuryBeige,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${booking.checkInDate} to ${booking.checkOutDate}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${String.format("%.2f", booking.totalAmount)}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${booking.guestCount} guests",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
            
            if (booking.specialRequests.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Special: ${booking.specialRequests}",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun BookingStatusChip(status: BookingStatus) {
    val (backgroundColor, textColor) = when (status) {
        BookingStatus.CONFIRMED -> Color.Green to Color.White
        BookingStatus.CHECKED_IN -> Color.Blue to Color.White
        BookingStatus.CHECKED_OUT -> Color.Gray to Color.White
        BookingStatus.CANCELLED -> Color.Red to Color.White
        BookingStatus.PENDING -> Color.Yellow to Color.Black
    }
    
    Surface(
        modifier = Modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = status.name.replace("_", " "),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SummaryCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = LuxuryBeige),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = DarkText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                color = DarkText,
                fontSize = 12.sp
            )
        }
    }
}
