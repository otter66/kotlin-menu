package menu.model

class Coach(
    private val name: String,
    private val cantEatMenus: List<String>
) {

    private var recommendedMenus: List<String> = listOf()

    fun containsCantEatMenu(menu: String): Boolean {
        repeat(cantEatMenus.size) { index ->
            if(cantEatMenus[index] == menu) {
                return true
            }
        }

        return false
    }

    fun setRecommendedMenus(recommendMenus: List<String>) {
        recommendedMenus = recommendMenus
    }

    fun getRecommendedMenus() = recommendedMenus

    fun getName() = name

}