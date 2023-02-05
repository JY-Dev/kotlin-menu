package menu

import menu.data.MenuRecommendService
import menu.model.MenuInfo
import menu.view.MenuRecommendView

fun main() {
    val recommendView = MenuRecommendView()
    val recommendService = MenuRecommendService(MenuInfo())
    MenuRecommendApplication(recommendView,recommendService).startRecommendMenu()
}
