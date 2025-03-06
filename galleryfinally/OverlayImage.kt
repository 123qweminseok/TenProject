package com.minseok.galleryfinally

// 이미지 오버레이를 위한 데이터 클래스
data class OverlayImage(
    val resourceId: Int,
    val position: Pair<Float, Float>, // x, y 위치 (퍼센트 단위, 0.0f ~ 1.0f)
    val size: Pair<Int, Int>, // width, height (dp 단위)
    val isVideo: Boolean = false // 비디오 여부 (기본값은 false)
)