package org.androidtown.mungnyangguardian

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//컬러 팔레트
private val Cream = Color(0xFFFFFCF7)
private val Brown = Color(0xFF5D4635)
private val CardBorder = Color(0xFFECE8E2)
private val TextGray = Color(0xFF999999)

private val BgMic = Color(0xFF909DB0)
private val BgBell = Color(0xFFF2A133)
private val BgSparkle = Color(0xFF77C83C)
private val BgNoti = Color(0xFFA89DF5)
private val BgBlock = Color(0xFFEA6B6B)
private val BgRobot = Color(0xFF6F7B8B)
private val BgInfo = Color(0xFF9BA3B0)

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Cream),
        contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)
    ) {
        item {
            Text(
                text = "설정",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Brown,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ProfileCard()
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SettingsListCard()
        }
    }
}

@Composable
fun ProfileCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(1.dp, CardBorder, CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dogtori), // 기존에 사용하던 강아지 아이콘
                    contentDescription = "프로필 이미지",
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 프로필 정보
            Column {
                Text(text = "우리 강아지", fontSize = 12.sp, color = TextGray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "코코", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Brown)
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Text(text = "종: ", fontSize = 11.sp, color = TextGray)
                    Text(text = "푸들", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "생일: ", fontSize = 11.sp, color = TextGray)
                    Text(text = "2021.05.20", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextGray)
                }
            }
        }
    }
}

@Composable
fun SettingsListCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(CardBorder)
        )
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            SettingItemRow(iconRes = R.drawable.speak, iconBg = BgMic, title = "보호자 목소리", subtitle = "등록된 목소리로 경고를 보내요")
            SettingItemRow(iconRes = R.drawable.notion, iconBg = BgBell, title = "경고 톤 설정", subtitle = "다정하게")
            SettingItemRow(iconRes = R.drawable.sparkle, iconBg = BgSparkle, title = "AI 민감도 설정", subtitle = "중간")
            SettingItemRow(iconRes = R.drawable.noti, iconBg = BgNoti, title = "알림 설정", subtitle = "ON")
            SettingItemRow(iconRes = R.drawable.cancel, iconBg = BgBlock, title = "금지 구역 관리", subtitle = "3개 설정됨")
            SettingItemRow(iconRes = R.drawable.robot2, iconBg = BgRobot, title = "로봇 설정", subtitle = "로봇 정보 및 업데이트")
            SettingItemRow(iconRes = R.drawable.info, iconBg = BgInfo, title = "앱 정보", subtitle = "버전 1.0.0", isLast = true)
        }
    }
}

@Composable
fun SettingItemRow(
    iconRes: Int,
    iconBg: Color,
    title: String,
    subtitle: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: 클릭 이벤트 처리 */ }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Brown)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 12.sp, color = TextGray)
        }

        Text(
            text = "›",
            fontSize = 24.sp,
            color = Color(0xFFCCCCCC),
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}