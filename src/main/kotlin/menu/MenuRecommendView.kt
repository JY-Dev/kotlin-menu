package menu

import camp.nextstep.edu.missionutils.Console

class MenuRecommendView {

    fun startRecommendMenu(){
        println("점심 메뉴 추천을 시작합니다.")
        println()
    }

    fun inputCoaches() : String{
        println("코치의 이름을 입력해 주세요. (, 로 구분)")
        return Console.readLine()
    }

    fun inputCoachInedibleMenu(coachName : String) : String{
        println("$coachName(이)가 못 먹는 메뉴를 입력해 주세요.")
        return Console.readLine()
    }

    fun printRecommendMenuResult(result : RecommendMenuResult){
        println("메뉴 추천 결과입니다.")
        println("[ 구분 | 월요일 | 화요일 | 수요일 | 목요일 | 금요일 ]")
        println("[ 카테고리 | ${result.categoryList.joinToString(" | ")} ]")
        result.menuResultList.forEach {
            println("[ ${it.name} | ${it.menu.joinToString(" | ")} ]")
        }
        println("추천을 완료했습니다.")
    }
}