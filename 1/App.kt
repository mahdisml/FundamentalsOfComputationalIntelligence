
fun main (){
    showAllPossibleAnswers ()
}

fun showAllPossibleAnswers (){
    for(i in 0..11111){
        var correct:Boolean = true
        for (item:Char in i.toString()){
            if (item.toString().toInt() > 1){
                correct = false
            }
        }
        if (correct){
            var data : String = i.toString()
            if (i.toString().length < 5){
                val temp :String = "0"
                var add : Int = 5 - i.toString().length
                while (add != 0 ){
                    data = temp + data
                    add --
                }
            }
            print(data)
            print (":")
            print(Cell(data).result)
            println()
        }
    }
}