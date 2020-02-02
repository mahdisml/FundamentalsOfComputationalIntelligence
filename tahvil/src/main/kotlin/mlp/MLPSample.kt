package mlp

import mlp.networks.BPNN
import mlp.networks.MLP
import onelayer.networks.Adaline
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import show
import showMlp
import java.io.File
import kotlin.random.Random

fun main (){
    val x = mutableListOf<Number>()
    val y = mutableListOf<Number>()
    val t = mutableListOf<Number>()
    val data:List<String> = File("mlp.txt").readText().split(" ","\n")
    var i = 0
    while (i < data.size - 1){
        x.add(data[i].toDouble())
        y.add(data[i+1].toDouble())
        if (data[i+2].toDouble() == 0.0) {
            t.add(-1.0)
        }else{
            t.add(data[i + 2].toDouble())
        }

        i += 3
    }
    val mlp = MLP(mutableListOf(x,y,t),0.1)
    mlp.test(true)

    var m = -10.0
    var n = -10.0
    val xLine = mutableListOf<Double>()
    val yLine = mutableListOf<Double>()
    while (m<10) {
        n = -10.0
        while (n<10) {
            var used = mlp.use(m,n)
            if (used < 0.5 && used > -0.5) {
                yLine.add(n)
                xLine.add(m)
            }
            n += 0.1
        }
        m += 0.1
    }
    val computed:Map<String,DoubleArray> = mapOf("x" to xLine.toDoubleArray(),"y" to yLine.toDoubleArray())

    showMlp(x.map { it.toDouble() }.toDoubleArray(),
        y.map { it.toDouble() }.toDoubleArray(),
        t.map { it.toDouble() }.toDoubleArray(),
        computed)


}