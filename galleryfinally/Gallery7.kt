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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.minseok.galleryfinally.ui.theme.GalleryFinallyTheme
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
class Gallery7 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery7Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery7Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal701,
        R.drawable.gal702,
        R.drawable.gal703,
        R.drawable.gal704,
        R.drawable.gal705,
        R.drawable.gal706,
        R.drawable.gal707,
        R.drawable.gal708,
        R.drawable.gal709,
        R.drawable.gal710,
        R.drawable.gal711,
        R.drawable.gal712,
        R.drawable.gal713,
        R.drawable.gal714,
        R.drawable.gal715,
        R.drawable.gal716,
        R.drawable.gal717,
        R.drawable.gal718,
        R.drawable.gal719
    )

    // Titles for each gallery screen - 5개의 제목
    val screenTitles = listOf(
        "3·15 민주화 운동의 시작",
        "마산에서의 첫 불꽃",
        "전국으로 번진 분노",
        "민주주의를 향한 도약",
        "3·15 의거와 학생운동"
    )

    // 화면과 제목 매핑
    // - 첫 번째 제목에 2개의 스크린 (0-1)
    // - 두 번째 제목에 1개의 스크린 (2)
    // - 세 번째 제목에 1개의 스크린 (3)
    // - 네 번째 제목에 1개의 스크린 (4)
    // - 다섯 번째 제목에 나머지 스크린 (5-18)
    val screenToTitleIndex = listOf(0, 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)

    // 각 페이지별 오버레이 이미지 설정 (gal7_1부터 gal7_9까지)
    val pageOverlayImages = listOf(
        // 페이지 1 (gal701): 첫 번째 제목
        listOf(
            OverlayImage(R.raw.video1, Pair(0.5f, 0.5f), Pair(500, 300), isVideo = true)
        ),
        // 페이지 2 (gal702): 첫 번째 제목
        listOf<OverlayImage>(),
        // 페이지 3 (gal703): 두 번째 제목
        listOf<OverlayImage>(),
        // 페이지 4 (gal704): 세 번째 제목
        listOf<OverlayImage>(),
        // 페이지 5 (gal705): 네 번째 제목
        listOf<OverlayImage>(),
        // 페이지 6 (gal706): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 7 (gal707): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 8 (gal708): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 9 (gal709): 다섯 번째 제목
        listOf(
            OverlayImage(R.raw.video2, Pair(0.5f, 0.5f), Pair(500, 300), isVideo = true)
        ),
        // 페이지 10 (gal710): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 11 (gal711): 다섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal7_3, Pair(0.3f, 0.5f), Pair(400, 250)),
            OverlayImage(R.drawable.gal7_4, Pair(0.7f, 0.5f), Pair(400, 250))
        ),
        // 페이지 12 (gal712): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 13 (gal713): 다섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal7_5, Pair(0.5f, 0.5f), Pair(450, 350))
        ),
        // 페이지 14 (gal714): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 15 (gal715): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 16 (gal716): 다섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal7_6, Pair(0.3f, 0.3f), Pair(300, 200)),
            OverlayImage(R.drawable.gal7_7, Pair(0.7f, 0.3f), Pair(300, 200)),
            OverlayImage(R.drawable.gal7_8, Pair(0.5f, 0.7f), Pair(300, 200))
        ),
        // 페이지 17 (gal717): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 18 (gal718): 다섯 번째 제목
        listOf<OverlayImage>(),
        // 페이지 19 (gal719): 다섯 번째 제목
        listOf(
            OverlayImage(R.drawable.gal7_11, Pair(0.5f, 0.5f), Pair(500, 350))
        )
    )

    // 이미지 확대 상태 관리
    var expandedImageId by remember { mutableStateOf<Int?>(null) }

    // 비디오 재생 상태 관리
    var playingVideoId by remember { mutableStateOf<Int?>(null) }

    // pagerState와 scope를 최상위 레벨로 이동
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
                .data(R.drawable.gal7background)
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

                        // 클릭 영역 설정 - 특별한 페이지들에 대해 색상으로 표시된 영역
                        if (page == 0) { // 첫 번째 페이지 (gal701) - gal7_1 영상
                            Box(
                                modifier = Modifier
                                    .size(width = 700.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        playingVideoId = R.raw.video1
                                    }
                            )
                        } else if (page == 8) { // 9번째 페이지 (gal709) - gal7_2 영상
                            Box(
                                modifier = Modifier
                                    .size(width = 700.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        playingVideoId = R.raw.video2
                                    }
                            )
                        } else if (page == 10) { // 11번째 페이지 (gal711) - gal7_3, gal7_4
                            Box(
                                modifier = Modifier
                                    .size(width = 720.dp, height = 500.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-430).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_3
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 720.dp, height = 500.dp)
                                    .align(Alignment.Center)
                                    .offset(x = 370.dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_4
                                    }
                            )
                        } else if (page == 12) { // 13번째 페이지 (gal713) - gal7_5
                            Box(
                                modifier = Modifier
                                    .size(width = 1150.dp, height = 500.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_5
                                    }
                            )
                        } else if (page == 15) { // 16번째 페이지 (gal716) - gal7_6, gal7_7, gal7_8
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 300.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-620).dp, y = (160).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_6
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 470.dp, height = 300.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-200).dp, y = (160).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_7
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 230.dp, height = 300.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (140).dp, y = (160).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_8
                                    }
                            )
                        } else if (page == 17) { // 19번째 페이지 (gal719) - gal7_9
                            Box(
                                modifier = Modifier
                                    .size(width = 530.dp, height = 580.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-500).dp, y = (-30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_10
                                    }
                            )
                        } else if (page == 18) { // 19번째 페이지 (gal719) - gal7_9
                            Box(
                                modifier = Modifier
                                    .size(width = 450.dp, height = 650.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-550).dp, y = (-30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal7_11
                                    }
                            )
                        }

                        // 오버레이 이미지들은 완전 투명하게 처리
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
                                            .alpha(0f) // 완전 투명하게 설정
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

                    // 확대된 이미지를 위한 Box
                    Box(
                        modifier = Modifier
                            .size(
                                width = 1200.dp,
                                height = 900.dp
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviewGallery7Screen() {
    GalleryFinallyTheme {
        Gallery7Screen()
    }
}