import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries

fun shower (x:DoubleArray, y:DoubleArray, t:DoubleArray): MutableMap<String, DoubleArray> {
    val result = mutableMapOf<String,DoubleArray>()
    val x1 = mutableListOf<Double>()
    val y1 = mutableListOf<Double>()
    val x0 = mutableListOf<Double>()
    val y0 = mutableListOf<Double>()
    val xm1 = mutableListOf<Double>()
    val ym1 = mutableListOf<Double>()
    for (i in t.indices){
        when (t[i]){
            1.0 -> {
                x1.add(x[i])
                y1.add(y[i])
            }
            0.0 -> {
                x0.add(x[i])
                y0.add(y[i])
            }
            -1.0 -> {
                xm1.add(x[i])
                ym1.add(y[i])
            }
        }
    }
    result["x1"] = x1.toDoubleArray()
    result["y1"] = y1.toDoubleArray()
    result["x0"] = x0.toDoubleArray()
    result["y0"] = y0.toDoubleArray()
    result["xm1"] = xm1.toDoubleArray()
    result["ym1"] = ym1.toDoubleArray()
    return result
}
fun show (x:DoubleArray, y:DoubleArray, t:DoubleArray, w1:Double, w2:Double, b:Double , range:DoubleArray){
    val data = shower(x, y, t)
    val chart = XYChartBuilder().width(1280).height(720).title("Chart").xAxisTitle("X").yAxisTitle("Y").build()
    if (data["x1"]!!.isNotEmpty() && data["y1"]!!.isNotEmpty())
        chart.addSeries("1", data["x1"],data["y1"])
    if (data["x0"]!!.isNotEmpty() && data["y0"]!!.isNotEmpty())
        chart.addSeries("0", data["x0"],data["y0"])
    if (data["xm1"]!!.isNotEmpty() && data["ym1"]!!.isNotEmpty())
        chart.addSeries("-1", data["xm1"],data["ym1"])
    val line = lineCreator(w1,w2,b,range)
    chart.addSeries("w", line["x"],line["y"])
    chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    SwingWrapper(chart).displayChart()
}
fun showMlp (x:DoubleArray, y:DoubleArray, t:DoubleArray,computed:Map<String,DoubleArray>){
    val data = shower(x, y, t)
    val chart = XYChartBuilder().width(1280).height(720).title("Chart").xAxisTitle("X").yAxisTitle("Y").build()
    if (data["x1"]!!.isNotEmpty() && data["y1"]!!.isNotEmpty())
        chart.addSeries("1", data["x1"],data["y1"])
    if (data["x0"]!!.isNotEmpty() && data["y0"]!!.isNotEmpty())
        chart.addSeries("0", data["x0"],data["y0"])
    if (data["xm1"]!!.isNotEmpty() && data["ym1"]!!.isNotEmpty())
        chart.addSeries("-1", data["xm1"],data["ym1"])
    val line = computed
    chart.addSeries("w", line["x"],line["y"])
    chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    SwingWrapper(chart).displayChart()
}
fun lineCreatorMlp(computed:DoubleArray,range:DoubleArray):Map<String,DoubleArray>{
    val x = mutableListOf<Double>()
    val y = mutableListOf<Double>()
    var i = range[0]
    while(i < range[1]){
        x.add(i)
        y.add(computed[i.toInt()])
        i += 0.001
    }
    return mapOf("x" to x.toDoubleArray(),"y" to y.toDoubleArray())
}
fun lineCreator(w1:Double,w2:Double,b:Double,range:DoubleArray):Map<String,DoubleArray>{
    val x = mutableListOf<Double>()
    val y = mutableListOf<Double>()
    var i = range[0]
    while(i < range[1]){
        x.add(i)
        y.add(((-w1*i)-b)/w2)
        i += 0.001
    }
    return mapOf("x" to x.toDoubleArray(),"y" to y.toDoubleArray())
}
