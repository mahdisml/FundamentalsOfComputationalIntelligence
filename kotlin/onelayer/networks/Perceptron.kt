package onelayer.networks

class Perceptron(input: MutableList<MutableList<Number>>, var teta:Double){
    var x = input
    var y = x.removeAt(input.size-1) // javab
    var w = MutableList(x.size){0.0}
    var b = 0.0 //bias
    fun compute (input:MutableList<Double>):Int?{
        var yin = 0.0
        var i = 0
        while (i < w.size){
            yin += w[i] * input[i]
            i++
        }
        yin += b
        return when {
            teta < yin -> 1
            -teta < yin && yin < teta -> 0
            yin < -teta -> -1
            else -> null
        }
    }
    fun learn (a:Number,debug:Boolean){
        var m = 0 //loop checker
        var changed = false
        while (true){
            if (m == w.size - 1){
                m = 0
                if (!changed){
                    break
                }
            }
            changed = false
            for (i in 0 until y.size){
                val line = mutableListOf<Double>()
                for (n in 0 until x.size){
                    line.add(x[n][i].toDouble())
                }
                if (compute(line)!!.toDouble() != y[i].toDouble()){
                    for (j in 0 until x.size){
                        w[j] += a.toDouble() * x[j][i].toDouble() * y[i].toDouble()
                        changed = true
                    }
                    b += y[i].toDouble()
                }
                if (debug) print()
            }
            m++
        }
    }
    fun test (debug:Boolean):Double{
        var wins = 0
        var losses = 0
        for (i in 0 until y.size){
            val line = mutableListOf<Double>()
            for (n in 0 until x.size){
                line.add(x[n][i].toDouble())
            }
            if (debug) println(line + " : " + compute(line).toString() + " ?= " +  y[i].toString())
            if (compute(line)!!.toDouble() == y[i].toDouble())
                wins ++
            else
                losses ++
        }
        val percent = (wins.toDouble() * 100.0)/(wins.toDouble() + losses.toDouble())
        if (debug) println("$wins:$losses = ${percent.toInt()}%")
        return percent
    }
    fun print (){
        println(w + "$b")
    }
}