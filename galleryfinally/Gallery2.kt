package com.minseok.galleryfinally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.minseok.galleryfinally.ui.theme.GalleryFinallyTheme
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
// Coil 라이브러리 임포트
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material3.Icon
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.compose.ui.platform.LocalContext
import com.minseok.galleryfinally.OverlayImage


// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
class Gallery2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery2Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery2Screen(modifier: Modifier = Modifier) {


    // 현재 컨텍스트 가져오기
    val context = LocalContext.current
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal201,
        R.drawable.gal202,
        R.drawable.gal203,
        R.drawable.gal204,
        R.drawable.gal205
    )

    // Titles for each gallery screen
    val screenTitles = listOf(
        "4년제 학제 개편의 배경",
        "마산공립상업중학교",
        "마산상업중학교로 교명 변경",
        "응원가의 등장",
        "교지 「용마」"
    )

    // 각 페이지별 오버레이 이미지 설정
    val pageOverlayImages = listOf(
        // 페이지 1: 마산 지역의 교육환경 - 한장
        listOf(
            OverlayImage(R.drawable.gal2_1, Pair(-0.28f, 0.515f), Pair(620, 460))
        ),
        // 페이지 2: 마산공립상업학교 설립 결의 - 세장
        listOf(
            OverlayImage(R.drawable.gal2_2, Pair(-0.56f, 0.31f), Pair(280, 210)),
            OverlayImage(R.drawable.gal2_3, Pair(-0.1f, 0.425f), Pair(230, 350)),
            OverlayImage(R.drawable.gal2_4, Pair(-0.57f, 0.79f), Pair(280, 250))
        ),
        // 페이지 3: 마산공립상업학교의 첫걸음 - 없음
        listOf(),
        // 페이지 4: 상남동 교사 신축 - 한장
        listOf(
            OverlayImage(R.drawable.gal2_5, Pair(0.0f, 0.49f), Pair(350, 450))
        ),
        // 페이지 5: 을종 3년제의 한계 - 네장
        listOf(
            OverlayImage(R.drawable.gal2_6, Pair(-0.67f, 0.29f), Pair(150, 200)),
            OverlayImage(R.drawable.gal2_7, Pair(-0.4f, 0.29f), Pair(150, 200)),
            OverlayImage(R.drawable.gal2_8, Pair(-0.13f, 0.29f), Pair(150, 200)),
            OverlayImage(R.drawable.gal2_9, Pair(-0.418f, 0.745f), Pair(455, 225))
        )
    )

    // 이미지 확대 상태 관리
    var expandedImageId by remember { mutableStateOf<Int?>(null) }

    // 중요: pagerState와 scope를 최상위 레벨로 이동
    val pagerState = rememberPagerState(pageCount = { galleryImages.size })
    val currentPage by rememberUpdatedState(pagerState.currentPage)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil
        AsyncImage(
            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                .data(R.drawable.gal2background)
                .crossfade(true)
                .build(),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top navigation bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp, bottom = 1.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Navigation buttons
// Navigation buttons
// Navigation buttons
                for (index in screenTitles.indices) {
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(70.dp)
                            .clickable {
                                // Start a coroutine to animate to the selected page
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // 직접 만든 버튼 배경
                        Box(
                            modifier = Modifier
                                .width(320.dp)
                                .height(70.dp)
                                .clip(RoundedCornerShape(30.dp)) // 둥근 모서리 적용
                                .background(
                                    if (index == currentPage)
                                        Color(0xFF0095D0) // 활성화된 버튼 색상
                                    else
                                        Color.Black.copy(alpha = 0.3f) // 비활성화된 버튼 색상
                                )
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // 버튼 텍스트
                            Text(
                                text = screenTitles[index],
                                color = if (index == currentPage) Color.Black else Color(0xFF0095D0),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Main content pager with overlay images
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // 하나의 페이저에 메인 이미지와 오버레이 이미지를 함께 배치
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    // 각 페이지는 Box로 감싸서 메인 이미지와 오버레이 이미지를 함께 배치
                    Box(modifier = Modifier.fillMaxSize()) {
                        // 메인 배경 이미지
                        AsyncImage(
                            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(galleryImages[page])
                                .crossfade(true)
                                .build(),
                            contentDescription = "Gallery image ${page + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )

                        // 클릭 영역 설정 - 페이지별 클릭 영역
                        if (page == 0) {
                            // 첫 번째 페이지 - 한장
                            Box(
                                modifier = Modifier
                                    .size(width = 620.dp, height = 460.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-168).dp, y = (9).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_1
                                    }
                            )
                        } else if (page == 1) {
                            // 두 번째 페이지 - 세장
                            Box(
                                modifier = Modifier
                                    .size(width = 280.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-336).dp, y = (-114).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_2
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 230.dp, height = 350.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-60).dp, y = (-45).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_3
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 280.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-342).dp, y = (174).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_4
                                    }
                            )
                        } else if (page == 3) {
                            // 네 번째 페이지 - 한장
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 450.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (0).dp, y = (-6).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_5
                                    }
                            )
                        } else if (page == 4) {
                            // 다섯 번째 페이지 - 네장
                            Box(
                                modifier = Modifier
                                    .size(width = 150.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-402).dp, y = (-126).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_6
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 150.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-240).dp, y = (-126).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_7
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 150.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-78).dp, y = (-126).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_8
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 455.dp, height = 225.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-250.8).dp, y = (147).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal2_9
                                    }
                            )
                        }

                        // 오버레이 이미지들 (메인 이미지 위에 배치) - 완전 투명하게 설정
                        pageOverlayImages[page].forEach { overlayImage ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                        .data(overlayImage.resourceId)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Overlay image",
                                    modifier = Modifier
                                        .size(
                                            width = overlayImage.size.first.dp,
                                            height = overlayImage.size.second.dp
                                        )
                                        .align(Alignment.Center)
                                        .offset(
                                            x = ((overlayImage.position.first - 0.5f) * 600).dp,
                                            y = ((overlayImage.position.second - 0.5f) * 600).dp
                                        )
                                        .alpha(0f) // 완전 투명하게 설정
                                        .clickable {
                                            expandedImageId = overlayImage.resourceId
                                        },
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }
            }
        }

        // 이미지 확대 다이얼로그 - 크기 변경
        expandedImageId?.let { imageId ->
            Dialog(
                onDismissRequest = { expandedImageId = null },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                        .clickable { expandedImageId = null },
                    contentAlignment = Alignment.Center // 다이얼로그 중앙에 이미지 배치
                ) {
                    // 닫기 버튼
                    IconButton(
                        onClick = { expandedImageId = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .zIndex(10f)
                    ) {
                        Text(
                            text = "X",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 확대된 이미지 - Coil 사용, 크기 조정
                    Box(
                        modifier = Modifier
                            .size(1200.dp, 900.dp) // 크기 조정
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(imageId)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Expanded image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        // 홈 버튼
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd) // 또는 원하는 위치
                .padding(16.dp)
                .size(65.dp)
                .offset(-45.dp)
                .offset(y = 26.dp)
                .clickable {
                    // 메인 화면으로 돌아가기
                    (context as? ComponentActivity)?.finish()
                }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                    .data(R.drawable.home) // 이미지 리소스 이름
                    .crossfade(true)
                    .build(),
                contentDescription = "홈으로",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
