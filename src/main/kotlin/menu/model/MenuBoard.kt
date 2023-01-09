package menu.model

import menu.values.CATEGORY_INDEX_END
import menu.values.CATEGORY_INDEX_START

class MenuBoard {
    private val allMenus: List<List<String>> = listOf(
        listOf(),
        listOf("규동", "우동", "미소시루", "스시", "가츠동", "오니기리", "하이라이스", "라멘", "오코노미야끼"),       // 일식, 1
        listOf("김밥", "김치찌개", "쌈밥", "된장찌개", "비빔밥", "칼국수", "불고기", "떡볶이", "제육볶음"),          // 한식, 2
        listOf("깐풍기", "볶음면", "동파육", "짜장면", "짬뽕", "마파두부", "탕수육", "토마토 달걀볶음", "고추잡채"),   // 중식, 3
        listOf("팟타이", "카오 팟", "나시고렝", "파인애플 볶음밥", "쌀국수", "똠얌꿍", "반미", "월남쌈", "분짜"),     // 아시안, 4
        listOf("라자냐", "그라탱", "뇨끼", "끼슈", "프렌치 토스트", "바게트", "스파게티", "피자", "파니니")          // 양식, 5
    )

    fun getMenuCategory(menu: String): String {

        for (categoryIndex in CATEGORY_INDEX_START..CATEGORY_INDEX_END) {
            if (allMenus[categoryIndex].contains(menu)) {
                return getMenuCategoryName(categoryIndex)
            }
        }

        return ""
    }

    fun getCategoryMenus(categoryName: String): List<String> = allMenus[getMenuCategoryIndex(categoryName)]

    fun countMaxCategoryNumber(findMenus: List<String>): Int {
        var max: Int = 0

        for (categoryIndex in CATEGORY_INDEX_START..CATEGORY_INDEX_END) {
            val count = countMatch(allMenus[categoryIndex], findMenus)
            if (max < count) max = count
        }

        return max
    }

    private fun countMatch(list1: List<String>, list2: List<String>): Int {
        var count = 0

        for (index in list2.indices) {
            if (list1.contains(list2[index])) count++
        }

        return count
    }

    private fun getMenuCategoryIndex(categoryName: String): Int {
        return when (categoryName) {
            "일식" -> 1
            "한식" -> 2
            "중식" -> 3
            "아시안" -> 4
            "양식" -> 5
            else -> 0
        }
    }

    fun getMenuCategoryName(categoryIndex: Int): String {
        return when (categoryIndex) {
            1 -> "일식"
            2 -> "한식"
            3 -> "중식"
            4 -> "아시안"
            5 -> "양식"
            else -> ""
        }
    }

}