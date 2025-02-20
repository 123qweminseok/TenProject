package com.minseok.directoryapp

import android.content.Context
import org.apache.poi.ss.usermodel.WorkbookFactory

// 이미 존재하는 DirectoryEntry 데이터 클래스와
// 기존 getDirectoryData() 코드는 "절대 수정/삭제하지 않음"
object DirectoryRepository {

    // ---------------------------------------------------
    // [1] 기존 코드 (그대로 둠)
    // ---------------------------------------------------
    fun getDirectoryDataFromCsv(context: Context): List<DirectoryEntry> {
        // CSV 파일에서 "졸업"년도에 따른 이름들을 그룹화하기 위한 Map
        val yearToNamesMap = mutableMapOf<Int, MutableList<String>>()

        // assets 폴더의 CSV 파일을 읽기
        context.assets.open("nita.csv").bufferedReader().use { reader ->
            // 첫 줄(헤더) 건너뛰기
            reader.readLine()
            reader.lineSequence().forEach { line ->
                val tokens = line.split(",")
                if (tokens.size >= 3) {
                    val name = tokens[0].trim()
                    // 기수는 필요에 따라 사용하지만 여기서는 졸업년도만 중요
                    val gradYear = tokens[2].trim().toIntOrNull() ?: return@forEach

                    // 해당 졸업년도에 이름 추가
                    val namesList = yearToNamesMap.getOrPut(gradYear) { mutableListOf() }
                    namesList.add(name)
                }
            }
        }

        // 기존 로직에 따라 1..99 기수를 순회하며, 계산된 졸업년도에 해당하는 이름 목록을 매칭
        return (1..99).map { generation ->
            val year = when (generation) {
                1 -> 1925
                2 -> 1926
                3 -> 1927
                4 -> 1928
                5 -> 1929
                6 -> 1930
                else -> {
                    var computedYear = 1925 + (generation - 1)
                    if (computedYear >= 1942) computedYear++  // 1942년 건너뛰기
                    if (computedYear >= 1947) computedYear++  // 1947년 건너뛰기
                    computedYear
                }
            }
            val names = yearToNamesMap[year] ?: emptyList()
            DirectoryEntry(generation, year, names)
        }
    }

    // ---------------------------------------------------
    // [2] 새로 추가한 함수
    //     "엑셀의 졸업년도" 기준으로 이름들을 가져와서
    //     기존과 똑같이 1..99 기수를 순회하여
    //     졸업년도별 이름 목록을 매핑해 반환
    // ---------------------------------------------------
    fun getDirectoryDataFromExcel(context: Context): List<DirectoryEntry> {
        // 1) assets/nita.xlsx 열기
        val inputStream = context.assets.open("nita.xlsx")
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)  // 첫 번째 시트

        // 2) 엑셀에서 [이름(A열), 기수(B열), 졸업(C열)]를 뽑아오기
        //    (엑셀 구조에 맞춰 인덱스 변경 가능)
        val rowDataList = mutableListOf<Triple<String, Int, Int>>()
        // Triple(이름, 기수, 졸업년도)

        // 0행이 헤더라면, 실제 데이터는 1행부터
        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex) ?: continue

            val nameCell = row.getCell(0)  // A열 = 이름
            val genCell  = row.getCell(1)  // B열 = 기수
            val yearCell = row.getCell(2)  // C열 = 졸업년도

            if (nameCell == null || genCell == null || yearCell == null) continue

            val name = nameCell.stringCellValue.trim()
            val generation = genCell.numericCellValue.toInt()
            val gradYear = yearCell.numericCellValue.toInt()

            rowDataList.add(Triple(name, generation, gradYear))
        }

        workbook.close()
        inputStream.close()

        // 3) "졸업년도 -> 이름들" 로 묶어두기
        val yearToNamesMap: Map<Int, List<String>> = rowDataList
            .groupBy { it.third }   // third = 졸업년도
            .mapValues { entry ->
                entry.value.map { it.first }  // first = 이름
            }

        // 4) 기존 로직과 동일하게 1..99 기수를 순회하면서
        //    -> 해당 기수의 졸업년도 계산
        //    -> 위에서 구한 yearToNamesMap[졸업년도] 가져오기
        return (1..99).map { generation ->
            val year = when (generation) {
                1 -> 1925
                2 -> 1926
                3 -> 1927
                4 -> 1928
                5 -> 1929
                6 -> 1930
                else -> {
                    var computedYear = 1925 + (generation - 1)
                    if (computedYear >= 1942) computedYear++  // 1942년 건너뛰기
                    if (computedYear >= 1947) computedYear++  // 1947년 건너뛰기
                    computedYear
                }
            }

            // "졸업년도 -> 이름목록"에서 해당 year에 맞는 이름들을 가져옴
            val names = yearToNamesMap[year] ?: emptyList()

            DirectoryEntry(generation, year, names)
        }
    }
}
