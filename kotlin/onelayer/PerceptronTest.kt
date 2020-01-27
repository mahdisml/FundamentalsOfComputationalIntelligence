package onelayer

import onelayer.networks.Perceptron
import show

fun main (){
    val x = mutableListOf<Number>(+1,+1,-1,-1)
    val y = mutableListOf<Number>(+1,-1,+1,-1)
    val t = mutableListOf<Number>(+1,-1,-1,-1)
    val perceptron = Perceptron(mutableListOf(x,y,t),0.2)
    perceptron.learn(1,false)
    perceptron.print()
    perceptron.test(true)
    show(x.map { it.toDouble() }.toDoubleArray(),
        y.map { it.toDouble() }.toDoubleArray(),
        t.map { it.toDouble() }.toDoubleArray(),
        perceptron.w[0],perceptron.w[1],perceptron.b,doubleArrayOf(-1.0,+1.0))
}
