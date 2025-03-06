package com.minseok.galleryfinally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.minseok.galleryfinally.ui.theme.GalleryFinallyTheme
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
// Coil 라이브러리 임포트
import coil.compose.AsyncImage
import coil.request.ImageRequest

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
class Gallery6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery6Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery6Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal601,
        R.drawable.gal602,
        R.drawable.gal603,
        R.drawable.gal604,
        R.drawable.gal605,
        R.drawable.gal606,
        R.drawable.gal607,
        R.drawable.gal608,
        R.drawable.gal609,
        R.drawable.gal610,
        R.drawable.gal611,
        R.drawable.gal612,
        R.drawable.gal613,
        R.drawable.gal614,
        R.drawable.gal615,
        R.drawable.gal616,
        R.drawable.gal617,
        R.drawable.gal618
    )

    // Titles for each gallery screen - 5개의 제목
    val screenTitles = listOf(
        "초기의 체육활동",
        "야구",
        "씨름",
        "축구",
        "육상"
    )

    // 화면과 제목 매핑
    // - 첫 번째 제목(초기의 체육활동)에 1개의 스크린 (0)
    // - 두 번째 제목(야구)에 6개의 스크린 (1-6)
    // - 세 번째 제목(씨름)에 8개의 스크린 (7-14)
    // - 네 번째 제목(축구)에 2개의 스크린 (15-16)
    // - 다섯 번째 제목(육상)에 1개의 스크린 (17)
    val screenToTitleIndex = listOf(0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 4)

    // 각 페이지별 오버레이 이미지 설정 (gal6_1부터 gal6_28까지)
    val pageOverlayImages = listOf(
        // 페이지 1 (gal601): 초기의 체육활동
        listOf(
            OverlayImage(R.drawable.gal6_1, Pair(-0.47f, 0.82f), Pair(400, 180)),
            OverlayImage(R.drawable.gal6_2, Pair(0.546f, 0.82f), Pair(780, 180)),
        ),

        // 페이지 2-7 (gal602-gal607): 야구
        listOf<OverlayImage>(),
        listOf<OverlayImage>(),
        listOf(
            OverlayImage(R.drawable.gal6_8, Pair(-0.648f, 0.135f), Pair(195, 165)),
            OverlayImage(R.drawable.gal6_9, Pair(-0.647f, 0.44f), Pair(195, 160)),
            OverlayImage(R.drawable.gal6_10, Pair(-0.639f, 0.82f), Pair(185, 225))
        ),
        listOf<OverlayImage>(),
        //5번째 페이지임 아래로 6번째 페이지
        listOf<OverlayImage>(),
        //7페이지
        listOf<OverlayImage>(),
        //8페이지
        // 페이지 8-15 (gal608-gal615): 씨름
        listOf<OverlayImage>(),
        //9페이지
        listOf<OverlayImage>(),
        listOf(
            OverlayImage(R.drawable.gal6_19, Pair(-0.53f, 0.24f), Pair(340, 230)),
        ),
        listOf(
            OverlayImage(R.drawable.gal6_20, Pair(-0.16f, 0.35f), Pair(765, 335))
        ),
        listOf(
            OverlayImage(R.drawable.gal6_21, Pair(-0.428f, 0.518f), Pair(445, 530))
        ),
        listOf(
            OverlayImage(R.drawable.gal6_22, Pair(-0.4075f, 0.518f), Pair(465, 530))
        ),
        listOf(
            OverlayImage(R.drawable.gal6_23, Pair(-0.519f, 0.509f), Pair(330, 525)),
        ),
        listOf(
            OverlayImage(R.drawable.gal6_24, Pair(-0.519f, 0.49f), Pair(330, 500)),
        ),
        // 페이지 16-17 (gal616-gal617): 축구
        listOf<OverlayImage>(),
        // 페이지 17 (gal617): 투명 처리
        listOf<OverlayImage>(),
        // 페이지 18 (gal618): 투명 처리
        listOf<OverlayImage>(),
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
    val currentTitleIndex =
        if (currentPage < screenToTitleIndex.size) screenToTitleIndex[currentPage] else 0

    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.gal6background)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp), // 양쪽 여백 더 줄임
                    horizontalArrangement = Arrangement.SpaceBetween // 버튼 사이 간격 균등 분배
                ) {
                    // Navigation buttons (5개의 제목)
                    for (index in screenTitles.indices) {
                        // 전체 버튼 영역 (넓은 클릭 영역)
                        Box(
                            modifier = Modifier
                                .width(300.dp) // 클릭 가능한 영역 너비 300dp로 확장
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
                            // 직접 만든 원형(타원형) 버튼 배경
                            Box(
                                modifier = Modifier
                                    .width(300.dp) // 버튼 너비 300dp로 확장
                                    .height(60.dp) // 높이는 그대로 유지
                                    .clip(RoundedCornerShape(30.dp)) // 타원형 모양 유지
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
                                    fontSize = 19.sp,
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

                        if (page == 1) { // 두 번째 페이지 (gal602)
                            // gal6_3을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 130.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.09f - 0.5f) * 600).dp,
                                        y = ((0.495f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_3
                                    }
                            )

                            // gal6_4를 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 500.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.7f - 0.5f) * 600).dp,
                                        y = ((0.5f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_4
                                    }
                            )

                            // gal6_5를 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((0.4f - 0.5f) * 600).dp,
                                        y = ((0.82f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_5
                                    }
                            )
                        } else if (page == 2) { // 세 번째 페이지 (gal603)
                            // gal6_6을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 400.dp, height = 360.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.7f - 0.5f) * 600).dp,
                                        y = ((0.38f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_6
                                    }
                            )

                            // gal6_7을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 250.dp, height = 350.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.1f - 0.5f) * 600).dp,
                                        y = ((0.3f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_7
                                    }
                            )
                        } else if (page == 4) { // 다섯 번째 페이지 (gal605)
                            // gal6_11을 위한 클릭 영역 (왼쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 700.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.3f - 0.5f) * 600).dp,
                                        y = ((0.7f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_11
                                    }
                            )

                            // gal6_12를 위한 클릭 영역 (오른쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((0.6f - 0.5f) * 600).dp,
                                        y = ((0.7f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_12
                                    }
                            )
                        } else if (page == 5) { // 여섯 번째 페이지 (gal606)
                            // gal6_13을 위한 클릭 영역 (왼쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 400.dp, height = 300.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.5f - 0.5f) * 600).dp,
                                        y = ((0.25f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_13
                                    }
                            )

                            // gal6_14를 위한 클릭 영역 (오른쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 400.dp, height = 300.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.5f - 0.5f) * 600).dp,
                                        y = ((0.8f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_14
                                    }
                            )
                        } else if (page == 6) { // 여덟 번째 페이지 (gal608)
                            // gal6_15를 위한 클릭 영역 (중앙)
                            Box(
                                modifier = Modifier
                                    .size(width = 1400.dp, height = 700.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((0.5f - 0.5f) * 600).dp,
                                        y = ((0.5f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_15
                                    }
                            )
                        } else if (page == 7) { // 여덟 번째 페이지 (gal608)
                            Box(
                                modifier = Modifier
                                    .size(width = 600.dp, height = 450.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.4f - 0.5f) * 600).dp,
                                        y = ((0.5f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_16
                                    }
                            )
                        } else if (page == 8) { // 아홉 번째 페이지 (gal609)
                            // gal6_17을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 450.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.6f - 0.5f) * 600).dp,
                                        y = ((0.45f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_17
                                    }
                            )

                            // gal6_18을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 500.dp, height = 500.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((0.15f - 0.5f) * 600).dp,
                                        y = ((0.4f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_18
                                    }
                            )
                        } else if (page == 15) { // 16번째 페이지 (gal616)
                            // gal6_25를 위한 클릭 영역 (왼쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 420.dp, height = 230.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.5f - 0.5f) * 600).dp,
                                        y = ((0.3f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_25
                                    }
                            )

                            // gal6_26을 위한 클릭 영역 (오른쪽)
                            Box(
                                modifier = Modifier
                                    .size(width = 420.dp, height = 230.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.5f - 0.5f) * 600).dp,
                                        y = ((0.75f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_26
                                    }
                            )
                        } else if (page == 16) { // 17번째 페이지 (gal617)
                            // gal6_27을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 570.dp, height = 485.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.34f - 0.5f) * 600).dp,
                                        y = ((0.475f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_27
                                    }
                            )
                        } else if (page == 17) { // 18번째 페이지 (gal618)
                            // gal6_28을 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 450.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.6f - 0.5f) * 600).dp,
                                        y = ((0.5f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_28
                                    }
                            )

                            // gal6_29를 위한 클릭 영역
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 290.dp)
                                    .align(Alignment.Center)
                                    .offset(
                                        x = ((-0.05f - 0.5f) * 600).dp, // 위치 조정 필요할 수 있음
                                        y = ((0.8f - 0.5f) * 600).dp
                                    )
                                    .clickable {
                                        expandedImageId = R.drawable.gal6_29
                                    }
                            )
                        }

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

        // 이미지 확대 다이얼로그 - Box와 이미지 크기 모두 조정
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

                    // 확대된 이미지를 위한 Box - gal6_15인 경우 더 크게
                    Box(
                        modifier = Modifier
                            .size(
                                width = when (imageId) {
                                    R.drawable.gal6_15 -> 1500.dp  // 특별히 큰 이미지는 그대로 유지
                                    R.drawable.gal6_1, R.drawable.gal6_2 -> 1400.dp  // 첫 번째, 두 번째 사진 더 크게
                                    else -> 1200.dp  // 나머지 이미지는 1200dp로 크기 증가
                                },
                                height = when (imageId) {
                                    R.drawable.gal6_15 -> 1500.dp
                                    R.drawable.gal6_1, R.drawable.gal6_2 -> 1600.dp  // 첫 번째, 두 번째 사진 더 크게
                                    else -> 1200.dp
                                }
                            )
                            .background(Color.Transparent)
                            .clickable(onClick = {}, enabled = false),
                        contentAlignment = Alignment.Center
                    ) {
                        // 확대된 이미지
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(imageId)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Expanded image",
                            modifier = Modifier
                                .fillMaxSize() // Box의 크기에 맞게 이미지 크기 설정
                                .clickable { expandedImageId = null },
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

