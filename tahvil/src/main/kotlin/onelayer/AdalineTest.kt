package onelayer

import onelayer.networks.Adaline
import show

fun main (){
    val x = mutableListOf<Number>(+1,+1,-1,-1)
    val y = mutableListOf<Number>(+1,-1,+1,-1)
    val t = mutableListOf<Number>(+1,-1,-1,-1)
    val adaline = Adaline(mutableListOf(x,y,t))
    adaline.learn(0.1,0.1,100,false)
    adaline.print()
    adaline.test(true)
    show(x.map { it.toDouble() }.toDoubleArray(),
        y.map { it.toDouble() }.toDoubleArray(),
        t.map { it.toDouble() }.toDoubleArray(),
        adaline.w[0],adaline.w[1],adaline.b,doubleArrayOf(-1.0,+1.0))
}