package menu.domain

import camp.nextstep.edu.missionutils.Randoms
import menu.model.Coach
import menu.model.MenuBoard
import menu.values.CATEGORY_INDEX_END
import menu.values.CATEGORY_INDEX_START
import menu.values.Menu

class Generator {

//    private val recommendations: MutableList<Recommendation> = mutableListOf()
//
//    fun makeRecommendations(days: List<String>, coaches: List<Coach>): List<Recommendation> {
//        repeat(days.size) { index ->
//            recommendations.add(makeRecommendation(days[index], coaches))
//        }
//
//        return recommendations
//    }
//
//    // 요일별 추천 만들기
//    private fun makeRecommendation(day: String, coaches: List<Coach>): Recommendation {
//        val category = makeCategory(day, coaches)
//        val coachMenus: MutableList<Pair<String,String>> = mutableListOf()
//
//        repeat(coaches.size) { index ->
//            val menu: String = makeMenu(category, coaches[index])
//            coachMenus.add(Pair(coaches[index].getName(), menu))
//        }
//
//        return Recommendation(day, category, coachMenus)
//    }

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

    private fun makeMenu(category: String, coach: Coach): String {
        var menus: MutableList<String> = mutableListOf()
        Menu.values().forEach { it ->
            if (it.name_ko == category) menus = it.menus.toMutableList()
        }

        while (true) {
            val randomMenu = Randoms.shuffle(menus)[0]
            if (isAvailableMenu(coach, randomMenu)) return randomMenu
            return randomMenu
        }
    }

    private fun isAvailableMenu(coach: Coach, menu: String): Boolean {
        if (coach.containsCantEatMenu(menu)) return false
        //if (coach.containsRecommend(menu)) return false

        return true
    }

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