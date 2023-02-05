package menu

fun main() {
    val recommendView = MenuRecommendView()
    val recommendService = MenuRecommendService(MenuInfo())
    MenuRecommendApplication(recommendView,recommendService).startRecommendMenu()
}
