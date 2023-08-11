import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.SourceCodeCompletionVariant

data class Outcome(val completions: List<SourceCodeCompletionVariant>, val reports: List<ScriptDiagnostic>) {
    fun inspect() {
        println()
        println("***************** Completions *****************")
        completions.forEach {
            println(it.displayText)
        }
        println("***************** Diagnostics *****************")
        reports.forEach {
            println(it.message)
        }

    }



}
