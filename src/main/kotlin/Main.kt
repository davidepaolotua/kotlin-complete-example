fun main() {
    val script = "x."
    val line = 0
    val col = 3

    println("Starting Working One, creation of classes is included inside the script every single time")

    val rightResult = Autocompleter.getSuggestions2(script, line, col)
    rightResult.inspect()

    println()
    println()
    println()
//    println() //    println("Starting failing one, creation of classes is done in an imported script")
//
//    val result = Autocompleter.getSuggestions(script, line, col)
//    result.inspect()

    println("Starting failing one, calling the same script with un updated definition - keeping the same file name crashes")
    println("It fails when you remove scriptName at ScriptingEngine2, line ")
    val script2 = "x.cop"   //stands for x.copy(...
    val line2 = 0
    val col2 = 6
    val crashingResult = Autocompleter.getSuggestions2(script2, line2, col2)
    crashingResult.inspect()


}




