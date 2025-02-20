@file:OptIn(ExperimentalMaterial3Api::class)

package com.minseok.directoryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minseok.directoryapp.ui.theme.DirectoryAppTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

// DirectoryEntry.kt
data class DirectoryEntry(
    val generation: Int,
    val year: Int,
    val names: List<String>,
    var isExpanded: Boolean = false
)

// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Repository 통해 데이터 가져오기
        val directoryData = DirectoryRepository.getDirectoryDataFromCsv(this)

        setContent {
            DirectoryAppTheme {
                DirectoryScreen(directoryData)
            }
        }
    }
}

// 메인 화면 컴포저블 함수
@Composable
fun DirectoryScreen(entries: List<DirectoryEntry>) {
    // 실제로 표시할 DirectoryEntry 리스트
    var directoryEntries by remember { mutableStateOf(entries) }
    // 검색어 상태
    var searchQuery by remember { mutableStateOf("") }
    // 검색창 표시 여부
    var isSearchVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var isSearchExpanded by remember { mutableStateOf(false) }

    // 검색어가 바뀔 때마다, 해당 이름이 포함된 항목만 자동으로 펼쳐지도록 업데이트
    val displayedEntries = if (searchQuery.isEmpty()) {
        // 검색어가 없으면, 사용자가 클릭한 상태(directoryEntries)를 그대로 사용
        directoryEntries
    } else {
        // 검색어가 있을 경우, 해당 항목은 자동으로 펼치고 나머지는 접힌 상태로 필터링
        val updated = directoryEntries.map { entry ->
            if (entry.names.any { it.contains(searchQuery, ignoreCase = true) }) {
                entry.copy(isExpanded = true)
            } else {
                entry.copy(isExpanded = false)
            }
        }
        updated.filter { it.isExpanded }
    }
    // Column 전체에 좌우 여백을 살짝 준다 (horizontal = 16.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    top = 0.dp,
                    end = 16.dp,
                    bottom = 120.dp
                )
                .verticalScroll(scrollState)
        ) {
            // 상단 Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 왼쪽 영역: 텍스트 (왼쪽 정렬)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .offset(x = 50.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "1925-2025년",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontFamily = NanumBrush  // 손글씨 폰트 적용!
                    )
                }

                // 중앙 영역: 이미지 (가운데 정렬)
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.title3),
                        contentDescription = "용마인",
                        modifier = Modifier.size(300.dp)
                    )
                }

// 오른쪽 영역: 검색바 (항상 확장된 상태로 표시)
// 오른쪽 영역: 검색바 (항상 확장된 상태로 표시)
// 기존 weight(1f)는 유지하되, 내부에 Box로 감싸서 오른쪽 정렬
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { newValue -> searchQuery = newValue },
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
                            Icon(
                                painter = painterResource(id = R.drawable.search3),
                                contentDescription = "검색",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Cyan
                            )
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
                        // 원하는 너비를 지정하고, Box의 오른쪽 정렬 효과를 그대로 사용
                        modifier = Modifier.width(300.dp)            .offset(x = (-20).dp) // 왼쪽으로 10.dp 이동

                    )
                }
}// filteredEntries (displayedEntries) 표시
            displayedEntries.forEach { entry ->
                DirectoryEntryItem(
                    entry = entry,
                    onExpand = { generation ->
                        // 클릭 시 접힘/펼침 토글 (직접 제어)
                        directoryEntries = directoryEntries.map {
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
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(
                text = entry.year.toString(),
                color = Color(0xFF00BFFF),
                fontSize = 16.sp
            )
        }

        if (entry.isExpanded && entry.names.isNotEmpty()) {
            Text(
                text = entry.names.joinToString(" "),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Divider(
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 50.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DirectoryScreenPreview() {
    val sampleData = listOf(
        DirectoryEntry(1, 1925, listOf("김종명", "김상우", "고재열"), false),
        DirectoryEntry(2, 1926, emptyList(), false)
    )
    DirectoryAppTheme {
        DirectoryScreen(sampleData)
    }
}


val NanumBrush = FontFamily(
    Font(R.font.nita) // 파일명에 맞게 수정
)
