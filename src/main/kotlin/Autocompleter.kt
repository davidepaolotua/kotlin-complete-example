import kotlinx.coroutines.runBlocking
import kotlin.script.experimental.api.SourceCode

object Autocompleter {

    fun getSuggestions(script: String, line: Int, col: Int): Outcome {
        val position = SourceCode.Position(line, col)
        return runBlocking {
            ScriptingEngine.autocomplete(script, position)
        }
    }

    fun getSuggestions2(script: String, line: Int, col: Int): Outcome {
        val position = SourceCode.Position(line, col)
        return runBlocking {
            ScriptingEngine2.autocomplete(script, position)
        }
    }

}
