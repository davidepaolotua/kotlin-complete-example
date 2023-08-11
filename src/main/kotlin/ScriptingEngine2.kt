import org.jetbrains.kotlin.scripting.ide_services.compiler.KJvmReplCompilerWithIdeServices
import java.util.*
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.defaultJvmScriptingHostConfiguration
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object ScriptingEngine2 {
    private val autocompleter = KJvmReplCompilerWithIdeServices(defaultJvmScriptingHostConfiguration)

    // Setup predefined variables (e.g., datasets, projects)
    private val injectedScript: String = """      
        data class MyDataSet2(private val x: String)
        data class MyProject2(private val x: String)
        val x = MyDataSet2("a")
        """.trimIndent() + "\n"

    private val compileConfig = ScriptCompilationConfiguration {
        this.jvm {
            dependenciesFromCurrentContext(unpackJarCollections = true, wholeClasspath = true)
            defaultImports(
                "java.time.*",
                "kotlin.io.path.Path",
                "kotlin.stdlib",
                "kotlin.script.runtime",
                "kotlin.reflect",
            )
        }
        compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
    }

    private fun getSourceCode(script: String, extraScript: String): SourceCode {
        return asSource(extraScript + script)
    }

    private fun asSource(script: String, scriptName: String = "A" + UUID.randomUUID().toString()): SourceCode {
        return script.toScriptSource(scriptName)    // <-- Remove script name here, to make the second run fail...
    }

    suspend fun autocomplete(script: String, position: SourceCode.Position): Outcome {
        val injectedLines = injectedScript.lines().size
        val adjustedPosition = position.copy(line = position.line + injectedLines)
        val sourceCode = getSourceCode(script, injectedScript)
        val result =
            autocompleter.complete(
                sourceCode,
                adjustedPosition,
                compileConfig
            )

        val xxx = result.valueOrThrow()
        val completionVariants = result.valueOrNull()?.toMutableList() ?: mutableListOf()
        val reports = result.reports.map {
            it.copy(
                location = it.location?.copy(
                    start = it.location!!.start.copy(line = it.location!!.start.line - injectedLines),
                    end = it.location!!.end?.copy(line = it.location!!.end!!.line - injectedLines),
                )
            )
        }

        return Outcome(completionVariants, reports)
    }

}
