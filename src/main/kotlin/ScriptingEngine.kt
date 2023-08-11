import org.jetbrains.kotlin.scripting.ide_services.compiler.KJvmReplCompilerWithIdeServices
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.defaultJvmScriptingHostConfiguration
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object ScriptingEngine {
    private val autocompleter = KJvmReplCompilerWithIdeServices(defaultJvmScriptingHostConfiguration)

    // Setup predefined variables (e.g., datasets, projects)
    private val injectedScript: String = """      
        class MyDataSet(private val x: String)
        class MyProject(private val x: String)
        val x = MyDataSet("a")
        """.trimIndent() + "\n"

    private val compileConfig = ScriptCompilationConfiguration {
        jvm {
            dependenciesFromCurrentContext(unpackJarCollections = true, wholeClasspath = true)
            defaultImports(
                "java.time.*",
                "kotlin.io.path.Path",
                "kotlin.stdlib",
                "kotlin.script.runtime",
                "kotlin.reflect",
            )
        }
        importScripts(injectedScript.toScriptSource("injected_script.kts"))
        compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
    }

    suspend fun autocomplete(script: String, position: SourceCode.Position): Outcome {
        val sourceCode = script.toScriptSource()
        val result =
            autocompleter.complete(
                sourceCode,
                position,
                compileConfig
            )

        val xxx = result.valueOrThrow() //added to get the stack trace
        val completionVariants = result.valueOrNull()?.toMutableList() ?: mutableListOf()
        val reports = result.reports
        return Outcome(completionVariants, reports)
    }
}

