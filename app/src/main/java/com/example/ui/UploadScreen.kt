package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import /*  */ androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    var manuscriptUploaded by remember { mutableStateOf(false) }
    var templateUploaded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Manuscript", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F5F9))
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundWhite)
            )
        },
        containerColor = BackgroundWhite
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                TabButton(
                    text = "File Upload",
                    icon = Icons.Filled.Folder,
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f)
                )
                TabButton(
                    text = "Google Docs",
                    icon = Icons.Filled.Link,
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Upload Area
            if (selectedTab == 0) {
                UploadDropzone(
                    isUploaded = manuscriptUploaded,
                    fileName = "Capstone_Final_v3.docx",
                    onToggle = { manuscriptUploaded = !manuscriptUploaded }
                )
            } else {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Paste Google Docs link here...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceWhite,
                        unfocusedContainerColor = SurfaceWhite,
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Template Section
            Text(
                "DOCUMENT TEMPLATE — optional",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Upload your school's manuscript template so Resync understands your chapter structure — e.g. if your Chapter 1 is \"Theoretical Background\" instead of \"Introduction.\"",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            TemplateDropzone(
                isUploaded = templateUploaded,
                fileName = "DLSU_Thesis_Template_2024.docx",
                onToggle = { templateUploaded = !templateUploaded }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Launch Button
            Button(
                onClick = { navController.navigate("processing") },
                enabled = manuscriptUploaded || selectedTab == 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    disabledContainerColor = Color(0xFFCBD5E1)
                )
            ) {
                Text("\uD83D\uDE80 Launch Scan", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun TabButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val bgColor = if (selected) PrimaryBlue else Color.Transparent
    val contentColor = if (selected) SurfaceWhite else TextGray
    
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = contentColor, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun UploadDropzone(isUploaded: Boolean, fileName: String, onToggle: () -> Unit) {
    val dashColor = if (isUploaded) PrimaryBlue else Color(0xFFCBD5E1)
    val bgColor = if (isUploaded) PastelBlue.copy(alpha = 0.3f) else SurfaceWhite
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            // Note: In real Compose, dashed borders require a custom drawBehind modifier.
            // Using a standard border for simplicity.
            .border(1.dp, dashColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Description, contentDescription = null, tint = if(isUploaded) PrimaryBlue else TextGray, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(12.dp))
            if (isUploaded) {
                Text(fileName, style = MaterialTheme.typography.titleMedium, color = PrimaryBlue)
                Text("Tap to remove", style = MaterialTheme.typography.bodySmall, color = TextGray)
            } else {
                Text("Tap to upload .docx or .pdf", style = MaterialTheme.typography.titleMedium, color = TextDark)
                Text("Max 25MB", style = MaterialTheme.typography.bodySmall, color = TextGray)
            }
        }
    }
}

@Composable
fun TemplateDropzone(isUploaded: Boolean, fileName: String, onToggle: () -> Unit) {
    val dashColor = if (isUploaded) PrimaryBlue else Color(0xFFCBD5E1)
    val bgColor = if (isUploaded) PastelBlue.copy(alpha = 0.3f) else SurfaceWhite
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.dp, dashColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Assignment, contentDescription = null, tint = if(isUploaded) PrimaryBlue else TextGray, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                if (isUploaded) {
                    Text(fileName, style = MaterialTheme.typography.titleMedium, color = PrimaryBlue)
                    Text("Tap to remove", style = MaterialTheme.typography.bodySmall, color = TextGray)
                } else {
                    Text("Upload template file", style = MaterialTheme.typography.titleMedium, color = TextDark)
                    Text("Optional", style = MaterialTheme.typography.bodySmall, color = TextGray)
                }
            }
        }
    }
}
