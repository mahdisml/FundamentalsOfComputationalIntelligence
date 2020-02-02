package onelayer

import onelayer.networks.Perceptron
import show

fun main (){
    val x = mutableListOf<Number>(0.67044,-0.35508,0.10452,0.95826,0.098617,-0.33915,0.23894,0.27873,0.51302,0.1722,-0.01531,0.38949,0.94547,-0.34449,0.67561,0.47814,0.90835,-0.93615,-0.28626,0.32531)
    val y = mutableListOf<Number>(-0.437,-0.53923,0.42226,0.24915,0.18122,0.32088,-0.90489,-0.30243,-0.097319,-0.51819,0.43009,0.71236,-0.43698,0.4621,-0.72447,0.67345,-0.7228,0.17642,-0.26769,0.61352)
    val t = mutableListOf<Number>(1,-1,1,1,1,1,-1,-1,1,-1,1,1,1,1,-1,1,-1,-1,-1,1)
    val perceptron = Perceptron(mutableListOf(x,y,t),0.2)
    perceptron.learn(1,false)
    perceptron.print()
    perceptron.test(true)
    show(x.map { it.toDouble() }.toDoubleArray(),
        y.map { it.toDouble() }.toDoubleArray(),
        t.map { it.toDouble() }.toDoubleArray(),
        perceptron.w[0],perceptron.w[1],perceptron.b,doubleArrayOf(-1.0,+1.0))
}
