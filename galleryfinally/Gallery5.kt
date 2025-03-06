package com.minseok.galleryfinally
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
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
class Gallery5 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery5Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery5Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal501,
        R.drawable.gal502,
        R.drawable.gal503,
        R.drawable.gal504,
        R.drawable.gal505,
        R.drawable.gal506,
        R.drawable.gal507,
        R.drawable.gal508,
        R.drawable.gal509,
        R.drawable.gal510,
        R.drawable.gal511
    )

    // Titles for each gallery screen - 7개의 제목
    val screenTitles = listOf(
        "인문학교로의\n전환 배경",
        "인문학교로의\n전환 과정",
        "2001년대 교육환경의\n변화와 학제 변경",
        "100주년 기념식과\n동문 화합 행사",
        "예술과 체육을 통한\n동문 화합",
        "기록물 발간,\n미래를 위한 기반 조성",
        "학교 연혁"
    )

    // 화면과 제목 매핑
    // - 첫 번째 제목에 두 개의 스크린 (0, 1)
    // - 두 번째 제목에 세 개의 스크린 (2, 3, 4)
    // - 세 번째 제목에 한 개의 스크린 (5)
    // - 네 번째 제목에 두 개의 스크린 (6, 7)
    // - 다섯 번째 제목에 한 개의 스크린 (8)
    // - 여섯 번째 제목에 한 개의 스크린 (9)
    // - 일곱 번째 제목에 한 개의 스크린 (10)
    val screenToTitleIndex = listOf(0, 0, 1, 1, 1, 2, 3, 3, 4, 5, 6)

    // 각 페이지별 오버레이 이미지 설정 (gal5_1부터 gal5_21까지)
    val pageOverlayImages = listOf(
        // 페이지 1 (gal501): 첫 번째 제목 (첫 번째 화면)
        listOf(
        ),
        // 페이지 2 (gal502): 첫 번째 제목 (두 번째 화면)
        listOf(
            OverlayImage(R.drawable.gal5_1, Pair(-0.2f, 0.5f), Pair(380, 540)),
            OverlayImage(R.drawable.gal5_2, Pair(0.5f, 0.5f), Pair(400, 540)),
            OverlayImage(R.drawable.gal5_3,  Pair(1.21f, 0.5f), Pair(410, 540))
        ),
        // 페이지 3 (gal503): 두 번째 제목 (첫 번째 화면)
        listOf(
        ),
        // 페이지 4 (gal504): 두 번째 제목 (두 번째 화면)
        listOf(
        ),
        // 페이지 5 (gal505): 두 번째 제목 (세 번째 화면)
        listOf(
            OverlayImage(R.drawable.gal5_4, Pair(-0.39f, 0.54f), Pair(500,370)),
            OverlayImage(R.drawable.gal5_5,  Pair(0.5f, 0.54f), Pair(500,370)),
            OverlayImage(R.drawable.gal5_6, Pair(1.4f, 0.54f), Pair(500,370)),
        ),
        // 페이지 6 (gal506): 세 번째 제목
        listOf(
            OverlayImage(R.drawable.gal5_7, Pair(-0.29f, 0.49f), Pair(610, 450)),
        ),
        // 페이지 7 (gal507): 네 번째 제목 (첫 번째 화면) - 비디오
        listOf(
            OverlayImage(
                resourceId = R.raw.video,  // raw 폴더에 있는 video.mp4 파일 참조
                position = Pair(0f, 0.5f),  // 화면 중앙에 위치
                size = Pair(900, 500),  // 적절한 크기로 조정
                isVideo = true  // 비디오 플래그 설정 (중요!)
            )
        ),
        // 페이지 8 (gal508): 네 번째 제목 (두 번째 화면) - 일반 이미지
        listOf(
            OverlayImage(R.drawable.gal5_8, Pair(-0.16f, 0.21f), Pair(365, 260)),
            OverlayImage(R.drawable.gal5_9,Pair(0.5f, 0.21f), Pair(365, 260)),
            OverlayImage(R.drawable.gal5_10, Pair(1.152f, 0.21f), Pair(365, 260)),
            OverlayImage(R.drawable.gal5_11, Pair(-0.16f, 0.73f), Pair(365, 260), isVideo = false),
            OverlayImage(R.drawable.gal5_12, Pair(0.5f, 0.73f), Pair(365, 260)),
            OverlayImage(R.drawable.gal5_13,  Pair(1.152f, 0.73f), Pair(365, 260)),
        ),
        // 페이지 9 (gal509): 다섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal5_14, Pair(-0.558f, 0.43f), Pair(290,373)),
            OverlayImage(R.drawable.gal5_15, Pair(-0.065f, 0.43f), Pair(300,373)),
            OverlayImage(R.drawable.gal5_16, Pair(0.43f, 0.43f), Pair(290,373)),
        ),
        // 페이지 10 (gal510): 여섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal5_17, Pair(-0.65f, 0.32f), Pair(180, 250)),
            OverlayImage(R.drawable.gal5_18, Pair(-0.65f, 0.682f), Pair(180, 140)),
        ),
        // 페이지 11 (gal511): 일곱 번째 제목
        listOf(
            // 투명한 클릭 영역 (보이지 않음)
            OverlayImage(
                resourceId = R.drawable.gal5_19, // 클릭 시 팝업될 이미지
                position = Pair(0.5f, 0.5f),    // 클릭 영역 위치
                size = Pair(1600, 550),           // 클릭 영역 크기
                isVideo = false                  // 이미지 확대 모드 사용
            )
        )
    )

    // 이미지 확대 상태 관리
    var expandedImageId by remember { mutableStateOf<Int?>(null) }

    // 비디오 재생 상태 관리
    var playingVideoId by remember { mutableStateOf<Int?>(null) }

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
                .data(R.drawable.gal5background)
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
                        .padding(horizontal = 65.dp), // 양쪽 여백 추가
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

                                            .alpha(if (page == 10) 0f else 1f)
                                            .clickable {
                                                if (overlayImage.isVideo) {
                                                    playingVideoId = overlayImage.resourceId
                                                } else {
                                                    expandedImageId = overlayImage.resourceId
                                                }
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

        // 이미지 확대 다이얼로그 - 1000dp × 600dp 고정 크기
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

                    // 확대된 이미지 - Coil 사용, 1000dp × 600dp 고정 크기
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

        // 비디오 재생 다이얼로그
        playingVideoId?.let { videoId ->
            Dialog(
                onDismissRequest = { playingVideoId = null },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.9f))
                        .clickable { playingVideoId = null }, // 여기에 클릭 이벤트 추가
                    contentAlignment = Alignment.Center
                ) {
                    // 닫기 버튼
                    IconButton(
                        onClick = { playingVideoId = null },
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

                    // 비디오 플레이어
                    Box(
                        modifier = Modifier
                            .size(1000.dp, 600.dp)
                            .background(Color.Black)
                            .clickable(onClick = {}, enabled = false) // 비디오 영역은 클릭 이벤트 차단
                            .zIndex(5f), // 비디오를 배경보다 위에 표시
                        contentAlignment = Alignment.Center
                    ) {
                        AndroidView(
                            factory = { context ->
                                VideoView(context).apply {
                                    // 비디오 리소스 ID에 해당하는 URI 설정
                                    val videoUri = Uri.parse("android.resource://${context.packageName}/${videoId}")
                                    setVideoURI(videoUri)

                                    // MediaController 추가
                                    val mediaController = MediaController(context)
                                    mediaController.setAnchorView(this)
                                    setMediaController(mediaController)

                                    setOnPreparedListener { mp ->
                                        mp.start()
                                        mediaController.show()
                                    }
                                    setOnCompletionListener {
                                        it.start() // 비디오 루프 재생
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
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
fun Gallery5ScreenPreview() {
    GalleryFinallyTheme {
        Gallery5Screen()
    }
}