package menu.controller

import menu.domain.Generator
import menu.model.Coach
import menu.model.MenuBoard
import menu.values.NOTICE_RECOMMEND_SUCCESS_MESSAGE
import menu.values.NOTICE_SERVICE_RESULT_MESSAGE
import menu.values.NOTICE_SERVICE_START_MESSAGE
import menu.values.REQUIRE_COACHES_MESSAGE
import menu.view.InputView
import menu.view.OutputView

class ServiceController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val generator: Generator
) {
    
    private val menuBoard = MenuBoard()
    private val days = listOf("월요일", "화요일", "수요일", "목요일", "금요일")

    fun run() {
        noticeStart()
        
        val coaches: List<Coach> = createCoaches()
        val categoryRecommendations: List<String> = generator.recommendCategories(days.size, menuBoard)
        repeat(coaches.size) { coachIndex ->
            val coachRecommendedMenus = generator.recommendMenus(coaches[coachIndex], menuBoard, categoryRecommendations)
            coaches[coachIndex].setRecommendedMenus(coachRecommendedMenus)
        }

        noticeResult(coaches, categoryRecommendations)
        noticeEnd()
    }

    fun noticeResult(coaches: List<Coach>, categories: List<String>) {
        outputView.printMessage(NOTICE_SERVICE_RESULT_MESSAGE)
        outputView.printList(listOf("구분") + days)
        outputView.printList(listOf("카테고리") + categories)

        repeat(coaches.size) { coachIndex ->
            val coachRecommendations = listOf(coaches[coachIndex].getName()) + coaches[coachIndex].getRecommendedMenus()
            outputView.printList(coachRecommendations)
        }
    }

    private fun noticeStart() {
        outputView.printMessage(NOTICE_SERVICE_START_MESSAGE)
        outputView.printMessage("")
    }

    private fun noticeEnd() {
        outputView.printMessage("")
        outputView.printMessage(NOTICE_RECOMMEND_SUCCESS_MESSAGE)
    }

    // 사용자로부터 코치의 기본 정보를 입력 받아 생성한다
    private fun createCoaches(): List<Coach> {
        val coaches = mutableListOf<Coach>()
        val coachNames = getValidatedInputCoachNames()

        repeat (coachNames.size) { index ->
            val coachName = coachNames[index]
            val coachCantEatMenus = getValidatedCoachCantEatMenus(coachName)

            coaches.add(Coach(coachName, coachCantEatMenus))
        }

        return coaches
    }

    // 코치 이름들 입력을 검사해 반환
    private fun getValidatedInputCoachNames(): List<String> {
        while (true) {
            var coachNames: List<String>

            try {
                outputView.printMessage(REQUIRE_COACHES_MESSAGE)
                coachNames = inputView.readCoachNames()
                outputView.printMessage("")

                return coachNames

            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e)
            }
        }
    }

    // 코치가 못먹는 음식들 입력을 검사해 반환
    private fun getValidatedCoachCantEatMenus(coachName: String): List<String> {
        while (true) {
            var coachCantEatMenus: List<String>

            try {
                outputView.printRequireCoachCantEatMenus(coachName)
                coachCantEatMenus = inputView.readCoachCantEatMenus()
                outputView.printMessage("")

                return coachCantEatMenus

            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e)
            }
        }
    }
}
