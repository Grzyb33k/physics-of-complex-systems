package pl.kamil.exporter

import java.io.File

//import kotlin.collections.mutableMapOf

object CsvExporter {
    fun exportMultipleRowData(results: MutableMap<Double, DoubleArray>, filename: String, formatHeader: Int = 1) {

        val dirName = "output"

        val directory = File(dirName)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, filename)

        val header = results.keys.joinToString(",") { "%.${formatHeader}f".format(it) }

        file.printWriter().use { out ->

            out.println(header)

            val rows = results.values.firstOrNull()?.size ?: 0

            for(i in 0 until rows) {
                val row = results.keys.joinToString(",") { x0 ->
                    val value = results[x0]?.get(i)
                    "%.6f".format(value)
                }
                out.println(row)
            }

        }

    }
}