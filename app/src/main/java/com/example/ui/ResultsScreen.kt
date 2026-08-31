package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = BackgroundWhite,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate("upload") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("+ New Scan", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScoreHeader(navController)
            
            // Highlight Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LegendItem("Logic Gap", HighlightLogicGap)
                LegendItem("Contradiction", HighlightContradiction)
                LegendItem("Redundancy", HighlightRedundancy)
            }

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceWhite)
                    .padding(4.dp)
            ) {
                ResultsTab("Manuscript", selectedTab == 0) { selectedTab = 0 }
                ResultsTab("Issues (4)", selectedTab == 1) { selectedTab = 1 }
                ResultsTab("Citations", selectedTab == 2) { selectedTab = 2 }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {
                0 -> ManuscriptTab()
                1 -> IssuesTab()
                2 -> CitationsTab()
            }
        }
    }
}

@Composable
fun ScoreHeader(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceWhite)
            .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        IconButton(
            onClick = { navController.navigate("home") { popUpTo("home") } },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextGray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Simulated Gauge
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF1F5F9),
                    strokeWidth = 8.dp,
                    strokeCap = StrokeCap.Round
                )
                CircularProgressIndicator(
                    progress = { 0.74f },
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF59E0B), // Orange/Amber
                    strokeWidth = 8.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("74", style = MaterialTheme.typography.headlineMedium, color = TextDark)
                    Text("/100", style = MaterialTheme.typography.labelSmall, color = TextGray)
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Text("COHERENCE SCORE", style = MaterialTheme.typography.labelSmall, color = TextGray)
                Text("Moderate Coherence", style = MaterialTheme.typography.titleLarge, color = Color(0xFFF59E0B))
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IssueBadge("2 Contradictions", ContradictionBadgeBg, ContradictionBadgeText)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IssueBadge("1 Logic Gap", LogicGapBadgeBg, LogicGapBadgeText)
                    IssueBadge("1 Redundancy", RedundancyBadgeBg, RedundancyBadgeText)
                }
            }
        }
    }
}

@Composable
fun IssueBadge(text: String, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = textColor)
    }
}

@Composable
fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextGray)
    }
}

@Composable
fun RowScope.ResultsTab(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) PrimaryBlue else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = if (selected) SurfaceWhite else TextGray
        )
    }
}

@Composable
fun ManuscriptTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text("Tap any highlight to see the issue details.", style = MaterialTheme.typography.bodySmall, color = TextGray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("ABSTRACT", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryBlue)
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val abstractText = buildAnnotatedString {
                    append("This study investigates the factors affecting technology adoption among higher education students in ")
                    withStyle(style = SpanStyle(background = HighlightContradiction)) {
                        append("Metro Manila institutions")
                    }
                    append(". The research aims to ")
                    withStyle(style = SpanStyle(background = HighlightLogicGap)) {
                        append("evaluate user satisfaction")
                    }
                    append(", identify adoption barriers, and propose implementation frameworks for educational technology systems.")
                }
                Text(
                    text = abstractText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text("CHAPTER 1 — INTRODUCTION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryBlue)
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val introText = buildAnnotatedString {
                    append("The rapid integration of educational technology in Philippine higher education institutions has created both opportunities and challenges. Adoption rates have been inconsistent across institutions, with ")
                    withStyle(style = SpanStyle(background = HighlightRedundancy)) {
                        append("user satisfaction remaining a critical but underexplored metric")
                    }
                    append(".\n\nThis research is conducted within ")
                    withStyle(style = SpanStyle(background = HighlightContradiction)) {
                        append("one university")
                    }
                    append("...")
                }
                Text(
                    text = introText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun IssuesTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IssueCard(
                type = "Contradiction",
                path = "Abstract → Introduction",
                description = "Abstract claims the study covers 'Metro Manila institutions' (plural), but the Introduction explicitly states the research is conducted within 'one university'.",
                fix = "Revise the abstract to reflect a single-institution scope, or update the methodology if multiple institutions are involved.",
                badgeBg = ContradictionBadgeBg,
                badgeText = ContradictionBadgeText
            )
        }
        item {
            IssueCard(
                type = "Logic Gap",
                path = "Abstract → Methodology",
                description = "Abstract promises to 'evaluate user satisfaction', but the Methodology chapter does not contain any instrument (survey/interview) designed to measure satisfaction.",
                fix = "Add a user satisfaction survey to your data gathering instruments, or remove the promise from the abstract.",
                badgeBg = LogicGapBadgeBg,
                badgeText = LogicGapBadgeText
            )
        }
    }
}

@Composable
fun IssueCard(type: String, path: String, description: String, fix: String, badgeBg: Color, badgeText: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IssueBadge(type, badgeBg, badgeText)
                Spacer(modifier = Modifier.width(8.dp))
                Text(path, style = MaterialTheme.typography.labelSmall, color = TextGray)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium, color = TextDark)
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(BackgroundWhite)
                    .padding(12.dp)
            ) {
                Column {
                    Text("RECOMMENDED FIX", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = PrimaryBlue)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(fix, style = MaterialTheme.typography.bodyMedium, color = TextDark)
                }
            }
        }
    }
}

@Composable
fun CitationsTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = ContradictionBadgeBg),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Error, contentDescription = null, tint = ContradictionBadgeText)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("1 dead link found in bibliography.", style = MaterialTheme.typography.bodyMedium, color = ContradictionBadgeText)
                }
            }
        }

        item {
            CitationItem(
                text = "Smith, J. (2023). Educational Tech. Journal of Ed. https://doi.org/10.1234/edtech",
                isLive = true
            )
        }
        item {
            CitationItem(
                text = "Doe, A. (2021). Technology Acceptance Model. https://example.com/not-found-404",
                isLive = false
            )
        }
    }
}

@Composable
fun CitationItem(text: String, isLive: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (isLive) Icons.Filled.CheckCircle else Icons.Filled.Close,
                contentDescription = null,
                tint = if (isLive) Color(0xFF16A34A) else Color(0xFFB91C1C),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium, color = TextDark)
        }
    }
}
