package mlp.networks

import kotlin.math.exp
import kotlin.random.Random

class MLP (input: MutableList<MutableList<Number>>,learningRate:Number){
    val x = input[0]
    val y = input[1]
    val t = input[2]

    var w = Random.nextDouble(-0.5, +0.5)
    var wx = Random.nextDouble(-0.5, +0.5)
    var wy = Random.nextDouble(-0.5, +0.5)
    var v = Random.nextDouble(-0.5, +0.5)
    var vx = Random.nextDouble(-0.5, +0.5)
    var vy = Random.nextDouble(-0.5, +0.5)

    val result = mutableListOf<MutableList<Double>>()

    init{
        for (j in 0 until 100) {
            for (i in 0 until x.size) {
                learn(x[i].toDouble(), y[i].toDouble(), t[i].toDouble(), learningRate.toDouble())
                result.sortBy { it[0] }
                result.reverse()
                val tested = test(true)
                if(result.isEmpty()){
                    result.add(mutableListOf(tested,w,wx,wy,v,vx,vy))
                }else {
                    if (tested > result[0][0]){
                        result.add(mutableListOf(tested,w,wx,wy,v,vx,vy))
                    }
                }
            }
        }
        w = result[0][1]
        wx = result[0][2]
        wy = result[0][3]
        v = result[0][4]
        vx = result[0][5]
        vy = result[0][6]
        heavyTest(true)
    }
    fun use(x:Double,y: Double):Double{
        val result = yin(x, y)
        return (1 - exp(-result))/(1 + exp(-result))
    }
    fun compute(x:Double):Double{
        return (1 - exp(-x))/(1 + exp(-x))
    }
    private fun reverseCompute(x:Double):Double{
        return (2 * exp(-x))/((1 + exp(-x))*((1 + exp(-x))))
    }
    private fun learn (x:Double, y:Double, t:Double, a:Double){
        val zj = compute(zin(x,y))
        val yk = compute(yin(x,y))
        val delta = (t - yk)*reverseCompute(yin(x,y))

        val deltaw = a * delta
        val deltawx = a * delta * zj
        val deltawy = a * delta * zj

        val deltain = (delta * wx) + (delta * wy)
        val deltaj = deltain * reverseCompute(deltain)

        val deltav = a * deltaj
        val deltavx = a * deltaj * x
        val deltavy = a * deltaj * y

        w += deltaw
        wx += deltawx
        wy += deltawy
        v += deltav
        vx += deltavx
        vy += deltavy
    }

    private fun zin (x:Double, y:Double):Double{
        return v + (x*vx) + (y*vy)
    }
    private fun yin (x:Double, y:Double):Double{
        return w + (zin(x,y)*wx) + (zin(x,y)*wy)
    }
    fun test (debug:Boolean):Double{
        var wins = 0
        var losses = 0
        for (i in 0 until x.size){
            var computed =use(x[i].toDouble(),y[i].toDouble())
            var mustbe = t[i].toDouble()
            if (computed < mustbe + 0.5 && computed > mustbe - 0.5)
                wins ++
            else
                losses ++
        }
        val percent = (wins.toDouble() * 100.0)/(wins.toDouble() + losses.toDouble())
        if (debug) println("$wins:$losses = ${percent.toInt()}%")
        return percent
    }
    fun heavyTest (debug:Boolean):Double{
        var wins = 0
        var losses = 0
        for (i in 0 until x.size){
            var computed =use(x[i].toDouble(),y[i].toDouble())
            var mustbe = t[i].toDouble()
            if (computed < mustbe + 0.5 && computed > mustbe - 0.5) {
                wins++
                println("computed = $computed mustbe = $mustbe win")
            }
            else {
                losses++
                println("computed = $computed mustbe = $mustbe lose")
            }

        }
        val percent = (wins.toDouble() * 100.0)/(wins.toDouble() + losses.toDouble())
        if (debug) println("$wins:$losses = ${percent.toInt()}%")
        return percent
    }
}