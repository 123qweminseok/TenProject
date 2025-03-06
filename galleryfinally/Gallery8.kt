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
import androidx.compose.ui.draw.alpha
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
class Gallery8 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery8Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery8Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal801,
        R.drawable.gal802,
        R.drawable.gal803,
        R.drawable.gal804,
        R.drawable.gal805,
        R.drawable.gal806,
        R.drawable.gal807,
        R.drawable.gal808,
        R.drawable.gal809,
        R.drawable.gal810,
        R.drawable.gal811,
        R.drawable.gal812,
        R.drawable.gal813,
        R.drawable.gal814,
        R.drawable.gal815,
        R.drawable.gal816,
        R.drawable.gal817,
        R.drawable.gal818,
        R.drawable.gal819,
        R.drawable.gal820,
        R.drawable.gal821
    )

    // Titles for each gallery screen - 4개의 제목
    val screenTitles = listOf(
        "마산상업고등학교와\n마산용마고등학교 휘장의 변천사",
        "역대 동창회장의 약력과 공적",
        "재단법인 용마동문장학회",
        "재단법인 우파장학회"
    )

    // 화면과 제목 매핑
    // - 첫 번째 제목에 1개의 스크린 (0)
    // - 두 번째 제목에 15개의 스크린 (1-15)
    // - 세 번째 제목에 2개의 스크린 (16-17)
    // - 네 번째 제목에 3개의 스크린 (18-20)
    val screenToTitleIndex = listOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3)

    // 각 페이지별 오버레이 이미지 설정 (gal8_1부터 gal8_22까지)
    val pageOverlayImages = listOf(
        // 페이지 1 (gal801): 첫 번째 제목 - gal8_1 이미지
        listOf(
            OverlayImage(R.drawable.gal8_1, Pair(-0.4f, 0.45f), Pair(450, 400))
        ),
        // 페이지 2 (gal802): 두 번째 제목 - gal8_2 이미지
        listOf(
            OverlayImage(R.drawable.gal8_2, Pair(-0.6f, 0.35f), Pair(300, 300))
        ),
        // 페이지 3 (gal803): 두 번째 제목 - gal8_3 이미지
        listOf(
            OverlayImage(R.drawable.gal8_3, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 4 (gal804): 두 번째 제목 - gal8_4 이미지
        listOf(
            OverlayImage(R.drawable.gal8_4, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 5 (gal805): 두 번째 제목 - gal8_5 이미지
        listOf(
            OverlayImage(R.drawable.gal8_5, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 6 (gal806): 두 번째 제목 - gal8_6 이미지
        listOf(
            OverlayImage(R.drawable.gal8_6, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 7 (gal807): 두 번째 제목 - gal8_7 이미지
        listOf(
            OverlayImage(R.drawable.gal8_7, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 8 (gal808): 두 번째 제목 - gal8_8 이미지
        listOf(
            OverlayImage(R.drawable.gal8_8, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 9 (gal809): 두 번째 제목 - gal8_9 이미지
        listOf(
            OverlayImage(R.drawable.gal8_9, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 10 (gal810): 두 번째 제목 - gal8_10 이미지
        listOf(
            OverlayImage(R.drawable.gal8_10, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 11 (gal811): 두 번째 제목 - gal8_11 이미지
        listOf(
            OverlayImage(R.drawable.gal8_11, Pair(-0.6f, 0.25f), Pair(280, 300))
        ),
        // 페이지 12 (gal812): 두 번째 제목 - gal8_12 이미지
        listOf(
            OverlayImage(R.drawable.gal8_12, Pair(-0.6f, 0.25f), Pair(280, 320))
        ),
        // 페이지 13 (gal813): 두 번째 제목 - gal8_13 이미지
        listOf(
            OverlayImage(R.drawable.gal8_13,Pair(-0.6f, 0.25f), Pair(280, 320))
        ),
        // 페이지 14 (gal814): 두 번째 제목 - gal8_14 이미지
        listOf(
            OverlayImage(R.drawable.gal8_14, Pair(-0.6f, 0.25f), Pair(280, 320))
        ),
        // 페이지 15 (gal815): 두 번째 제목 - gal8_15 이미지
        listOf(
            OverlayImage(R.drawable.gal8_15,Pair(-0.6f, 0.25f), Pair(280, 320))
        ),
        // 페이지 16 (gal816): 두 번째 제목 - gal8_16 이미지
        listOf(
            OverlayImage(R.drawable.gal8_16, Pair(-0.6f, 0.25f), Pair(280, 320))
        ),
        // 페이지 17 (gal817): 세 번째 제목 - gal8_17 이미지
        listOf(
            OverlayImage(R.drawable.gal8_17,Pair(-0.3f, 0.5f), Pair(550, 500))
        ),
        // 페이지 18 (gal818): 세 번째 제목 - 이미지 없음
        listOf<OverlayImage>(),
        // 페이지 19 (gal819): 네 번째 제목 - gal8_19 이미지
        listOf(
            OverlayImage(R.drawable.gal8_18, Pair(-0.5f, 0.53f), Pair(430, 520))
        ),
        // 페이지 20 (gal820): 네 번째 제목 - gal8_20 이미지
        listOf(
            OverlayImage(R.drawable.gal8_19, Pair(-0.3f, 0.3f), Pair(600, 450))
        ),
        // 페이지 21 (gal821): 네 번째 제목 - gal8_21 이미지
        listOf(
            OverlayImage(R.drawable.gal8_20, Pair(-0.55f, 0.3f), Pair(300,430))
        )
    )

    // 이미지 확대 상태 관리
    var expandedImageId by remember { mutableStateOf<Int?>(null) }

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
                .data(R.drawable.gal8background)
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
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Navigation buttons (4개의 제목)
                    for (index in screenTitles.indices) {
                        // 전체 버튼 영역 (넓은 클릭 영역)
                        Box(
                            modifier = Modifier
                                .width(380.dp) // 기존 크기로 설정
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
                                    .width(380.dp) // 기존 크기로 설정
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(30.dp))
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

                        // 클릭 영역 설정 - 각 페이지에 맞는 이미지 위치에 클릭 영역 설정
                        if (page < pageOverlayImages.size && pageOverlayImages[page].isNotEmpty()) {
                            pageOverlayImages[page].forEachIndexed { index, overlayImage ->
                                Box(
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
                                        }
                                )
                            }
                        }

                        // 오버레이 이미지들 - 숨김 처리 (투명도 0)
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

                    // 확대된 이미지를 위한 Box
                    Box(
                        modifier = Modifier
                            .size(
                                width = 700.dp,
                                height = 700.dp
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery8ScreenPreview() {
    GalleryFinallyTheme {
        Gallery8Screen()
    }
}