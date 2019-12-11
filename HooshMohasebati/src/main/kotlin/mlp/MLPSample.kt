package mlp

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder

fun main (){
    val chart: XYChart = XYChartBuilder().width(1280).height(720).title("MLP").xAxisTitle("X").yAxisTitle("Y").build()
    chart.addSeries("itsme2", doubleArrayOf(1.2))
    chart.addSeries("itsme", doubleArrayOf(2.0))
    SwingWrapper(chart).displayChart()
}