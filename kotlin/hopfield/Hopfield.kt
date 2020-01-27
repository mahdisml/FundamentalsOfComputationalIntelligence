package hopfield

class Hopfield(val ex: MutableList<Int> ,val o: MutableList<Int> ,val plus: MutableList<Int> ) {
    var w = mutableListOf<MutableList<Int>>()
    init {
        w = jam (
            zarbDakheli(taranahade(ex),ex),
            zarbDakheli(taranahade(o),o),
            zarbDakheli(taranahade(plus),plus)
        )
        for (i in 0 until w.size) {
            for (j in 0 until w.size) {
                if (i == j)
                    w[i][j] = 0
            }
        }

    }

    private fun yin (i:Int, data: MutableList<Int>) : Int{
            var result = data [i]
            for (j in 0 until  data.size){
                result += data[j]*w[i][j]
            }
        return result
    }
    private fun compute (i:Int, data: MutableList<Int> ) : Int {
        return when {
            yin(i,data) > 0 -> 1
            yin(i,data) < 0 -> 0
            else -> data[i]
        }
    }
    private fun check (i:Int, data: MutableList<Int> ):MutableList<Int>{
        val newData = data.toMutableList()
        newData [i] = compute(i,data)
        return newData

    }
    fun detect (mutableList: MutableList<Int>){
        var data = mutableList.toMutableList()
        for (x in 0 until data.size){ // must be random
            data = check (x,data)
        }
        this.print(data)
    }

    private fun taranahade (mutableList: MutableList<Int>):MutableList<Int>{
        return mutableList
    }
    private fun zarbDakheli (x: MutableList<Int>,y: MutableList<Int>):MutableList<MutableList<Int>>{
        val data = MutableList(x.size){MutableList(x.size){0} }
        for (i in 0 until x.size) {
            for (j in 0 until y.size) {
                data [i][j] = x[i]*y[j]
            }
        }
        return data
    }
    private fun jam (x1:MutableList<MutableList<Int>>,
                     x2:MutableList<MutableList<Int>>,
                     x3:MutableList<MutableList<Int>>):MutableList<MutableList<Int>>{
        val data = x1.toMutableList()
        for (i in 0 until x1.size)
            for (j in 0 until x1.size)
                data[i][j] += x2[i][j] + x3[i][j]
        return data

    }
    fun printWeights (){
        println()
        for (i in 0 until w.size) {
            for (j in 0 until w.size) {
                print(" ${w[i][j]} ")
            }
            println()
        }
    }
    private fun print (x: MutableList<Int>){
        println()
        for (i in 0 until x.size){
            if (i%5 == 0)
                println()
            print(x[i])

        }
    }

}