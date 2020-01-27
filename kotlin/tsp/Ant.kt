package tsp

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.random.Random

class AntColony (val populationSize: Int){
    var population :MutableList<MutableList<Int>> = mutableListOf()
    var cities :MutableList<MutableList<Int>> = mutableListOf()
    var results :MutableList<MutableList<Int>> = mutableListOf()
    init {
        val data:List<String> = File("cities.txt").readText().split(" ","\n")
        var m = 0
        while (m < data.size - 1){
            data[m+1]
            cities.add(mutableListOf(data[m].toInt(),data[m+1].toInt()))
            m += 2
        }
        for (i in 0 until populationSize){
            val mutableList = mutableListOf<Int>()
            val temp = mutableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,
                14,15,16,17,18,19)

            for (j in 0 until 20) {
                val getter = temp.random()
                temp.removeIf { it == getter }
                mutableList.add(getter)
            }
            population.add(mutableList)
        }
    }
    private fun reInit(){
        val data:List<String> = File("cities.txt").readText().split(" ","\n")
        var m = 0
        while (m < data.size - 1){
            cities.add(mutableListOf(data[m].toInt(),data[m+1].toInt()))
            m += 2
        }
        for (i in 0 until populationSize){
            val mutableList = mutableListOf<Int>()
            val temp = mutableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,
                14,15,16,17,18,19)

            for (j in 0 until 20) {
                val getter = temp.random()
                temp.removeIf { it == getter }
                mutableList.add(getter)
            }
            population.add(mutableList)
        }
    }
    private fun check():Boolean{
        population.sortBy {
            fitness(it)
        }
        results.sortBy {
            fitness(it)
        }
        if (results.isNotEmpty()) {
            if (fitness(population[0]) < fitness(results[0])){
                results.add(population[0])
                printTsp(population[0])
            }
        }else {
            results.add(population[0])
            printTsp(population[0])
        }

        if (fitness(population[0]) == 0.0){
            return true
        }
        return false
    }
    private fun fitness (mutableList: MutableList<Int>):Double{
        var masafat = 0.0

        var i = 0
        while (i < mutableList.size - 1){
            masafat += masir(mutableList[i],mutableList[i+1])
            i += 2
        }
        return masafat
    }
    private fun masir (x:Int,y:Int):Double{
        val xResult = cities[y][0] - cities[x][0]
        val yResult = cities[y][1] - cities[x][1]

        return sqrt((xResult.absoluteValue*xResult.absoluteValue).toDouble()) + sqrt((yResult.absoluteValue*yResult.absoluteValue).toDouble())
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
            val new = population[i]
            new[19] = population[i][0]
            new[0] = population[i][19]
            population.add(new)
            i += 10
        }
    }
    fun calculate(){
        var i = 0
        while (!check()){
            if (i > 15){
                reInit()
                i = 0
            }
            selection((population.size/2))
            crossOver()
            mutation()
            selection((population.size/11))
            i++
        }
    }
    private fun printTsp(mutableList: MutableList<Int>){
        println()
        for (i in 0 until 20){
            print(" ${mutableList[i]} ")
        }
        print (" : ${fitness(mutableList)}")
        println()
    }

}
fun main (){

    val ant = AntColony(50000)
    ant.calculate()

}