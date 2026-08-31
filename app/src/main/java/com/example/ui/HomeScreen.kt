package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("upload") },
                shape = CircleShape,
                containerColor = PrimaryBlue,
                contentColor = SurfaceWhite,
                modifier = Modifier.offset(y = 50.dp)
            ) {
                Icon(Icons.Filled.UploadFile, contentDescription = "Upload")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Good morning \uD83D\uDC4B", style = MaterialTheme.typography.bodyMedium, color = TextGray)
                Text(text = "Home", style = MaterialTheme.typography.headlineLarge, color = TextDark)
            }
            
            item {
                HeroBanner(navController)
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Quick Actions", style = MaterialTheme.typography.titleLarge)
                    Text("See All ›", style = MaterialTheme.typography.bodyMedium, color = PrimaryBlue)
                }
                Spacer(modifier = Modifier.height(16.dp))
                QuickActionsGrid(navController)
            }

            item {
                Text("Recent Scans", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                RecentScansList(navController)
                Spacer(modifier = Modifier.height(80.dp)) // Extra padding for bottom nav & FAB
            }
        }
    }
}

@Composable
fun HeroBanner(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(PrimaryBlue, Color(0xFF818CF8))
                )
            )
            .clickable { navController.navigate("upload") }
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Description, contentDescription = null, tint = SurfaceWhite)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Check your thesis before your adviser does.",
                        style = MaterialTheme.typography.titleMedium,
                        color = SurfaceWhite,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "AI-powered coherence scan • Free",
                        style = MaterialTheme.typography.bodySmall,
                        color = SurfaceWhite.copy(alpha = 0.8f)
                    )
                }
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = SurfaceWhite)
        }
    }
}

@Composable
fun QuickActionsGrid(navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "Upload .docx",
                subtitle = "Word document",
                icon = Icons.Filled.Description,
                bgColor = PastelBlue,
                onClick = { navController.navigate("upload") }
            )
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "Upload PDF",
                subtitle = "PDF manuscript",
                icon = Icons.Filled.PictureAsPdf,
                bgColor = PastelPurple,
                onClick = { navController.navigate("upload") }
            )
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "Paste Link",
                subtitle = "Google Docs",
                icon = Icons.Filled.Link,
                bgColor = PastelGreen,
                onClick = { navController.navigate("upload") }
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "With Template",
                subtitle = "Custom structure",
                icon = Icons.Filled.Assignment,
                bgColor = PastelYellow,
                onClick = { navController.navigate("upload") }
            )
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "View Sample",
                subtitle = "Demo scan",
                icon = Icons.Filled.Search,
                bgColor = PastelPink,
                onClick = { navController.navigate("results") }
            )
            QuickActionTile(
                modifier = Modifier.weight(1f),
                title = "Quick Scan",
                subtitle = "No template",
                icon = Icons.Filled.FlashOn,
                bgColor = PastelCyan,
                onClick = { navController.navigate("upload") }
            )
        }
    }
}

@Composable
fun QuickActionTile(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    bgColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(0.9f)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = TextDark.copy(alpha = 0.6f), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = TextDark)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TextGray)
        }
    }
}

data class RecentScan(val title: String, val score: Int, val issues: Int, val date: String)

@Composable
fun RecentScansList(navController: NavController) {
    val scans = listOf(
        RecentScan("Capstone_Final_v3.docx", 74, 4, "2 days ago"),
        RecentScan("Thesis_Draft_Introduction.pdf", 88, 1, "Last week")
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        scans.forEach { scan ->
            Card(
                onClick = { navController.navigate("results") },
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(PastelYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Description, contentDescription = null, tint = TextDark.copy(alpha = 0.5f))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(scan.title, style = MaterialTheme.typography.titleMedium, color = TextDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text("Score: ${scan.score} - ${scan.issues} issues - ${scan.date}", style = MaterialTheme.typography.bodySmall, color = TextGray)
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (scan.score >= 80) PastelGreen else PastelYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(scan.score.toString(), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = TextDark)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = SurfaceWhite,
        contentColor = TextGray,
        tonalElevation = 8.dp,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryBlue,
                selectedTextColor = PrimaryBlue,
                indicatorColor = SurfaceWhite
            )
        )
        // Placeholder for FAB space
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Spacer(Modifier.size(24.dp)) },
            label = { Text("Upload") },
            enabled = false
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Info, contentDescription = "About") },
            label = { Text("About") }
        )
    }
}
