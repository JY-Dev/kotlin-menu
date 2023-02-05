package menu

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class MenuRecommendServiceTest {
    private val menuInfo = MenuInfo()
    private val recommendService = MenuRecommendService(menuInfo)

    @BeforeEach
    fun clear(){
        recommendService.clear()
    }

    @Test
    fun getRecommendMenuTest(){
        val menu = recommendService.getRecommendMenu("coach", menuInfo.categories[0])
        Assertions.assertThat(menu).isNotEmpty
    }

    @Test
    fun getRecommendMenuExceptionTest(){
        val category = menuInfo.categories[0]
        val menuList = menuInfo.menuMap[category] ?: listOf()
        recommendService.setInedibleMenu("coach",menuList)
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java){
            recommendService.getRecommendMenu("coach",category)
        }
    }

    @Test
    fun getRecommendCategoryExceptionTest(){
        val category = menuInfo.categories[0]
        val menuList = menuInfo.menuMap[category] ?: listOf()
        recommendService.setInedibleMenu("coach",menuList)
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java){
            recommendService.getRecommendMenu("coach",category)
        }
    }

    @Test
    fun getWithoutMenuTest(){
        val category = menuInfo.categories[0]
        val menuList = menuInfo.menuMap[category] ?: listOf()
        val inedibleMenu = menuList.subList(0,2)
        recommendService.setInedibleMenu("coach",inedibleMenu)
        Assertions.assertThat(recommendService.getWithoutMenu("coach").containsAll(inedibleMenu)).isTrue
    }
}