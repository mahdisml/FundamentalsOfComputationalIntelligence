class Cell(x:String) {

    var result:Int
    var teta:Double = 1.5

    init {
        val sum:Int = (x[0].toString().toInt()*-2)+(x[1].toString().toInt())+(x[2].toString().toInt()*-3)+(x[3].toString().toInt()*2)+(-x[4].toString().toInt())
        result = if (sum > teta){
            1
        }else {
            0
        }
    }

}
