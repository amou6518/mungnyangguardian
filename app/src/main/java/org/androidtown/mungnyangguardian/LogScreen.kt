package org.androidtown.mungnyangguardian

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 컬러 팔레트
private val Cream = Color(0xFFFFFCF7)
private val Brown = Color(0xFF5D4635)
private val CardBorder = Color(0xFFECE8E2)
private val Green = Color(0xFF70A866)
private val LightGreen = Color(0xFFEAF5E7)
private val LightPurple = Color(0xFFEDEBFA)
private val PrimaryPurple = Color(0xFF7B75D7)
private val RedAlert = Color(0xFFD34F4F)
private val OrangeAlert = Color(0xFFF08C3A)
private val TextGray = Color(0xFF888888)
private val TimelineLineColor = Color(0xFFEEEEEE)

// 타임라인 데이터 클래스
data class TimelineEvent(
    val time: String,
    val title: String,
    val titleColor: Color = Color.Black,
    val iconRes: Int? = null,
    val dangerLevel: String,
    val action: String? = null,
    val imageRes: Int,
    val duration: String
)

@Composable
fun LogScreen(modifier: Modifier = Modifier) {

    var selectedTabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("전체", "위험 이벤트", "음성 개입", "이동 기록")

    // 임시 타임라인 데이터
    val events = listOf(
        TimelineEvent(
            time = "14:23",
            title = "전선 접근 감지",
            titleColor = RedAlert,
            iconRes = R.drawable.alert_red,
            dangerLevel = "위험도: 높음",
            action = "음성 + 물리 개입",
            imageRes = R.drawable.log_img1,
            duration = "00:15"
        ),
        TimelineEvent(
            time = "11:10",
            title = "음식 접근 감지",
            titleColor = Color.Black,
            iconRes = R.drawable.alert_orange,
            dangerLevel = "위험도: 중간",
            action = "음성 개입",
            imageRes = R.drawable.log_img2,
            duration = "00:12"
        ),
        TimelineEvent(
            time = "09:05",
            title = "탐색 행동",
            titleColor = Color.Black,
            iconRes = null,
            dangerLevel = "위험도: 낮음",
            action = null,
            imageRes = R.drawable.log_img3,
            duration = "00:12"
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Cream),
        contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp)
    ) {

        item {
            Text(
                text = "기록",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Brown,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }


        item {
            DateSelector(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    iconRes = R.drawable.guard,
                    iconBg = LightGreen,
                    title = "위험 차단",
                    value = "2회",
                    valueColor = Green,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    iconRes = R.drawable.pet,
                    iconBg = LightPurple,
                    title = "평균 활동량",
                    value = "높음",
                    valueColor = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .border(width = 1.dp, color = CardBorder, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 20.dp)
            ) {
                TabRowMenu(
                    tabs = tabs,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                // 타임라인 리스트
                events.forEachIndexed { index, event ->
                    TimelineItem(
                        event = event,
                        isLast = index == events.size - 1,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AiSummaryCard(modifier = Modifier.padding(horizontal = 20.dp))
        }
    }
}

@Composable
fun DateSelector(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = CardBorder, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 14.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "<", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "2026년 5월 5일 (화)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(text = ">", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SummaryCard(
    iconRes: Int,
    iconBg: Color,
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(CardBorder)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 11.sp, color = TextGray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = valueColor)
            }
        }
    }
}

@Composable
fun TabRowMenu(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                TextButton(
                    onClick = { onTabSelected(index) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) Color.Black else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(PrimaryPurple)
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineItem(
    event: TimelineEvent,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier.width(70.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = event.time,
                fontSize = 14.sp,
                color = TextGray,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 2.dp)
            )

            Canvas(modifier = Modifier
                .fillMaxHeight()
                .padding(start = 30.dp)) {

                val circleRadius = 4.dp.toPx()
                val lineStart = Offset(size.width / 2, 0f)
                val lineEnd = Offset(size.width / 2, size.height)

                if (!isLast) {
                    drawLine(
                        color = TimelineLineColor,
                        start = lineStart,
                        end = lineEnd,
                        strokeWidth = 2.dp.toPx()
                    )
                } else {
                    drawLine(
                        color = TimelineLineColor,
                        start = lineStart,
                        end = Offset(size.width / 2, 40.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                drawCircle(
                    color = Color.White,
                    radius = circleRadius,
                    center = Offset(size.width / 2, 24.dp.toPx())
                )
                drawCircle(
                    color = PrimaryPurple,
                    radius = circleRadius,
                    center = Offset(size.width / 2, 24.dp.toPx()),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                )
            }
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (event.iconRes != null) {
                        Image(
                            painter = painterResource(id = event.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = event.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = event.titleColor
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = event.dangerLevel, fontSize = 12.sp, color = Color.DarkGray)
                if (event.action != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = event.action, fontSize = 12.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = event.imageRes),
                    contentDescription = "썸네일",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                ) {
                    Text(
                        text = event.duration,
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AiSummaryCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F1FA)) // Light Purple Background
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "AI 분석 요약", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = PrimaryPurple)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "(14:23 이벤트)", fontSize = 12.sp, color = PrimaryPurple.copy(alpha = 0.7f))
                }
                Spacer(modifier = Modifier.height(12.dp))

                AiSummaryRow("행동:", "냄새 탐색")
                AiSummaryRow("위험 요소:", "전선 (멀티탭)")
                AiSummaryRow("조치:", "음성 경고 + 로봇 차단 이동")
                AiSummaryRow("위험도:", "높음")
            }

            Image(
                painter = painterResource(id = R.drawable.analysis),
                contentDescription = "AI 강아지",
                modifier = Modifier.size(140.dp)
            )
        }
    }
}

@Composable
fun AiSummaryRow(label: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(text = "•", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(end = 4.dp))
        Text(text = "$label ", color = Color.DarkGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = value, color = Color.DarkGray, fontSize = 12.sp)
    }
}