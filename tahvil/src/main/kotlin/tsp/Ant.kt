package tsp

import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.random.Random


class Ant (val cities :MutableList<MutableList<Int>>,
           val edges :MutableList<Edge>,
           val results:MutableList<MutableList<Int>>) {

    var imHere = Random.nextInt(0,20)
    val doneCities = mutableListOf<Int>()
    val doneEdges = mutableListOf<Edge>()
    var choose = mutableListOf<Edge>()
    init{
        GlobalScope.async {
            var i = 0
            while (i < 20) {
                calculate()
                i++
            }

            results.sortBy { fitness(it) }

            if (results.isEmpty()){
                results.add(doneCities)
                printTsp(results[0])
            }
            if (fitness(doneCities) < fitness(results[0])) {
                results.add(doneCities)
                printTsp(results[0])
            }
            back()
        }
    }
    suspend fun calculate(){
        findEdges()
        if (choose.size > 2){
            if (Random.nextBoolean()) {
                choose.sortBy { it.distance }
                var new = mutableListOf<Edge>()
                for (i in 0..2) {
                    new.add(choose[i])
                }
                choose = new
            }
        }
        if (choose.isNotEmpty()) {
            var rand = choose.random()

            var po = mutableListOf<Double>()
            for (i in choose){
                po.add(probability(i)/allProbability(choose))
            }
            for (i in 1 until po.size){
                po[i] = po[i] + po[i-1]
            }
            var randomNumber:Double = try {
                Random.nextDouble(po[0],po[po.size-1])
            } catch (e: Exception) {
                0.0
            }

            for (i in 0 until po.size){
                if (po.elementAtOrNull(i+1) == null){
                    rand = choose[i]
                }else {
                    if (randomNumber < po[i + 1]){
                        rand = choose[i]
                        break
                    }
                }
            }

            if (imHere == rand.x) {
                doneCities.add(imHere)
                doneEdges.add(rand)
                imHere = rand.y
                rand.pheromone += 0.01
            } else {
                doneCities.add(imHere)
                doneEdges.add(rand)
                imHere = rand.x
                rand.pheromone += 0.01
            }
            delay(rand.distance.toLong()/10)
        }else{
            doneCities.add(imHere)
        }

    }
    suspend fun back(){
        for (i in doneEdges){
            delay(i.distance.toLong()/10)
            i.pheromone += 0.01
        }
    }
    fun findEdges(){
        choose.clear()
        for(i in edges){
            if (i.x == imHere){
                if (!doneEdges.contains(i) && !doneCities.contains(i.y)){
                    choose.add(i)
                }
            }else if (i.y ==imHere){
                if (!doneEdges.contains(i) && !doneCities.contains(i.x)){
                    choose.add(i)
                }
            }
        }
    }
    fun fitness (mutableList: MutableList<Int>):Double{
        var masafat = 0.0
        var x = 0
        var y = 0
        val temp = mutableList.toMutableList()
        x = temp[0]
        temp.removeAt(0)
        for (i in 0 until temp.size){
            temp.elementAtOrNull(0)?.let {
                y = temp[0]
                masafat += masir(x,y)
                x = y
                temp.removeAt(0) }
        }
        return masafat
    }
    private fun masir (x:Int, y:Int):Double{
        val xResult = (cities[y][0] - cities[x][0]).absoluteValue
        val yResult = (cities[y][1] - cities[x][1]).absoluteValue
        return sqrt((xResult*xResult).toDouble()+(yResult*yResult).toDouble())
    }
    fun printTsp(mutableList: MutableList<Int>){
        println()
        for (i in 0 until 20){
            if (i == 19)
                print(" ${mutableList[i]}")
            else
                print(" ${mutableList[i]}, ")
        }
        print (" : ${fitness(mutableList)}")
        println()
    }
    fun probability (edge:Edge) : Double{
        return edge.pheromone * (1/edge.distance)
    }
    fun allProbability (edge:MutableList<Edge>) : Double{
        var result = 0.0
        for (i in edge)
            result += probability(i)
        return result
    }
}

class Edge(val x:Int,val y: Int,val distance:Double){
    var pheromone = 1.0
}

class AntColony {
    var cities :MutableList<MutableList<Int>> = mutableListOf()
    var edges :MutableList<Edge> = mutableListOf()
    var results :MutableList<MutableList<Int>> = mutableListOf()

    init {
        val data: List<String> = File("cities.txt").readText().split(" ", "\n")
        var m = 0
        while (m < data.size - 1) {
            data[m + 1]
            cities.add(mutableListOf(data[m].toInt(), data[m + 1].toInt()))
            m += 2
        }
        for (i in 0 until cities.size){
            for (j in 0 until cities.size){
                var had = false
                for(m in edges){
                    if ((m.x == i && m.y == j)||(m.y == i && m.x == j))
                        had = true
                    if (i == j)
                        had = true
                    if (i == 0 && j == 0)
                        had = true
                }
                if(!had && i !=j )
                    edges.add(Edge(i,j,masir(i,j)))
            }
        }
    }
    private fun masir (x:Int, y:Int):Double{
        val xResult = (cities[y][0] - cities[x][0]).absoluteValue
        val yResult = (cities[y][1] - cities[x][1]).absoluteValue
        return sqrt((xResult*xResult).toDouble()+(yResult*yResult).toDouble())
    }
    fun fitness (mutableList: MutableList<Int>):Double{
        var masafat = 0.0
        var x = 0
        var y = 0
        val temp = mutableList.toMutableList()
        x = temp[0]
        temp.removeAt(0)
        for (i in 0 until temp.size){
            temp.elementAtOrNull(0)?.let {
                y = temp[0]
                masafat += masir(x,y)
                x = y
                temp.removeAt(0) }
        }
        return masafat
    }
    fun printTsp(mutableList: MutableList<Int>){
        println()
        for (i in 0 until 20){
            if (i == 19)
                print(" ${mutableList[i]}")
            else
                print(" ${mutableList[i]}, ")
        }
        print (" : ${fitness(mutableList)}")
        println()
    }
    suspend fun calculate(size:Int,time:Int){
        val ants = mutableListOf<Ant>()
        val jobs = mutableListOf<Job>()
        for (m in 0 until time){
            for (i in 0..size){
                var job = GlobalScope.async {
                    ants.add(Ant(cities,edges,results))
                }
                jobs.add(job)
            }
            for(i in jobs){
                i.join()
            }
            for(i in edges){
                i.pheromone = i.pheromone - 0.001
            }
            delay(50)

        }

    }

}
suspend fun main (){
    val antColony = AntColony()
    antColony.calculate(500,999999999)
}