package menu.data

import camp.nextstep.edu.missionutils.Randoms
import menu.model.MenuInfo
import menu.model.MenuResult
import menu.model.RecommendMenuResult

class MenuRecommendService(private val menuInfo: MenuInfo) {
    private val selectCategoryCntMap = hashMapOf<String,Int>()
    private val inedibleMenuMap = hashMapOf<String,HashSet<String>>()
    private val recommendMenuMap = linkedMapOf<String,LinkedHashSet<String>>()

    fun setInedibleMenu(coachName : String, menuList : List<String>){
        inedibleMenuMap[coachName] = menuList.toHashSet()
    }

    fun getRecommendMenuResult(coachList : List<String>) : RecommendMenuResult {
        val categoryList = mutableListOf<String>()
        while (selectCategoryCntMap.entries.sumOf { it.value } < RECOMMEND_DAY){
            val recommendCategory = recommendCategory()
            coachList.forEach { name ->
                val menu = getRecommendMenu(name, recommendCategory)
                addMenu(name,menu)
            }
            increaseSelectCategoryCnt(recommendCategory)
            categoryList.add(recommendCategory)
        }
        val menuResultList = recommendMenuMap.map { (name,menuList) ->
            MenuResult(name, menuList.toList())
        }
        return RecommendMenuResult(categoryList,menuResultList)
    }

    private fun addMenu(name : String, menu : String){
        val recommendMenuList = recommendMenuMap[name] ?: LinkedHashSet()
        recommendMenuList.add(menu)
        recommendMenuMap[name] = recommendMenuList
    }

    private fun increaseSelectCategoryCnt(category: String){
        val cnt = selectCategoryCntMap[category]?:0
        selectCategoryCntMap[category] = cnt.plus(1)
    }

    fun getRecommendMenu(coachName: String, category : String) : String {
        val menuList = menuInfo.menuMap[category]?: listOf()
        val withoutMenu = getWithoutMenu(coachName)
        val visit = hashMapOf<String,Unit>()
        while (true){
            val menu = Randoms.shuffle(menuList)[0]
            if(!withoutMenu.contains(menu))
                return menu
            visit[menu] = Unit
            if(menuList.size == visit.size)
                throw IllegalArgumentException("추천할 메뉴가 없습니다.")
        }
    }

    fun getWithoutMenu(coachName: String) : Set<String>{
        val alreadySelectMenu = recommendMenuMap[coachName]?: setOf()
        val inedibleMenu = inedibleMenuMap[coachName] ?: setOf()
        return alreadySelectMenu+inedibleMenu
    }

    fun clear(){
        inedibleMenuMap.clear()
        recommendMenuMap.clear()
        selectCategoryCntMap.clear()
    }

    private fun recommendCategory() : String {
        val visit = hashMapOf<String,Unit>()
        while (true){
            val index = Randoms.pickNumberInRange(1, 5)
            val category = menuInfo.categories[index-1]
            val selectCnt = selectCategoryCntMap[category]?:0
            if(selectCnt < CATEGORY_SELECT_MAX)
                return category
            visit[category] = Unit
            if(visit.size == menuInfo.categories.size)
                throw IllegalArgumentException("추천할 카테고리가 없습니")
        }
    }

    companion object{
        const val CATEGORY_SELECT_MAX = 2
        const val RECOMMEND_DAY = 5
    }
}