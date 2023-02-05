package menu.data

import camp.nextstep.edu.missionutils.Randoms
import menu.model.Coach
import menu.model.MenuInfo
import menu.model.MenuResult
import menu.model.RecommendMenuResult
import java.util.LinkedHashMap

class MenuRecommendService(private val menuInfo: MenuInfo) {

    fun getRecommendMenuResult(coachList : List<Coach>) : RecommendMenuResult {
        val categoryList = mutableListOf<String>()
        val selectCategoryCntMap = hashMapOf<String,Int>()
        val recommendMenuMap = linkedMapOf<String,LinkedHashSet<String>>()
        while (selectCategoryCntMap.entries.sumOf { it.value } < RECOMMEND_DAY){
            val recommendCategory = getRecommendCategory(selectCategoryCntMap)
            coachList.forEach { coach ->
                val menu = getRecommendMenu(coach, recommendCategory,recommendMenuMap[coach.name]?: setOf())
                addMenu(coach.name,menu,recommendMenuMap)
            }
            increaseSelectCategoryCnt(recommendCategory,selectCategoryCntMap)
            categoryList.add(recommendCategory)
        }
        val menuResultList = recommendMenuMap.map { (name,menuList) ->
            MenuResult(name, menuList.toList())
        }
        return RecommendMenuResult(categoryList,menuResultList)
    }

    private fun addMenu(name : String, menu : String, recommendMenuMap : LinkedHashMap<String,LinkedHashSet<String>>){
        val recommendMenuList = recommendMenuMap[name] ?: LinkedHashSet()
        recommendMenuList.add(menu)
        recommendMenuMap[name] = recommendMenuList
    }

    private fun increaseSelectCategoryCnt(category: String, selectCategoryCntMap : HashMap<String,Int>){
        val cnt = selectCategoryCntMap[category]?:0
        selectCategoryCntMap[category] = cnt.plus(1)
    }

    private fun getRecommendMenu(coach : Coach, category : String, recommendMenuList : Set<String>) : String {
        val menuList = menuInfo.menuMap[category]?: listOf()
        val visit = hashMapOf<String,Unit>()
        while (true){
            val menu = Randoms.shuffle(menuList)[0]
            if(coach.possibleMenu(recommendMenuList, menu))
                return menu
            visit[menu] = Unit
            if(menuList.size == visit.size)
                throw IllegalArgumentException("추천할 메뉴가 없습니다.")
        }
    }

    private fun getRecommendCategory(selectCategoryCntMap : HashMap<String,Int>) : String {
        val visit = hashMapOf<String,Unit>()
        while (true){
            val index = Randoms.pickNumberInRange(1, 5)
            val category = menuInfo.categories[index-1]
            val selectCnt = selectCategoryCntMap[category]?:0
            if(selectCnt < CATEGORY_SELECT_MAX)
                return category
            visit[category] = Unit
            if(visit.size == menuInfo.categories.size)
                throw IllegalArgumentException("추천할 카테고리가 없습니다.")
        }
    }

    companion object{
        const val CATEGORY_SELECT_MAX = 2
        const val RECOMMEND_DAY = 5
    }
}