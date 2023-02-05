package menu.model

data class Coach(val name : String, val inedibleMenuList : Set<String>){
    fun possibleMenu(recommendMenuList : Set<String>, menu : String) : Boolean{
        val withoutMenuList = recommendMenuList+inedibleMenuList
        return (!withoutMenuList.contains(menu))
    }
}