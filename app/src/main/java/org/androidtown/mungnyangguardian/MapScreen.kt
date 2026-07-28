package org.androidtown.mungnyangguardian

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//컬러 팔레트
private val Cream = Color(0xFFFFFCF7)
private val Green = Color(0xFF70A866)
private val LightGreen = Color(0xFFEAF5E7)
private val Brown = Color(0xFF5D4635)
private val CardBorder = Color(0xFFECE8E2)
private val Red = Color(0xFFE95D4D)
private val LightRed = Color(0xFFFFF0F0)
private val LightPurple = Color(0xFFEDEBFA)
private val Orange = Color(0xFFFFF0EC)

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Cream),
        contentPadding = PaddingValues(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "집 지도",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Brown
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(iconRes = R.drawable.battery, text = "배터리 85%")
                StatusChip(iconRes = R.drawable.wifi, text = "연결 상태 좋음")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }


        item {
            Image(
                painter = painterResource(id = R.drawable.exmap),
                contentDescription = "집 평면도 및 로봇 위치",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }


        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoCard(
                    iconRes = R.drawable.robot,
                    iconBg = LightPurple,
                    title = "현재 위치",
                    value = "거실",
                    modifier = Modifier.weight(1f)
                )
                InfoCard(
                    iconRes = R.drawable.dog,
                    iconBg = Orange,
                    title = "반려 동물",
                    value = "거실 소파 근처",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }


        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(
                    iconRes = R.drawable.location,
                    text = "특정 위치 이동",
                    textColor = Green,
                    bgColor = LightGreen
                )
                ActionButton(
                    iconRes = R.drawable.cancel,
                    text = "금지 구역 설정",
                    textColor = Red,
                    bgColor = LightRed
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun StatusChip(iconRes: Int, text: String) {
    Row(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .border(width = 1.dp, color = CardBorder, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Green,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun InfoCard(
    iconRes: Int,
    iconBg: Color,
    title: String,
    value: String,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(36.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, fontSize = 11.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Brown)
            }
        }
    }
}

@Composable
fun ActionButton(
    iconRes: Int,
    text: String,
    textColor: Color,
    bgColor: Color
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { /* TODO: 클릭 이벤트 처리 */ }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
