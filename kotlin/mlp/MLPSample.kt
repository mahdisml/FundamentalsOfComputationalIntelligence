package mlp

import mlp.networks.BPNN
import onelayer.networks.Adaline
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import show
import java.io.File

fun main (){
    val x = mutableListOf<Number>()
    val y = mutableListOf<Number>()
    val t = mutableListOf<Number>()
    val data:List<String> = File("mlp.txt").readText().split(" ","\n")
    var i = 0
    while (i < data.size - 1){
        x.add(data[i].toDouble())
        y.add(data[i+1].toDouble())
        t.add(data[i+2].toDouble())

        i += 3
    }
    val bpnn = BPNN(mutableListOf(x,y,t))
    bpnn.learn(0.001,70.0,true)

    show(x.map { it.toDouble() }.toDoubleArray(),
        y.map { it.toDouble() }.toDoubleArray(),
        t.map { it.toDouble() }.toDoubleArray(),
        bpnn.w1,bpnn.w2,bpnn.w3,doubleArrayOf(-10.0,+10.0))
}