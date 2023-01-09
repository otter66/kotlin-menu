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
            val categoryMenus: List<String> = menuBoard.getCategoryMenus(categories[categoryIndex])
            // todo 테스트 오류 발생 지점
            // todo 카테고리는 정상적으로 불러오지만,
            // todo 두번째 돌 때 아래 셔플에서 양식 카테고리의 메뉴를 줘도, 첫번째 카테고리인 한식 카테고리에서 메뉴를 가져옴;;;;;;

            val randomCategoryMenus = Randoms.shuffle(categoryMenus)
            val randomMenu: String = randomCategoryMenus[0]

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