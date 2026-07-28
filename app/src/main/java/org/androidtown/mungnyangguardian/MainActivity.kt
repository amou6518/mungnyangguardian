package org.androidtown.mungnyangguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// 컬러 팔레트
private val Cream = Color(0xFFFFFCF7)
private val Green = Color(0xFF70A866)
private val LightGreen = Color(0xFFEAF5E7)
private val Brown = Color(0xFF5D4635)
private val CardBorder = Color(0xFFECE8E2)
private val Red = Color(0xFFE95D4D)
private val LightPurple = Color(0xFFEDEBFA)
private val Blue = Color(0xFFE9F1FF)
private val Orange = Color(0xFFFFF0EC)
private val AlertRed = Color(0xFFD34F4F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Green,
                    background = Cream,
                    surface = Color.White
                )
            ) {
                MungNyangApp()
            }
        }
    }
}

@Composable
fun MungNyangApp() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Cream,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                BottomTab(
                    selectedIconRes = R.drawable.home,
                    unselectedIconRes = R.drawable.homegray,
                    label = "홈",
                    selected = selectedTab == 0
                ) { selectedTab = 0 }
                BottomTab(
                    selectedIconRes = R.drawable.map,
                    unselectedIconRes = R.drawable.mapgray,
                    label = "지도",
                    selected = selectedTab == 1
                ) { selectedTab = 1 }
                BottomTab(
                    selectedIconRes = R.drawable.log,
                    unselectedIconRes = R.drawable.loggray,
                    label = "기록",
                    selected = selectedTab == 2
                ) { selectedTab = 2 }
                BottomTab(
                    selectedIconRes = R.drawable.setting,
                    unselectedIconRes = R.drawable.settinggray,
                    label = "설정",
                    selected = selectedTab == 3
                ) { selectedTab = 3 }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> HomeScreen(Modifier.padding(padding))
            1 -> MapScreen(Modifier.padding(padding))
            2 -> LogScreen(Modifier.padding(padding))
            3 -> SettingScreen(Modifier.padding(padding))
            else -> ComingSoonScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var showWarningDialog by remember { mutableStateOf(false) }

    if (showWarningDialog) {
        WarningDialog(onDismiss = { showWarningDialog = false })
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { TopBar() }
        item { SafetyCard() }

        item {
            Column {
                SectionTitle("실시간 라이브", "")
                Spacer(Modifier.height(16.dp))
                LiveCameraCard()
            }
        }
        item {
            Column {
                SectionTitle("AI 상태 요약", "더보기 ›")
                Spacer(Modifier.height(16.dp))
                AiStatusRow()
            }
        }

        item {
            QuickActionGrid(onWarningClick = { showWarningDialog = true })
        }
    }
}

@Composable
fun WarningDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.alert_red),
                            contentDescription = "경고",
                            tint = AlertRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "위험 상황이 감지되었어요!",
                            color = AlertRed,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "전선에 접근하고 있어요.\n로봇이 차단 중이에요.",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Image(
                        painter = painterResource(R.drawable.danger),
                        contentDescription = "위험 상황 사진",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { /* TODO: 상세보기 이동 이벤트 */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green)
                        ) {
                            Text(text = "상세 보기", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Orange)
                        ) {
                            Text(text = "확인", color = Brown, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(86.dp)
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.dogtori),
                    contentDescription = "프로필",
                    modifier = Modifier.size(76.dp)
                )
            }
        }
    }
}

@Composable
fun TopBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "멍냥 수호대",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Brown
            )
            Spacer(Modifier.width(6.dp))
            Image(
                painter = painterResource(R.drawable.dotori),
                contentDescription = "도토리 로고",
                modifier = Modifier.size(40.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.notion),
            contentDescription = "알림",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(26.dp)
        )
    }
}

@Composable
fun SafetyCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = LightGreen),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().height(90.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxHeight().fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
                Image(
                    painter = painterResource(R.drawable.guard),
                    contentDescription = "안전 아이콘",
                    modifier = Modifier.size(40.dp)
                )

            Spacer(Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "모두 안전해요!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Green
                )
                Text(
                    text = "오늘도 평온한 하루예요",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Image(
                painter = painterResource(R.drawable.dog),
                contentDescription = "도토리 강아지",
                modifier = Modifier.requiredSize(90.dp)
            )
        }
    }
}

@Composable
fun LiveCameraCard() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(CardBorder)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // 실제 카메라 스트림을 붙일 자리 (지금은 라이브 이미지로 프리뷰)
            Image(
                painter = painterResource(R.drawable.live),
                contentDescription = "실시간 카메라 프리뷰",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Surface(
                color = Red,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Text(
                    text = "● LIVE",
                    color = Color.White,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircleIconButton(iconRes = R.drawable.sound)
                CircleIconButton(iconRes = R.drawable.zoom)
            }
        }
    }
}

@Composable
fun CircleIconButton(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "컨트롤 아이콘",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun AiStatusRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatusBox(
            iconRes = R.drawable.pet,
            iconBg = LightPurple,
            title = "현재 행동",
            value = "탐색 중",
            valueColor = Brown,
            modifier = Modifier.weight(1f)
        )
        StatusBox(
            iconRes = R.drawable.smile,
            iconBg = LightGreen,
            title = "위험도",
            value = "낮음",
            valueColor = Green,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatusBox(
    iconRes: Int,
    iconBg: Color,
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(CardBorder)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = title, fontSize = 11.sp, color = Color.Gray)
                Text(text = value, fontWeight = FontWeight.Bold, color = valueColor, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun QuickActionGrid(onWarningClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickButton(R.drawable.speak, "TTS", LightGreen, modifier = Modifier.weight(1f))
        QuickButton(R.drawable.play, "간식 타임", Blue, modifier = Modifier.weight(1f))
        QuickButton(R.drawable.alert, "경고 보내기", Orange, modifier = Modifier.weight(1f), onClick = onWarningClick)
    }
}

@Composable
fun QuickButton(
    iconRes: Int,
    title: String,
    bgColor: Color,
    modifier: Modifier = Modifier ,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(text = title, fontSize = 12.sp, color = Brown, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SectionTitle(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Brown,
            modifier = Modifier.weight(1f)
        )

        if (action.isNotBlank()) {
            if (action == "LIVE") {
                Surface(color = Red, shape = RoundedCornerShape(8.dp)) {
                    Text(
                        text = "● LIVE",
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            } else {
                Text(text = action, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun RowScope.BottomTab(
    selectedIconRes: Int,
    unselectedIconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Image(
                painter = painterResource(if (selected) selectedIconRes else unselectedIconRes),
                contentDescription = label,
                modifier = Modifier.size(30.dp)
            )
        },
        label = { Text(label, fontSize = 11.sp) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Green,
            selectedTextColor = Green,
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray,
            indicatorColor = LightGreen
        )
    )
}

@Composable
fun ComingSoonScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("준비 중인 화면입니다 🐾", color = Color.Gray)
    }
}