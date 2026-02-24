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
import com.example.myrestaurant.data.Room
import com.example.myrestaurant.data.RoomStatus
import com.example.myrestaurant.resort.DeepOlive
import com.example.myrestaurant.resort.LuxuryBeige
import com.example.myrestaurant.resort.DarkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomManagementDashboard(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFloor by remember { mutableStateOf("All") }
    var selectedStatus by remember { mutableStateOf<RoomStatus?>(null) }
    
    val floors = listOf("All", "1", "2", "3")
    
    val filteredRooms = MockData.rooms.filter { room ->
        val matchesFloor = selectedFloor == "All" || room.floor.toString() == selectedFloor
        val matchesStatus = selectedStatus == null || room.status == selectedStatus
        matchesFloor && matchesStatus
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
                text = "Room Management",
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
        
        // Floor Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            floors.forEach { floor ->
                FilterChip(
                    onClick = { selectedFloor = floor },
                    label = { Text("Floor $floor", fontSize = 12.sp) },
                    selected = selectedFloor == floor,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LuxuryBeige,
                        selectedLabelColor = DarkText,
                        containerColor = Color.DarkGray,
                        labelColor = Color.White
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Status Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoomStatus.values().forEach { status ->
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
            Box(modifier = Modifier.weight(1f)) {
                RoomSummaryCard(
                    title = "Total Rooms",
                    value = MockData.rooms.size.toString(),
                    color = LuxuryBeige
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                RoomSummaryCard(
                    title = "Available",
                    value = MockData.rooms.count { it.status == RoomStatus.AVAILABLE }.toString(),
                    color = Color.Green
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                RoomSummaryCard(
                    title = "Occupied",
                    value = MockData.rooms.count { it.status == RoomStatus.OCCUPIED }.toString(),
                    color = Color.Blue
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Rooms Grid
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredRooms.chunked(2)) { roomPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    roomPair.forEach { room ->
                        RoomCard(
                            room = room,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // If odd number of rooms, add empty space
                    if (roomPair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun RoomCard(room: Room, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = room.number,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = room.type,
                        color = LuxuryBeige,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Floor ${room.floor}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                RoomStatusChip(status = room.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$${room.pricePerNight}/night",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Max ${room.maxOccupancy} guests",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            if (room.currentGuest != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Guest",
                        tint = LuxuryBeige,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = room.currentGuest,
                        color = LuxuryBeige,
                        fontSize = 12.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Amenities
            Text(
                text = "Amenities:",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = room.amenities.take(3).joinToString(", "),
                color = Color.LightGray,
                fontSize = 11.sp
            )
            
            if (room.amenities.size > 3) {
                Text(
                    text = "+${room.amenities.size - 3} more",
                    color = LuxuryBeige,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun RoomStatusChip(status: RoomStatus) {
    val backgroundColor = when (status) {
        RoomStatus.AVAILABLE -> Color.Green
        RoomStatus.OCCUPIED -> Color.Blue
        RoomStatus.MAINTENANCE -> Color.Red
        RoomStatus.CLEANING -> Color.Yellow
        RoomStatus.OUT_OF_ORDER -> Color.Gray
    }
    
    val textColor = when (status) {
        RoomStatus.AVAILABLE -> Color.White
        RoomStatus.OCCUPIED -> Color.White
        RoomStatus.MAINTENANCE -> Color.White
        RoomStatus.CLEANING -> Color.Black
        RoomStatus.OUT_OF_ORDER -> Color.White
    }
    
    val icon = when (status) {
        RoomStatus.AVAILABLE -> Icons.Default.CheckCircle
        RoomStatus.OCCUPIED -> Icons.Default.Person
        RoomStatus.MAINTENANCE -> Icons.Default.Build
        RoomStatus.CLEANING -> Icons.Default.CleaningServices
        RoomStatus.OUT_OF_ORDER -> Icons.Default.Block
    }
    
    Surface(
        modifier = Modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status.name.replace("_", " "),
                color = textColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RoomSummaryCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 24.sp,
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
