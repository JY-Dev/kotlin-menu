package menu.model

data class RecommendMenuResult(val categoryList: List<String>, val menuResultList : List<MenuResult>)
data class MenuResult(val name : String, val menu : List<String>)