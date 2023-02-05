package menu

import camp.nextstep.edu.missionutils.Console

class MenuRecommendView {

    fun startRecommendMenu(){
        println("점심 메뉴 추천을 시작합니다.")
    }

    fun inputCoaches() : String{
        println("코치의 이름을 입력해 주세요. (, 로 구분)")
        return Console.readLine()
    }

    fun inputCoachInedibleFoods(coachName : String) : String{
        println("$coachName(이)가 못 먹는 메뉴를 입력해 주세요.")
        return Console.readLine()
    }
}