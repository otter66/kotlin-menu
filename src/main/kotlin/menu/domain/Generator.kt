package menu.domain

import camp.nextstep.edu.missionutils.Randoms
import menu.model.Coach
import menu.model.MenuBoard
import menu.values.CATEGORY_INDEX_END
import menu.values.CATEGORY_INDEX_START
import menu.values.Menu

class Generator {

    fun recommendCategories(daysSize: Int, menuBoard: MenuBoard): List<String> {
        val categoryRecommendations: MutableList<String> = mutableListOf()

        while (categoryRecommendations.size < daysSize) {
            val categoryRecommendation = getCategoryName(Randoms.pickNumberInRange(CATEGORY_INDEX_START, CATEGORY_INDEX_END))
            val tmpCategoryRecommendations = categoryRecommendations + listOf(categoryRecommendation)

            if (menuBoard.countMaxCategoryNumber(tmpCategoryRecommendations) < 2) {
                categoryRecommendations.add(categoryRecommendation)
            }
        }

        return categoryRecommendations
    }

    fun recommendMenus(coach: Coach, menuBoard: MenuBoard, categories: List<String>): List<String> {
        val menuRecommendations: MutableList<String> = mutableListOf()
        var categoryIndex = 0

        while (categoryIndex < categories.size) {
            val randomMenu = Randoms.shuffle(menuBoard.getCategoryMenus(categories[categoryIndex]))[0]

            if (!menuRecommendations.contains(randomMenu) &&
                !coach.containsCantEatMenu(randomMenu)
            ) {
                menuRecommendations.add(randomMenu)
                categoryIndex++
            }
        }
        return menuRecommendations
    }

    // todo MenuBoard Class 이용해 해당 함수 사용 않도록 수정 필요
    private fun getCategoryName(categoryNumber: Int): String {
        return when (categoryNumber) {
            Menu.JAPAN.number -> Menu.JAPAN.name_ko
            Menu.KOREA.number -> Menu.KOREA.name_ko
            Menu.CHINA.number -> Menu.CHINA.name_ko
            Menu.ASIA.number -> Menu.ASIA.name_ko
            Menu.WESTERN.number -> Menu.WESTERN.name_ko
            else -> ""
        }
    }

}