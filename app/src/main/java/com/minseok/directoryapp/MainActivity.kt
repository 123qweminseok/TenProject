@file:OptIn(ExperimentalMaterial3Api::class)

package com.minseok.directoryapp
import android.view.WindowManager
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.ui.platform.LocalDensity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minseok.directoryapp.ui.theme.DirectoryAppTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// DirectoryEntry.kt
data class DirectoryEntry(
    val generation: Int,
    val year: Int,
    val names: List<String>,
    var isExpanded: Boolean = false
)

// MainActivity.kt 추가 수정
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 키보드 설정 및 전체 화면 모드 설정
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        // 레이아웃이 전체 화면을 채우도록 강제 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        enableEdgeToEdge()

        // Repository 통해 데이터 가져오기
        val directoryData = DirectoryRepository.getDirectoryDataFromCsv(this)

        setContent {
            DirectoryAppTheme {
                // 시스템 UI 색상 설정 (투명하게)

                OptimizedDirectoryScreen(directoryData)
            }
        }
    }
}

@Composable
fun OptimizedDirectoryScreen(entries: List<DirectoryEntry>) {
    // 초기 데이터를 remember로 캐싱
    val initialEntries = remember { entries }

    // 실제로 표시할 DirectoryEntry 리스트
    var directoryEntries by remember { mutableStateOf(initialEntries) }

    // 검색어 상태 (디바운싱 적용)
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    // 검색 결과
    var searchResults by remember { mutableStateOf(entries) }

    // 디버깅 로그를 위한 상태
    val lastClickedGeneration = remember { mutableStateOf<Int?>(null) }

    // LazyColumn 상태 저장
    val lazyListState = rememberLazyListState()

    // 99회 항목이 있는지 체크
    val has99Item = remember(searchResults) {
        searchResults.any { it.generation == 99 }
    }

    // 99회 항목으로 스크롤하는 함수
    val scrollTo99 = {
        if (has99Item) {
            val index = searchResults.indexOfFirst { it.generation == 99 }
            if (index >= 0) {
                android.util.Log.d("DirectoryApp", "99회 항목으로 스크롤 시도: 인덱스 $index")
            }
        }
    }

    // 검색 인덱스 생성 (성능 향상)
    val nameToEntryMap = remember(initialEntries) {
        buildNameToEntryMap(initialEntries)
    }

    // 검색 로직
    LaunchedEffect(searchText) {
        isSearching = true
        delay(300) // 300ms 디바운스

        // 백그라운드 스레드에서 검색 수행
        val results = withContext(Dispatchers.Default) {
            if (searchText.isEmpty()) {
                // 기존 확장 상태 유지
                directoryEntries
            } else {
                // 인덱스를 활용한 빠른 검색
                val matchingEntries = mutableSetOf<DirectoryEntry>()
                val lowercaseQuery = searchText.lowercase()

                nameToEntryMap.forEach { (name, entries) ->
                    if (name.contains(lowercaseQuery)) {
                        matchingEntries.addAll(entries)
                    }
                }

                // 원래 순서로 정렬 및 확장 상태 적용
                initialEntries
                    .filter { matchingEntries.contains(it) }
                    .map { it.copy(isExpanded = true) }
            }
        }

        searchResults = results
        isSearching = false
    }

    // 배경 설정을 위한 BoxWithConstraints 사용
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenHeight = constraints.maxHeight.toFloat()
        val screenWidth = constraints.maxWidth.toFloat()

        // 하단에 여백을 주기 위한 계산
        val bottomPadding = remember { 80.dp }

        // 배경 이미지를 절대 크기로 설정 (화면 크기에 맞춤)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // 배경 이미지는 항상 전체 화면 크기로 고정
            Image(
                painter = painterResource(id = R.drawable.title3),
                contentDescription = "배경 이미지",
                modifier = Modifier
                    .fillMaxSize()
                    .requiredHeight(with(LocalDensity.current) { screenHeight.toDp() })
                    .requiredWidth(with(LocalDensity.current) { screenWidth.toDp() }),
                contentScale = ContentScale.FillBounds
            )

            // 검색창을 상단에 별도로 배치
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .offset(x = 650.dp),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newValue -> searchText = newValue },
                    placeholder = {
                        Text(
                            "검색어를 입력하세요",
                            color = Color.LightGray,
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    trailingIcon = {
                        if (isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.Cyan,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.search3),
                                contentDescription = "검색",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Cyan
                            )
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White
                    ),
                    modifier = Modifier
                        .width(300.dp)
                )
            }

            // 실제 콘텐츠 - 전체적으로 225dp 아래로 이동
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 225.dp)
            ) {
                // 콘텐츠 영역
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 16.dp,
                                top = 0.dp,
                                end = 16.dp,
                                bottom = 0.dp
                            ),
                        state = lazyListState,
                        contentPadding = PaddingValues(bottom = bottomPadding)
                    ) {
                        // 상단 헤더 (고정)
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .offset(x = 50.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) { /* 내용 유지 */ }

                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) { /* 내용 유지 */ }

                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                    contentAlignment = Alignment.CenterEnd
                                ) { /* 내용 유지 */ }
                            }
                        }

                        // 디렉토리 항목들
                        searchResults.forEachIndexed { index, entry ->
                            item(key = entry.generation) {
                                // 99회 항목에 대한 특별 처리
                                if (entry.generation == 99) {
                                    // 디버그용 로그 추가
                                    android.util.Log.d("DirectoryApp", "99회 항목 렌더링 (인덱스: $index)")

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(IntrinsicSize.Min)
//                                            .background(Color(0xFF222222)) // 더 명확한 배경색
                                            .padding(vertical = 4.dp) // 패딩 추가
                                            .clickable {
                                                // 클릭 시 로그 및 상태 업데이트
                                                lastClickedGeneration.value = 99
                                                android.util.Log.d("DirectoryApp", "99회 항목 클릭됨")

                                                // 상태 업데이트
                                                directoryEntries = directoryEntries.map {
                                                    if (it.generation == 99) {
                                                        it.copy(isExpanded = !it.isExpanded)
                                                    } else {
                                                        it
                                                    }
                                                }

                                                searchResults = searchResults.map {
                                                    if (it.generation == 99) {
                                                        it.copy(isExpanded = !it.isExpanded)
                                                    } else {
                                                        it
                                                    }
                                                }
                                            }
                                    ) {
                                        // 기존 DirectoryEntryItem과 동일한 내용
                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        imageVector = if (entry.isExpanded)
                                                            Icons.Default.KeyboardArrowDown
                                                        else
                                                            Icons.Default.KeyboardArrowRight,
                                                        contentDescription = if (entry.isExpanded) "접기" else "펼치기",
                                                        tint = Color.White,
                                                        modifier = Modifier.size(24.dp)
                                                    )

                                                    Text(
                                                        text = "${entry.generation}회",
                                                        color = Color(0xFF00BFFF),
                                                        fontSize = 16.sp,
                                                        modifier = Modifier.padding(start = 8.dp),
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }

                                                Text(
                                                    text = entry.year.toString(),
                                                    color = Color(0xFF00BFFF),
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }

                                            // 클릭 이벤트 디버깅
                                            // 내용 표시 (직접 if로 제어)
                                            if (entry.isExpanded && entry.names.isNotEmpty()) {
                                                OptimizedNamesList(names = entry.names)
                                            }

                                            Divider(
                                                color = Color.DarkGray,
                                                modifier = Modifier.padding(horizontal = 50.dp)
                                            )
                                        }
                                    }
                                } else {
                                    // 다른 항목들은 기존 컴포넌트 사용
                                    DirectoryEntryItem(
                                        entry = entry,
                                        onExpand = { generation ->
                                            lastClickedGeneration.value = generation
                                            android.util.Log.d("DirectoryApp", "$generation 회 항목 클릭됨")

                                            directoryEntries = directoryEntries.map {
                                                if (it.generation == generation) {
                                                    it.copy(isExpanded = !it.isExpanded)
                                                } else {
                                                    it
                                                }
                                            }

                                            searchResults = searchResults.map {
                                                if (it.generation == generation) {
                                                    it.copy(isExpanded = !it.isExpanded)
                                                } else {
                                                    it
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        // 추가 여백 항목
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }

                // 하단에 99회 바로가기 버튼 추가
                if (has99Item) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF333333))
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                // 99회 항목으로 스크롤
                                scrollTo99()

                                // 직접 99회 항목 확장
                                directoryEntries = directoryEntries.map {
                                    if (it.generation == 99) {
                                        it.copy(isExpanded = true)
                                    } else {
                                        it
                                    }
                                }

                                searchResults = searchResults.map {
                                    if (it.generation == 99) {
                                        it.copy(isExpanded = true)
                                    } else {
                                        it
                                    }
                                }

                                android.util.Log.d("DirectoryApp", "99회 바로가기 버튼 클릭됨")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "99회 바로가기",
                            color = Color.Cyan,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


// 효율적인 검색을 위한 인덱스 생성 함수
private fun buildNameToEntryMap(entries: List<DirectoryEntry>): Map<String, List<DirectoryEntry>> {
    val map = mutableMapOf<String, MutableList<DirectoryEntry>>()

    entries.forEach { entry ->
        entry.names.forEach { name ->
            val normalizedName = name.lowercase()
            if (!map.containsKey(normalizedName)) {
                map[normalizedName] = mutableListOf()
            }
            map[normalizedName]?.add(entry)
        }
    }

    return map
}

@Composable
fun DirectoryEntryItem(
    entry: DirectoryEntry,
    onExpand: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpand(entry.generation) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (entry.isExpanded)
                        Icons.Default.KeyboardArrowDown
                    else
                        Icons.Default.KeyboardArrowRight,
                    contentDescription = if (entry.isExpanded) "접기" else "펼치기",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "${entry.generation}회",
                    color = Color(0xFF00BFFF),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold  // 굵게 설정
                )
            }

            Text(
                text = entry.year.toString(),
                color = Color(0xFF00BFFF),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold  // 굵게 설정
            )
        }

        // AnimatedVisibility로 전환하여 성능 향상
        AnimatedVisibility(visible = entry.isExpanded && entry.names.isNotEmpty()) {
            // 이름 리스트 렌더링 최적화
            OptimizedNamesList(names = entry.names)
        }

        Divider(
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 50.dp)
        )
    }
}

@Composable
fun OptimizedNamesList(names: List<String>) {
    // 모든 행에 대해 동일한 개수로 그룹화 - 한 행에 20명씩
    val chunkedNames = remember(names) { names.chunked(20) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        chunkedNames.forEach { nameChunk ->
            // 각 행에 대해 일관된 그리드 레이아웃 적용
            NameRow(
                names = nameChunk,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun NameRow(
    names: List<String>,
    modifier: Modifier = Modifier
) {
    // FlowRow 대신 고정된 그리드 레이아웃 사용
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 최대 20개 이름을 위한 공간 확보
        repeat(20) { index ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (index < names.size && names[index].isNotEmpty()) {
                    Text(
                        text = names[index],
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DirectoryScreenPreview() {
    val sampleData = listOf(
        DirectoryEntry(1, 1925, List(45) { "이름${it+1}" }, false),
        DirectoryEntry(2, 1926, emptyList(), false)
    )
    DirectoryAppTheme {
        OptimizedDirectoryScreen(sampleData)
    }
}

val NanumBrush = FontFamily(
    Font(R.font.nita2) // 파일명에 맞게 수정
)