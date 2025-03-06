package com.minseok.galleryfinally

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.minseok.galleryfinally.ui.theme.GalleryFinallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                VerticalPanelsGallery()
            }
        }
    }
}

@Composable
fun VerticalPanelsGallery() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.fianll_bak),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 45.dp), // 전체 컨텐츠에 아래쪽 패딩 추가
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title

            // Single row of vertical panels
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Panel 1: 태동기 - 클릭 시 Gallery1 액티비티로 이동
                VerticalPanel(
                    imageResId = R.drawable.fianll_1,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery1 액티비티로 이동
                        val intent = Intent(context, Gallery1::class.java)
                        context.startActivity(intent)
                    }
                )


                // Panel 2: 전환기 - 클릭 시 Gallery2 액티비티로 이동
                VerticalPanel(
                    imageResId = R.drawable.fianll_2,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery2 액티비티로 이동
                        val intent = Intent(context, Gallery2::class.java)
                        context.startActivity(intent)
                    }
                )

// Panel 3: 격동기 - 클릭 시 Gallery3 액티비티로 이동
                VerticalPanel(
                    imageResId = R.drawable.fianll_3,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery3 액티비티로 이동
                        val intent = Intent(context, Gallery3::class.java)
                        context.startActivity(intent)
                    }
                )
                // Panel 4: 변혁기 - 클릭 시 Gallery4 액티비티로 이동
                VerticalPanel(
                    imageResId = R.drawable.fianll_4,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery4 액티비티로 이동
                        val intent = Intent(context, Gallery4::class.java)
                        context.startActivity(intent)
                    }
                )

                // Panel 5: 부흥기 - 클릭 시 Gallery5 액티비티로 이동
                VerticalPanel(
                    imageResId = R.drawable.fianll_5,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery5 액티비티로 이동
                        val intent = Intent(context, Gallery5::class.java)
                        context.startActivity(intent)
                    }
                )

                VerticalPanel(
                    imageResId = R.drawable.fianll_6,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery5 액티비티로 이동
                        val intent = Intent(context, Gallery6::class.java)
                        context.startActivity(intent)
                    }
                )

                // Panel 7: 3.15의거
                VerticalPanel(
                    imageResId = R.drawable.fianll_7,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery5 액티비티로 이동
                        val intent = Intent(context, Gallery7::class.java)
                        context.startActivity(intent)
                    }
                )

                // Panel 8: 총동창회
                VerticalPanel(
                    imageResId = R.drawable.fianll_8,
                    title = "",
                    textColor = Color(0xFF00BFFF),
                    onClick = {
                        // Gallery5 액티비티로 이동
                        val intent = Intent(context, Gallery8::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun VerticalPanel(
    imageResId: Int,
    title: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(165.dp)
            .height(720.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(30.dp), // 라운드 처리 추가 (24dp 곡률)
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 이 부분 추가

    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Label at the bottom
            Text(
                text = title,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}