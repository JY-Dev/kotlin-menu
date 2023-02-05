package menu

class MenuRecommendApplication(private val menuRecommendView: MenuRecommendView,
                               private val menuRecommendService: MenuRecommendService) {
    fun startRecommendMenu(){
        menuRecommendView.startRecommendMenu()
        val coachList = retryIllegalArgument {
            menuRecommendView.inputCoaches().split(",").also {
                validationCoach(it)
            }
        }
        coachList.forEach { name ->
            retryIllegalArgument {
                val inedibleMenu = menuRecommendView.inputCoachInedibleMenu(name).split(",")
                validationInedibleMenu(inedibleMenu)
                menuRecommendService.setInedibleMenu(name,inedibleMenu)
            }
        }
        val result = menuRecommendService.getRecommendMenuResult(coachList)
        menuRecommendView.printRecommendMenuResult(result)
    }

    private fun validationCoach(coachList : List<String>){
        if((coachList.size !in 2..5))
            throw IllegalArgumentException("코치의 수는 2에서 5사이 입니다.")
    }

    private fun validationInedibleMenu(inedibleMenuList : List<String>){
        if(inedibleMenuList.size !in 0..2)
            throw IllegalArgumentException("먹지 못하는 메뉴의 수는 0에서 2사이 입니다.")
    }

    private fun <T> retryIllegalArgument(block : () -> T) : T{
        return try {
            block()
        }catch (e : IllegalArgumentException){
            menuRecommendView.printError(e.message?:"오류 발생")
            retryIllegalArgument(block)
        }
    }


}