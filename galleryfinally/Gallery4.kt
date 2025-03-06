package com.minseok.galleryfinally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalContext
// Coil 라이브러리 임포트
import coil.compose.AsyncImage
import coil.request.ImageRequest

// OverlayImage 클래스는 별도 파일로 분리됨 (OverlayImage.kt)
// import com.minseok.galleryfinally.OverlayImage

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
class Gallery4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery4Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery4Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal401,
        R.drawable.gal402,
        R.drawable.gal403,
        R.drawable.gal404,
        R.drawable.gal405,
        R.drawable.gal406,
        R.drawable.gal407,
        R.drawable.gal408,
        R.drawable.gal409
    )

    // Titles for each gallery screen - 7개의 제목
    val screenTitles = listOf(
        "1970년대\n고도성장기의 교육환경",
        "1970년대의\n교육변화",
        "1970년대,\n교육 시설의 확충",
        "1980년대 이후\n민주화 시대의 교육환경",
        "1990년대\n본교의 발전",
        "1990년대 변화 속\n마산용마고의 출발",
        "1970~1990년대\n본관 전경의 변화"
    )

    // 화면과 제목 매핑 - 두 번째 제목과 세 번째 제목에 각각 두 개의 스크린이 매핑됨
    val screenToTitleIndex = listOf(0, 1, 1, 2, 2, 3, 4, 5, 6)

    // 각 페이지별 오버레이 이미지 설정 (gal4_1부터 gal4_22까지)
    val pageOverlayImages = listOf(
        // 페이지 1 (gal401): 첫 번째 제목 - 하나의 사진
        listOf(
            OverlayImage(R.drawable.gal4_1, Pair(-0.55f, 0.575f), Pair(300, 530)),
            OverlayImage(R.drawable.gal4_2, Pair(-0.09f, 0.575f), Pair(250, 530)),
        ),
        // 페이지 2 (gal402): 두 번째 제목 (첫 번째 화면) - 2개의 사진
        listOf(
            OverlayImage(R.drawable.gal4_3, Pair(-0.55f, 0.29f), Pair(300, 230)),
            OverlayImage(R.drawable.gal4_4, Pair(-0.55f, 0.74f), Pair(300, 220)),
        ),
        // 페이지 3 (gal403): 두 번째 제목 (두 번째 화면) - 2개의 사진
        listOf(
            OverlayImage(R.drawable.gal4_5, Pair(-0.045f, 0.54f), Pair(905,500)),
            OverlayImage(R.drawable.gal4_6, Pair(1.28f, 0.54f), Pair(630,500))
        ),
        // 페이지 4 (gal404): 세 번째 제목 (첫 번째 화면) - 5개의 사진
        listOf(
            OverlayImage(R.drawable.gal4_7, Pair(-0.625f, 0.24f), Pair(195, 133)),
            OverlayImage(R.drawable.gal4_8, Pair(-0.4f,  0.24f),Pair(80, 133)),
            OverlayImage(R.drawable.gal4_9, Pair(-0.13f,0.24f), Pair(183, 138)),
            OverlayImage(R.drawable.gal4_10, Pair(0.17f,0.24f), Pair(183, 138)),
            OverlayImage(R.drawable.gal4_11, Pair(-0.46f, 0.66f), Pair(400, 240)),
            OverlayImage(R.drawable.gal4_12, Pair(0.2f, 0.66f), Pair(340, 240)),
            OverlayImage(R.drawable.gal4_13, Pair(0.79f, 0.66f), Pair(335, 240)),
        ),
        // 페이지 5 (gal405): 세 번째 제목 (두 번째 화면) - 2개의 사진
        listOf(
            OverlayImage(R.drawable.gal4_14, Pair(-0.152f, 0.55f), Pair(770, 510)),
            OverlayImage(R.drawable.gal4_15, Pair(1.17f, 0.55f), Pair(770, 510))
        ),
        // 페이지 6 (gal406): 네 번째 제목 - 사진 없음
        listOf(),
        // 페이지 7 (gal407): 다섯 번째 제목 - 2개의 사진
        listOf(
        ),
        // 페이지 8 (gal408): 여섯 번째 제목 - 3개의 사진
        listOf(
        ),
        // 페이지 9 (gal409): 일곱 번째 제목 - 5개의 사진
        listOf(
            OverlayImage(R.drawable.gal4_16, Pair(-0.52f, 0.345f), Pair(335, 258)),
            OverlayImage(R.drawable.gal4_17, Pair(0.143f, 0.345f), Pair(400, 258)),
            OverlayImage(R.drawable.gal4_18, Pair(0.78f, 0.345f), Pair(330, 258)),
            OverlayImage(R.drawable.gal4_19,Pair(1.45f, 0.345f), Pair(435, 258)),
            OverlayImage(R.drawable.gal4_20, Pair(-0.58f, 0.79f), Pair(260, 200)),
            OverlayImage(R.drawable.gal4_21, Pair(-0.095f, 0.79f), Pair(275, 200)),
            OverlayImage(R.drawable.gal4_22, Pair(0.41f, 0.79f), Pair(275, 200))
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

    // 현재 페이지의 제목 인덱스 가져오기 (범위 체크)
    val currentTitleIndex = if (currentPage < screenToTitleIndex.size) screenToTitleIndex[currentPage] else 0

    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.gal4background)
                .crossfade(true)
                .build(),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top navigation bar with custom circular buttons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp, bottom = 1.dp)
            ) {
                // 버튼들을 담을 Row
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 65.dp), // 양쪽 여백 추가
                    horizontalArrangement = Arrangement.SpaceEvenly // 균등 간격
                ) {
                    // Navigation buttons (7개의 제목)
                    for (index in screenTitles.indices) {
                        // 전체 버튼 영역 (넓은 클릭 영역)
                        Box(
                            modifier = Modifier
                                .width(210.dp) // 클릭 가능한 넓은 영역
                                .height(70.dp)
                                .clickable {
                                    // 각 제목에 해당하는 첫 번째 페이지로 이동
                                    val targetPage = screenToTitleIndex.indexOfFirst { it == index }
                                    if (targetPage >= 0) {
                                        scope.launch {
                                            pagerState.animateScrollToPage(targetPage)
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            // 직접 만든 원형 버튼 배경
                            Box(
                                modifier = Modifier
                                    .width(260.dp)
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(28.dp)) // 타원형 모양을 위한 라운드 코너
                                    .background(
                                        if (index == currentTitleIndex)
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
                                    color = if (index == currentTitleIndex) Color.Black else Color(0xFF0095D0),
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

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
                            model = ImageRequest.Builder(context)
                                .data(galleryImages[page])
                                .crossfade(true)
                                .build(),
                            contentDescription = "Gallery image ${page + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )

                        // 오버레이 이미지들 (메인 이미지 위에 배치) - 인덱스 범위 체크 추가
                        if (page < pageOverlayImages.size) {
                            pageOverlayImages[page].forEach { overlayImage ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
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
                                            .alpha(0f) // 추가된 부분: 완전 투명하게 설정

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
        }

        // 이미지 확대 다이얼로그 - 800dp × 800dp 고정 크기
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

                    // 확대된 이미지 - Coil 사용, 800dp × 800dp 고정 크기
                    Box(
                        modifier = Modifier
                            .size(800.dp, 800.dp) // 요청대로 고정 크기 설정
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
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

        // 홈 버튼 (메인 화면으로 돌아가기)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 42.dp, end = 61.dp)
                    .size(65.dp)
                    .clickable {
                        // 메인 화면으로 돌아가기
                        (context as? ComponentActivity)?.finish()
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(R.drawable.home) // home.png 이미지 리소스
                        .crossfade(true)
                        .build(),
                    contentDescription = "홈으로",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun Gallery4ScreenPreview() {
    GalleryFinallyTheme {
        Gallery4Screen()
    }
}