package onelayer.networks

class Adaline(input: MutableList<MutableList<Number>>){
    var x = input
    var y = x.removeAt(input.size-1)
    var w = MutableList(x.size){0.0}
    var b = 0.0
    private fun yin (input:MutableList<Double>):Double{
        var yin = 0.0
        var i = 0
        while (i < w.size){
            yin += w[i] * input[i]
            i++
        }
        yin += b
        return yin
    }
    fun compute (input:MutableList<Double>):Int{
        return when {
            yin(input) >= 0 -> 1
            else -> -1
        }
    }
    fun learn (a:Number,delta:Number,successPercent:Number?,debug:Boolean){
        var m = 0
        var e = 0.0
        var es = mutableListOf<Double>()
        while (true){
            if (m == w.size - 1){
                m = 0
                es = es.toDoubleArray().toSortedSet().reversed().toMutableList()
                if (es.first() <= delta.toDouble()){
                    if (successPercent == null) {
                        break
                    }
                }
                if (successPercent != null) {
                    if (test(debug) >= successPercent.toDouble()){
                        break
                    }
                }
                es = mutableListOf()
            }
            for (i in 0 until y.size){
                val line = mutableListOf<Double>()
                for (n in 0 until x.size){
                    line.add(x[n][i].toDouble())
                }
                if (compute(line).toDouble() != y[i].toDouble()){
                    for (j in 0 until x.size){
                        e = w[j]
                        w[j] += a.toDouble() * x[j][i].toDouble() * (y[i].toDouble() - yin(line))
                        e -= w[j]
                        es.add(e)
                    }
                    b += a.toDouble() * (y[i].toDouble() - yin(line))
                }
                if (debug) print(e)
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
            if (compute(line).toDouble() == y[i].toDouble())
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
    fun print (e:Double){
        println(w + "$b" + "error : $e")
    }
}