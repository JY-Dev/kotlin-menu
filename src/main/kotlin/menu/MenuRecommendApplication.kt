package menu

class MenuRecommendApplication(private val menuRecommendView: MenuRecommendView,
                               private val menuRecommendService: MenuRecommendService) {
    fun startRecommendMenu(){
        menuRecommendView.startRecommendMenu()
        val inputCoaches = menuRecommendView.inputCoaches().split(",")
        inputCoaches.forEach { name ->
            val inedibleMenu = menuRecommendView.inputCoachInedibleMenu(name).split(",")
            menuRecommendService.setInedibleMenu(name,inedibleMenu)
        }
        val result = menuRecommendService.getRecommendMenuResult(inputCoaches)
        menuRecommendView.printRecommendMenuResult(result)
    }

}