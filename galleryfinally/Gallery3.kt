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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.minseok.galleryfinally.ui.theme.GalleryFinallyTheme

// OverlayImage 클래스 선언 대신 임포트 (별도 파일로 이동했으므로)
// import com.minseok.galleryfinally.OverlayImage

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
class Gallery3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryFinallyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Gallery3Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Add this annotation to suppress experimental API warnings
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Gallery3Screen(modifier: Modifier = Modifier) {
    // List of gallery images
    val galleryImages = listOf(
        R.drawable.gal301,
        R.drawable.gal302,
        R.drawable.gal303,
        R.drawable.gal304,
        R.drawable.gal305,
        R.drawable.gal306,
        R.drawable.gal307
    )

    // Titles for each gallery screen - 5개의 제목
    val screenTitles = listOf(
        "마산상업고등학교",
        "6·25전쟁과 피란학교",
        "학도병",
        "1960년 3·15의거와 용마인",
        "3·15의거와 명예 졸업생 김주열"
    )

    // 화면과 제목 매핑
    // 세 번째 제목(학도병)에 두 개의 스크린이 매핑되고, 마지막 제목에 두 개의 스크린이 매핑됨
    // 수정: 네 번째 제목(1960년 3·15의거와 용마인)에 두 개의 스크린이 매핑되도록 변경
    val screenToTitleIndex = listOf(0, 1, 2, 2, 3, 3, 4)

    // 각 페이지별 오버레이 이미지 설정 (gal3_1부터 gal3_23까지)
    val pageOverlayImages = listOf(
        // 페이지 1: 마산상업고등학교
        listOf(
            OverlayImage(R.drawable.gal3_1, Pair(-0.55f, 0.575f), Pair(310, 530)),
        ),
        // 페이지 2: 6·25전쟁과 피란학교
        listOf(
            OverlayImage(R.drawable.gal3_2, Pair(-0.7f, 0.8f), Pair(140, 250)),
            OverlayImage(R.drawable.gal3_3, Pair(-0.14f, 0.8f), Pair(150, 250)),
            OverlayImage(R.drawable.gal3_4, Pair(0.4f, 0.8f), Pair(170, 250)),
            OverlayImage(R.drawable.gal3_5, Pair(0.95f, 0.8f), Pair(170, 250)),
            OverlayImage(R.drawable.gal3_6, Pair(1.5f, 0.8f), Pair(150, 250)),
        ),
        // 페이지 3: 학도병 (첫 번째 화면)
        listOf(
            OverlayImage(R.drawable.gal3_7, Pair(-0.57f, 0.75f), Pair(280, 200)),
            OverlayImage(R.drawable.gal3_8,Pair( -0.04f, 0.75f), Pair(260, 200)),
            OverlayImage(R.drawable.gal3_9, Pair(0.48f, 0.75f), Pair(260, 200)),
            OverlayImage(R.drawable.gal3_10, Pair(0.99f, 0.75f), Pair(260, 200)),
            OverlayImage(R.drawable.gal3_11, Pair(1.6f, 0.65f), Pair(260, 60)),
            OverlayImage(R.drawable.gal3_12, Pair(1.6f, 0.75f), Pair(260, 60)),
            OverlayImage(R.drawable.gal3_13, Pair(1.6f, 0.85f), Pair(260, 60))
        ),
        // 페이지 4: 학도병 (두 번째 화면)
        listOf(
            OverlayImage(R.drawable.gal3_14, Pair(-0.55f, 0.34f), Pair(300, 410)),
            OverlayImage(R.drawable.gal3_15, Pair(0.27f, 0.35f), Pair(580, 400)),
            OverlayImage(R.drawable.gal3_16, Pair(1.33f, 0.35f), Pair(550, 400)),
            OverlayImage(R.drawable.gal3_17, Pair(-0.48f, 0.88f), Pair(390, 80))
        ),
        // 페이지 5: 1960년 3·15의거와 용마인 (첫 번째 화면)
        listOf(
        ),
        // 페이지 6: 1960년 3·15의거와 용마인 (두 번째 화면)
        listOf(
            OverlayImage(R.drawable.gal3_18, Pair(-0.07f, 0.5f), Pair(875, 510)),
            OverlayImage(R.drawable.gal3_19, Pair(1.258f, 0.5f), Pair(680, 510)),
        ),
        // 페이지 7: 3·15의거와 명예 졸업생 김주열
        listOf(
            OverlayImage(R.drawable.gal3_20, Pair(-0.52f, 0.55f), Pair(350, 498)),
            OverlayImage(R.drawable.gal3_21, Pair(0.139f, 0.725f), Pair(420, 270)),
            OverlayImage(R.drawable.gal3_22, Pair(0.839f, 0.725f), Pair(355, 275)),
            OverlayImage(R.drawable.gal3_23, Pair(1.48f, 0.725f), Pair(380, 275))
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
    val currentTitleIndex = if (currentPage < screenToTitleIndex.size) screenToTitleIndex[currentPage] else 4

    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil
        AsyncImage(
            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                .data(R.drawable.gal3background)
                .crossfade(true)
                .build(),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top navigation bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 140.dp, bottom = 1.dp)
            ) {
                // 버튼들을 담을 Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // 균등 간격
                ) {
                    // Navigation buttons (5개의 제목)
                    for (index in screenTitles.indices) {
                        // 전체 버튼 영역 (넓은 클릭 영역)
                        Box(
                            modifier = Modifier
                                .width(280.dp) // 클릭 가능한 넓은 영역
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
                            // 직접 만든 버튼 배경
                            Box(
                                modifier = Modifier
                                    .width(350.dp)
                                    .height(65.dp)
                                    .clip(RoundedCornerShape(30.dp)) // 둥근 모서리 적용
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
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
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
                            model = ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(galleryImages[page])
                                .crossfade(true)
                                .build(),
                            contentDescription = "Gallery image ${page + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )

                        // 클릭 영역 설정 - 페이지별로 클릭 영역 추가
                        if (page == 0) { // 첫 번째 페이지: 마산상업고등학교
                            Box(
                                modifier = Modifier
                                    .size(width = 310.dp, height = 530.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-330).dp, y = (45).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_1
                                    }
                            )
                        } else if (page == 1) { // 두 번째 페이지: 6·25전쟁과 피란학교
                            Box(
                                modifier = Modifier
                                    .size(width = 140.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-420).dp, y = (180).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_2
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 150.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-84).dp, y = (180).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_3
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 170.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (240).dp, y = (180).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_4
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 170.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (570).dp, y = (180).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_5
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 150.dp, height = 250.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (900).dp, y = (180).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_6
                                    }
                            )
                        } else if (page == 2) { // 세 번째 페이지: 학도병 (첫 번째 화면)
                            Box(
                                modifier = Modifier
                                    .size(width = 280.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-342).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_7
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-24).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_8
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (288).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_9
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 200.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (594).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_10
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 60.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (960).dp, y = (90).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_11
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 60.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (960).dp, y = (150).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_12
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 260.dp, height = 60.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (960).dp, y = (210).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_13
                                    }
                            )
                        } else if (page == 3) { // 네 번째 페이지: 학도병 (두 번째 화면)
                            Box(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 410.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-330).dp, y = (-96).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_14
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 580.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (162).dp, y = (-90).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_15
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 550.dp, height = 400.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (798).dp, y = (-90).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_16
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 390.dp, height = 80.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-288).dp, y = (228).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_17
                                    }
                            )
                        } else if (page == 5) { // 여섯 번째 페이지: 1960년 3·15의거와 용마인 (두 번째 화면)
                            Box(
                                modifier = Modifier
                                    .size(width = 875.dp, height = 510.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-42).dp, y = (0).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_18
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 680.dp, height = 510.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (754.8).dp, y = (0).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_19
                                    }
                            )
                        } else if (page == 6) { // 일곱 번째 페이지: 3·15의거와 명예 졸업생 김주열
                            Box(
                                modifier = Modifier
                                    .size(width = 350.dp, height = 498.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (-312).dp, y = (30).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_20
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 420.dp, height = 270.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (83.4).dp, y = (135).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_21
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 355.dp, height = 275.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (503.4).dp, y = (135).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_22
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .size(width = 380.dp, height = 275.dp)
                                    .align(Alignment.Center)
                                    .offset(x = (888).dp, y = (135).dp)
                                    .clickable {
                                        expandedImageId = R.drawable.gal3_23
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
}