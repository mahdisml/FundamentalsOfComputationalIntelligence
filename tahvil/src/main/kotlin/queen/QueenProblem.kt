package queen

import kotlin.random.Random
import java.io.File

class World (val populationSize: Int){
    var population :MutableList<MutableList<Int>> = mutableListOf()
    init {
        for (i in 0 until populationSize){
            val mutableList = mutableListOf<Int>()
            for (j in 0 until 8)
                mutableList.add(Random.nextInt(0,8))
            population.add(mutableList)
        }
    }
    private fun reInit(){
        population = mutableListOf()
        for (i in 0 until populationSize) {
            val mutableList = mutableListOf<Int>()
            for (j in 0 until 8)
                mutableList.add(Random.nextInt(0, 8))
            population.add(mutableList)
        }

    }
    private fun check():Boolean{
        population.sortBy {
            fitness(it)
        }
        if (fitness(population[0]) == 0){
            return true
        }
        return false
    }
    private fun fitness (mutableList: MutableList<Int>):Int{
        var bad = 0
        for (i in 0 until 8){
            for (j in 0 until 8){
                if (i != j){
                    if (mutableList[i] == mutableList[j])
                        bad ++
                }
            }
        }
        for (i in 0 until 7){
            for (j in 1 until 7){
                    mutableList.elementAtOrNull(i + j)?.let {
                        if (mutableList[i] + j == mutableList[i+j])
                            bad++
                    }
                    mutableList.elementAtOrNull(i - j)?.let {
                        if (mutableList[i] - j == mutableList[i-j])
                            bad++
                    }
                    mutableList.elementAtOrNull(i + j)?.let {
                        if (mutableList[i] - j == mutableList[i+j])
                            bad++
                    }
                    mutableList.elementAtOrNull(i - j)?.let {
                        if (mutableList[i] - j == mutableList[i - j])
                            bad++
                    }
            }
        }
        if (bad == 0){
            printQueen(mutableList)
        }
        return bad
    }
    private fun selection (hazf:Int){
        population.sortBy {
            fitness(it)
        }
        if (population.size > 15) {
            for (i in 0..hazf) {
                population.elementAtOrNull(i)?.let {
                    population.removeAt(i) }

            }

        }
    }
    private fun crossOver(){
        val size = population.size
        for (m in 0 until size){
            val i = Random.nextInt(0,8)
            val j = Random.nextInt(0,8)
            population.elementAtOrNull(m+1)?.let {
                val new = population[m]
                new[i] = population[m+1][i]
                new[j] = population[m+1][j]
                population.add(new)
            }

        }
    }
    private fun mutation (){
        var i = 0
        val size = population.size
        while (i < size){
            val j = Random.nextInt(0,8)
            val k = Random.nextInt(0,8)
            val new = population[i]
            new[j] = k
            population.add(new)

            i += 10
        }
    }
    fun calculate(){
        var i = 0
        while (!check()){
            if (i > 7){
                reInit()
                i = 0
            }
            selection((population.size/2))
            crossOver()
            mutation()
            selection((population.size/11))
            i++
        }
        population.sortBy {
            fitness(it)
        }
        printQueen(population[0])
    }
    private fun printQueen(mutableList: MutableList<Int>){
        println()
        for (i in 0..7){
            for(j in 0..7){
                if (j == mutableList[i]){
                    print("Q")
                }else{
                    print("0")
                }
            }
            println()
        }
    }

}
fun main (){

    val world = World(50000)
    world.calculate()

}










