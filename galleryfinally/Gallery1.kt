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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
// Coil 라이브러리 임포트
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Icon
import com.minseok.galleryfinally.OverlayImage

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
class Gallery1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GalleryScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal101,
        R.drawable.gal102,
        R.drawable.gal103,
        R.drawable.gal104,
        R.drawable.gal105,
        R.drawable.gal106,
        R.drawable.gal107,
        R.drawable.gal108
    )

    // Titles for each gallery screen
    val screenTitles = listOf(
        "마산 지역의\n교육환경",
        "마산공립상업학교\n설립 결의",
        "마산공립상업학교의\n첫걸음",
        "상남동 교사\n신축",
        "을종 3년제의\n한계",
        "갑종 5년제\n학제 개편",
        "상남동 교사\n증축",
        "산호동 교사\n신축과 이전"
    )

    // 각 페이지별 오버레이 이미지 설정
    val pageOverlayImages = listOf(
        // 페이지 1: 마산 지역의 교육환경
        listOf(
            OverlayImage(R.drawable.gal1_1, Pair(-0.55f, 0.53f), Pair(300, 480))
        ),
        // 페이지 2: 마산공립상업학교 설립 결의
        listOf(
            OverlayImage(R.drawable.gal1_2, Pair(-0.43f, 0.45f), Pair(440, 380)),
            OverlayImage(R.drawable.gal1_3, Pair(0.31f, 0.36f), Pair(380, 270)),
            OverlayImage(R.drawable.gal1_4, Pair(0.16f, 0.83f), Pair(205, 205))
        ),
        // 페이지 3: 마산공립상업학교의 첫걸음
        listOf(

            OverlayImage(R.drawable.gal1_5, Pair(-0.53f, 0.3f), Pair(330, 210)),
            OverlayImage(R.drawable.gal1_6, Pair(0.053f, 0.3f), Pair(335, 210)),
            OverlayImage(R.drawable.gal1_7, Pair(-0.53f, 0.75f), Pair(335, 210)),
            OverlayImage(R.drawable.gal1_8, Pair(-0.02f, 0.75f), Pair(230, 210))

        ),
        // 페이지 4: 상남동 교사 신축
        listOf(

            OverlayImage(R.drawable.gal1_9, Pair(0.045f, 0.3f), Pair(365, 210)),
            OverlayImage(R.drawable.gal1_10, Pair(0.045f, 0.73f), Pair(365, 210)),
            OverlayImage(R.drawable.gal1_11, Pair(-0.55f, 0.48f), Pair(300, 430)),
        ),
        // 페이지 5: 을종 3년제의 한계
        listOf(
            OverlayImage(R.drawable.gal1_12, Pair(-0.57f, 0.45f), Pair(275,395)),

            OverlayImage(R.drawable.gal1_13, Pair(0.11f, 0.45f), Pair(505, 395)),
        ),
        // 페이지 6: 갑종 5년제 학제 개편
        listOf(
            OverlayImage(R.drawable.gal1_14, Pair(-0.4f, 0.38f), Pair(500, 310)),
            OverlayImage(R.drawable.gal1_15, Pair(1.37f, 0.38f), Pair(510, 310)),
            OverlayImage(R.drawable.gal1_16, Pair(0.47f, 0.38f), Pair(505, 310)),
            OverlayImage(R.drawable.gal1_17, Pair(-0.71f, 0.865f), Pair(110, 160))
        ),
        // 페이지 7: 상남동 교사 증축
        listOf(
        ),
        // 페이지 8: 산호동 교사 신축과 이전
        listOf(
            OverlayImage(R.drawable.gal1_18, Pair(-0.5f, 0.32f), Pair(360, 239)),
            OverlayImage(R.drawable.gal1_19, Pair(-0.5f, 0.77f), Pair(360, 237))
        )
    )

    // 이미지 확대 상태 관리
    var expandedImageId by remember { mutableStateOf<Int?>(null) }

    // 중요: pagerState와 scope를 최상위 레벨로 이동
    val pagerState = rememberPagerState(pageCount = { galleryImages.size })
    val currentPage by rememberUpdatedState(pagerState.currentPage)
    val scope = rememberCoroutineScope()

    // 현재 컨텍스트 가져오기
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil
        AsyncImage(
            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                .data(R.drawable.gal1background)
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
                for (index in screenTitles.indices) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                // Start a coroutine to animate to the selected page
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                    ) {
                        // Active or inactive button based on current page using Coil
                        AsyncImage(
                            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(if (index == currentPage) R.drawable.activebutton else R.drawable.nobutton)
                                .crossfade(true)
                                .build(),
                            contentDescription = screenTitles[index],
                            modifier = Modifier
                                .size(200.dp, 90.dp) // 이미지 크기 조절
                                .alpha(if (index == currentPage) 1f else 0.7f) // 선택되지 않았을 때 투명도 조절
                        )
                        // Button text
                        Box(
                            modifier = Modifier.matchParentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = screenTitles[index],
                                color = if (index == currentPage) Color.Black else Color(0xFF0095D0), // SkyBlue 적용
                                modifier = Modifier.padding(horizontal = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold // 텍스트 굵게 설정
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

                        // 클릭 영역 설정 - 페이지별 오버레이 이미지에 대한 클릭 영역 처리
                        if (page == 0) { // 첫 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 480.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-330).dp, y = (18).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_1
                                    }
                            )
                        } else if (page == 1) { // 두 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 440.dp, height = 380.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-258).dp, y = (-30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_2
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 380.dp, height = 270.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (186).dp, y = (-84).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_3
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 205.dp, height = 205.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (96).dp, y = (198).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_4
                                    }
                            )
                        } else if (page == 2) { // 세 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 330.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-318).dp, y = (-120).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_5
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 335.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (32).dp, y = (-120).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_6
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 335.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-318).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_7
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 230.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-12).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_8
                                    }
                            )
                        } else if (page == 3) { // 네 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 365.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (27).dp, y = (-120).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_9
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 365.dp, height = 210.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (27).dp, y = (138).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_10
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 430.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-330).dp, y = (-12).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_11
                                    }
                            )
                        } else if (page == 4) { // 다섯 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 275.dp, height = 395.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-342).dp, y = (-30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_12
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 505.dp, height = 395.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (66).dp, y = (-30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_13
                                    }
                            )
                        } else if (page == 5) { // 여섯 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 500.dp, height = 310.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-240).dp, y = (-72).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_14
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 510.dp, height = 310.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (822).dp, y = (-72).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_15
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 505.dp, height = 310.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (282).dp, y = (-72).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_16
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 110.dp, height = 160.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-426).dp, y = (219).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_17
                                    }
                            )
                        } else if (page == 7) { // 여덟 번째 페이지
                            Box(
                                modifier = Modifier
                                    .size(width = 360.dp, height = 239.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-300).dp, y = (-104).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_18
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 360.dp, height = 237.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-300).dp, y = (162).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal1_19
                                    }
                            )
                        }

                        // 오버레이 이미지들 (이제 완전 투명하게 설정)
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

        // 이미지 확대 다이얼로그 - 크기 조정
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
                    contentAlignment = Alignment.Center
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

                    // 확대된 이미지를 위한 Box - 크기 증가
                    Box(
                        modifier = Modifier
                            .size(1200.dp, 900.dp)
                            .background(Color.Transparent)
                            .clickable(onClick = {}, enabled = false),
                        contentAlignment = Alignment.Center
                    ) {
                        // 확대된 이미지
                        AsyncImage(
                            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(imageId)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Expanded image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { expandedImageId = null },
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        // 홈 버튼 - 이 부분만 수정 (메인 화면으로 돌아가도록)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
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