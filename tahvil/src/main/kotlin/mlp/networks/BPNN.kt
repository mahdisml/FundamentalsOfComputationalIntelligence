package mlp.networks

import java.util.concurrent.ThreadLocalRandom

class BPNN (input: MutableList<MutableList<Number>>){
    val x = input[0]
    val y = input[1]
    val t = input[2]
    var w1 = rand()
    var w2 = rand()
    var w3 = rand()
    var w4 = rand()
    var w5 = rand()
    var w6 = rand()

    private fun rand (): Double {
        return ThreadLocalRandom.current().nextDouble(-0.5, +0.5)
    }
    private fun compute (x: Double, y: Double) : Double{
        val h1 =((x*w1)+(y*w2))
        val h2 =((x*w3)+(y*w4))
        val result = ((h1* w5)+ (h2* w6))
        return if (result >= 0.5){
            1.0
        }
        else{
            0.0
        }
    }
    fun learn (a:Double,success:Double,debug:Boolean){ // a is learning rate

        while (true){
            //check
            if (test(debug) < success){
                w1 = rand()
                w2 = rand()
                w3 = rand()
                w4 = rand()
                w5 = rand()
                w6 = rand()
            }
            if (test(debug) >= success){
                break
            }
            for (i in 0 until x.size){
                //feedForward
                val h1 =((x[i].toDouble()*w1)+(y[i].toDouble()*w2))
                val h2 =((x[i].toDouble()*w3)+(y[i].toDouble()*w4))
                val pishbini = (h1*w5)+ (h2*w6)

                //know error
                val delta = pishbini - t[i].toDouble()

                //updating weights
                val deltaw6 = a*(h2*delta)
                val deltaw5 = a*(h1*delta)
                w6 -= a*(h2*delta)
                w5 -= a*(h1*delta)
                w4 -= a*(y[i].toDouble()*deltaw6)
                w3 -= a*(x[i].toDouble()*deltaw6)
                w2 -= a*(y[i].toDouble()*deltaw5)
                w1 -= a*(x[i].toDouble()*deltaw5)

            }

        }
    }
    fun test (debug:Boolean):Double{
        var wins = 0
        var losses = 0
        for (i in 0 until x.size){
            var computed =compute(x[i].toDouble(),y[i].toDouble())
            var mustbe = t[i].toDouble()
            if (compute(x[i].toDouble(),y[i].toDouble()) == t[i].toDouble())
                wins ++
            else
                losses ++
        }
        val percent = (wins.toDouble() * 100.0)/(wins.toDouble() + losses.toDouble())
        if (debug) println("$wins:$losses = ${percent.toInt()}%")
        return percent
    }
}
