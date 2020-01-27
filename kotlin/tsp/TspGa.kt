package tsp

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.random.Random

class WorldTsp (val populationSize: Int){
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

        return false
    }
    private fun fitness (mutableList: MutableList<Int>):Double{
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
    private fun masir (x:Int,y:Int):Double{
        val xResult = (cities[y][0] - cities[x][0]).absoluteValue
        val yResult = (cities[y][1] - cities[x][1]).absoluteValue
        return sqrt((xResult*xResult).toDouble()+(yResult*yResult).toDouble())
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
        for (i in 0 until size){
            population.elementAtOrNull(i+1)?.let {
                when (Random.nextInt(0,1)) {//3
                    0 -> {population.add(offspring3(population[i].toMutableList(),population[i+1].toMutableList()))}
                    1 -> {population.add(offspring2(population[i].toMutableList(),population[i+1].toMutableList()))}
                    else -> {population.add(offspring1(population[i].toMutableList(),population[i+1].toMutableList()))}
                }
            }
        }
    }
    private fun offspring1(p1:MutableList<Int>,p2:MutableList<Int>):MutableList<Int>{
        val all = mutableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,
            14,15,16,17,18,19)
        val done = mutableListOf<Int>()
        val new = p1.toMutableList()
        when (Random.nextInt(0,2)) {
            0 -> {
                for (i in 0..9) {
                    all.remove(new[i])
                    done.add(new[i])
                }
                for (i in 10..19){
                    if (done.contains(p2[i])) {
                        new[i] = -1
                    } else {
                        new[i] = p2[i]
                        all.remove(new[i])
                    }
                }
                for (i in 10..19) {
                    if (new[i] == -1) {
                        new[i] = all[0]
                        all.remove(all[0])
                    }
                }
            }
            else -> {
                for (i in 10..19) {
                    all.remove(new[i])
                    done.add(new[i])
                }
                for (i in 0..9){
                    if (done.contains(p2[i])) {
                        new[i] = -1
                    } else {
                        new[i] = p2[i]
                        all.remove(new[i])
                    }
                }
                for (i in 0..9) {
                    if (new[i] == -1) {
                        new[i] = all[0]
                        all.remove(all[0])
                    }
                }
            }
        }
        return new
    }
    private fun offspring2(p1:MutableList<Int>,p2:MutableList<Int>):MutableList<Int>{
        val all = mutableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,
            14,15,16,17,18,19)
        val done = mutableListOf<Int>()
        val new = mutableListOf<Int>()
        when (Random.nextInt(0,2)) {
            0 -> {
                new.add(p1[0])
                all.remove(p1[0])
            }
            else -> {
                new.add(p2[0])
                all.remove(p2[0])
            }
        }
        all.shuffle()
        for (i in 1..19){
            if(all.contains(p1[i]) && all.contains(p2[i])){
                if (masir(new[i-1],p1[i]) < masir(new[i-1],p2[i])){
                    new.add(p1[i])
                    all.remove(p1[i])
                }else{
                    new.add(p2[i])
                    all.remove(p2[i])
                }
            }else if (all.contains(p1[i]) ){
                new.add(p1[i])
                all.remove(p1[i])
            } else if (all.contains(p2[i])) {
                new.add(p2[i])
                all.remove(p2[i])
            } else {
                new.add(all[0])
                all.removeAt(0)
            }
        }

        return new
    }
    private fun offspring3(p1:MutableList<Int>,p2:MutableList<Int>):MutableList<Int>{
        val all = mutableListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,
            14,15,16,17,18,19)
        var new = mutableListOf<Int>()
        all.shuffle()

        when (Random.nextInt(0,4)) {
            0 -> {
                for (i in 0..9){
                    new.add(p1[i])
                    all.remove(p1[i])
                }
                for (i in 10..19){
                    all.sortBy { masir(new[i-1],it) }
                    new.add(all[0])
                    all.removeAt(0)
                }
            }
            1 -> {
                for (i in 0..9){
                    new.add(p2[i])
                    all.remove(p2[i])
                }
                for (i in 10..19){
                    all.sortBy { masir(new[i-1],it) }
                    new.add(all[0])
                    all.removeAt(0)
                }
            }
            2 -> {
                new = MutableList<Int>(20){0}
                for (i in 19 downTo 10){
                    new.set(i,p1[i])
                    all.remove(p1[i])
                }
                for (i in 9 downTo 0){
                    all.sortBy { masir(new[i+1],it) }
                    new.set(i,all[0])
                    all.removeAt(0)
                }
            }
            else -> {
                new = MutableList<Int>(20){0}
                for (i in 19 downTo 10){
                    new.set(i,p2[i])
                    all.remove(p2[i])
                }
                for (i in 9 downTo 0){
                    all.sortBy { masir(new[i+1],it) }
                    new.set(i,all[0])
                    all.removeAt(0)
                }
            }
        }

        return new
    }
    private fun mutation (){
        var i = 0
        val size = population.size
        while (i < size) {
            when(Random.nextInt(0,9)){//9
                0 -> {
                    val m = Random.nextInt(0,20)
                    val n = Random.nextInt(0,20)
                    if (m != n) {
                        val new = population[i].toMutableList()
                        new[m] = population[i][n]
                        new[n] = population[i][m]
                        population.add(new)
                    }
                    i ++
                }
                1 -> {
                    val m = Random.nextInt(0,20)
                    val n = Random.nextInt(0,20)
                    val s = Random.nextInt(0,20)
                    val d = Random.nextInt(0,20)
                    if (m != n) {
                        val new = population[i].toMutableList()
                        new[m] = population[i][n]
                        new[n] = population[i][m]
                        population.add(new)
                    }
                    if (s != d) {
                        val new = population[i].toMutableList()
                        new[s] = population[i][d]
                        new[d] = population[i][s]
                        population.add(new)
                    }
                    i ++
                }
                2 -> {
                    val m = Random.nextInt(0,20)
                    val n = Random.nextInt(0,20)
                    val s = Random.nextInt(0,20)
                    val d = Random.nextInt(0,20)
                    val h = Random.nextInt(0,20)
                    val b = Random.nextInt(0,20)
                    if (m != n) {
                        val new = population[i].toMutableList()
                        new[m] = population[i][n]
                        new[n] = population[i][m]
                        population.add(new)
                    }
                    if (s != d) {
                        val new = population[i].toMutableList()
                        new[s] = population[i][d]
                        new[d] = population[i][s]
                        population.add(new)
                    }
                    if (h != b) {
                        val new = population[i].toMutableList()
                        new[h] = population[i][b]
                        new[b] = population[i][h]
                        population.add(new)
                    }
                    i ++
                }
                3 -> {
                    val n = Random.nextInt(0,20)
                    if (10 != n) {
                        val new = population[i].toMutableList()
                        new[10] = population[i][n]
                        new[n] = population[i][10]
                        population.add(new)
                    }
                    i ++
                }
                4 -> {
                    val n = Random.nextInt(0,20)
                    if (19 != n) {
                        val new = population[i].toMutableList()
                        new[19] = population[i][n]
                        new[n] = population[i][19]
                        population.add(new)
                    }
                    i ++
                }
                5 -> {
                    val m = 0
                    val n = 19
                    if (m != n) {
                        val new = population[i].toMutableList()
                        new[m] = population[i][n]
                        new[n] = population[i][m]
                        population.add(new)
                    }
                    i ++
                }
                6 -> {
                    val m = 9
                    val n = 10
                    if (m != n) {
                        val new = population[i].toMutableList()
                        new[m] = population[i][n]
                        new[n] = population[i][m]
                        population.add(new)
                    }
                    i ++
                }
                7 -> {
                    val n = Random.nextInt(0,20)
                    if (9 != n) {
                        val new = population[i].toMutableList()
                        new[9] = population[i][n]
                        new[n] = population[i][9]
                        population.add(new)
                    }
                    i ++
                }
                else -> {
                    val n = Random.nextInt(0,20)
                    if (0 != n) {
                        val new = population[i].toMutableList()
                        new[0] = population[i][n]
                        new[n] = population[i][0]
                        population.add(new)
                    }
                    i ++
                }
            }

        }
    }
    fun calculate(){
        var i = 0
        while (!check()){
            if (population.size<populationSize){
                mutation()
            }
            selection((population.size/2))
            selection((population.size/2))
            crossOver()
            mutation()


            i++
        }
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

}
fun main (){

    val worldTsp = WorldTsp(1000)
    worldTsp.calculate()

}