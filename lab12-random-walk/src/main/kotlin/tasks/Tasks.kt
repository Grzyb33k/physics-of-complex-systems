package pl.kamil.tasks
import pl.kamil.randomwalk.RandWalkEngine
import java.io.File
import kotlin.math.sqrt

object Tasks {

    fun task1a() {

        val dirName = "output"
        val fileName = "zad1a.csv"
        val directory = File(dirName)
        if (!directory.exists()) { directory.mkdirs() }

        val out = File(dirName, fileName).printWriter()

        val nRuns = 5
        val nMax = 200

        out.println("run,step,distance")

        for (i in 0 until nRuns) {
            val rw = RandWalkEngine(1, 999, doubleArrayOf(0.5))

            val d = rw.proceedRandomWalk(nMax, debug = false, calculateSimpleDistance = true)

            d.forEachIndexed { index, element -> out.println("$i,$index,$element") }

        }

        out.close()

    }

    fun task1b() {

        val dirName = "output"
        val fileName = "zad1b.csv"
        val directory = File(dirName)
        if (!directory.exists()) { directory.mkdirs() }

        val out = File(dirName, fileName).printWriter()

        val nMax = 20
        val nEngines = 1_000_000

        val engineArr = Array(nEngines) { RandWalkEngine(1, 999, doubleArrayOf(0.5)) }

        for (engine in engineArr) {
            val dLast = engine.proceedRandomWalk(nMax, calculateSimpleDistance = true).last()
            out.println(dLast)
        }

        out.close()

    }

    fun task2() {

        val gridSize = 10
        val nMax = 1_000_000

        val dirName = "output"
        val fileName = "zad2.csv"
        val directory = File(dirName)
        if (!directory.exists()) { directory.mkdirs() }

        val out = File(dirName, fileName).printWriter()

        val engine = RandWalkEngine(2, gridSize, pArr = doubleArrayOf(0.5, 0.5))

        val result = engine.proceedRandomWalkCoordinates(nMax)

        out.println("x,y")

        result.forEach { out.println("${it.x},${it.y}") }

        out.close()
    }

    fun task3() {
        val nMaxArr = intArrayOf(10, 100, 200)

        for (nMax in nMaxArr) {
            var dMean = 0.0
            var count = 0.0
            var m2 = 0.0

            val repeat = nMax * 100

            val engineArr = Array(repeat) {RandWalkEngine(2, nMax, doubleArrayOf(0.5, 0.5))}

            for (engine in engineArr) {
                val dLast = engine.proceedRandomWalk(nMax, calculateSimpleDistance = false, noGrid = true).last()

                count++
                val delta = dLast - dMean
                dMean += delta / count
                m2 += delta * (dLast - dMean)

            }

            val variance = m2 / (count - 1)
            val std = sqrt(variance)

            println("N = $nMax | d_exp = %.2f | d_mean = %.2f | std = %.2f | repeat = $repeat"
                .format(
                sqrt(nMax.toDouble()),
                dMean,
                std),
            )

        }

    }

}