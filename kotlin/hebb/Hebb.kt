class Hebb (col:Array<DoubleArray>){
    var w:DoubleArray
    init {
        w = DoubleArray(col[0].size){0.0}
        var i:Int = 0
        while (i != col.size) {
            var j: Int = 0
            while (j != col[i].size) {
                if (j == col[i].size - 1) {
                    w[j] = (1 * col[i][col[i].size - 1]) + w[j]
                } else {
                    w[j] = (col[i][j] * col[i][col[i].size - 1]) + w[j]
                }

                j++
            }
            println(" ${w[0]},${w[1]},${w[2]}")

            i++
        }
    }


}