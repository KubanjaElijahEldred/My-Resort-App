package com.example.myrestaurant.resort

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Refined Color Palette
val DeepOlive = Color(0xFF3B4433)
val LuxuryBeige = Color(0xFFD9C5A7)
val DarkText = Color(0xFF2D2D2D)
val SurfaceWhite = Color(0xFFFDFCFB)

@Composable
fun GarugaResortScreen(
    modifier: Modifier = Modifier,
    onNavigateToDashboard: (String) -> Unit = {}
) {
    // Top-level container must be a Box for the Side Bar alignment to work
    Box(modifier = modifier.fillMaxSize().background(DeepOlive)) {

        // 1. Scrollable Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(end = 55.dp) // Leave space for the side bar
        ) {
            HeaderSection()

            // Dashboard Navigation Section
            Text(
                text = "MANAGEMENT DASHBOARDS",
                color = LuxuryBeige,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardNavButton("View Bookings") { onNavigateToDashboard("bookings") }
                DashboardNavButton("Manage Rooms") { onNavigateToDashboard("rooms") }
                DashboardNavButton("Financial Reports") { onNavigateToDashboard("revenue") }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Room Categories Section
            Text(
                text = "ROOM CATEGORIES",
                color = LuxuryBeige,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RoomCardSmall("GRAND", Modifier.weight(1f))
                RoomCardSmall("DOUBLE", Modifier.weight(1f))
                RoomCardSmall("SINGLE", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        // 2. Fixed Side Bar (This is where the 'align' fix is applied)
        // We move the alignment logic here so it is a direct child of the Box
        SideNavigationBar(modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
fun HeaderSection() {
    Column(modifier = Modifier.padding(start = 24.dp, top = 60.dp, bottom = 32.dp)) {
        Text(
            text = "GARUGA",
            color = LuxuryBeige,
            fontSize = 48.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "RESORT",
            color = Color.White,
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Light,
            modifier = Modifier.offset(y = (-10).dp)
        )
        HorizontalDivider(
            modifier = Modifier.width(80.dp).padding(vertical = 16.dp),
            thickness = 2.dp,
            color = LuxuryBeige
        )
    }
}

@Composable
fun DashboardNavButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = LuxuryBeige),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = DarkText, fontWeight = FontWeight.Bold)
            Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = null, tint = DarkText)
        }
    }
}

@Composable
fun RoomCardSmall(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.LightGray))
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun SideNavigationBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier // Fixed: The modifier passed from BoxScope handles alignment
            .fillMaxHeight()
            .width(55.dp)
            .background(LuxuryBeige),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {}, modifier = Modifier.padding(top = 20.dp)) {
            Text("≡", fontSize = 28.sp, color = DarkText)
        }

        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SocialIcon("f")
            SocialIcon("ig")
            SocialIcon("yt")
        }
    }
}

@Composable
fun SocialIcon(text: String) {
    Box(
        modifier = Modifier.size(24.dp).border(1.dp, DarkText, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}