package menu.domain

import camp.nextstep.edu.missionutils.Randoms
import menu.model.Coach
import menu.model.MenuBoard
import menu.values.CATEGORY_INDEX_END
import menu.values.CATEGORY_INDEX_START

class Generator {

    fun recommendCategories(daysSize: Int, menuBoard: MenuBoard): List<String> {
        val categoryRecommendations: MutableList<String> = mutableListOf()

        while (categoryRecommendations.size < daysSize) {
            val randomCategoryIndex = Randoms.pickNumberInRange(CATEGORY_INDEX_START, CATEGORY_INDEX_END)
            val categoryRecommendation = menuBoard.getMenuCategoryName(randomCategoryIndex)
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

}