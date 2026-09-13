package pl.kamil.tasks
import pl.kamil.kuramoto.KuramotoGenerator
import pl.kamil.kuramoto.KuramotoModel

import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.cos

import java.io.File

object Tasks {

    private fun calculateR(theta: DoubleArray): Double {
        val n = theta.size
        val r: Double = 1.0 / n * sqrt(
            theta.sumOf{ cos(it) }.pow(2) +
                theta.sumOf{ sin(it) }.pow(2)
        )

        return r
    }

    fun task1() {

        val dirName = "output"
        val fileName = "zad1.csv"
        val directory = File(dirName)
        if (!directory.exists()) {directory.mkdirs()}



        val out = File(dirName, fileName).printWriter()

        val nArr = listOf(10, 20, 50)

        val kArr = (25..500 step 50).map { it * 0.01}

        val dt = 1.0
        val tMax = 100.0

        out.println("n,k,t,r,theta_i,theta_val")

        for (n in nArr) {
            for (k in kArr) {

                val omegas = KuramotoGenerator.generateRandomOmegasNormal(n, sigma=0.5)

                val thetasInit = KuramotoGenerator.generateRandomThetasUniform(n)

                val model = KuramotoModel(n, k, omegas)

//                val integrator = ClassicalRungeKuttaIntegrator(dt)

                val integrator = DormandPrince54Integrator(1.0e-8, 1.0e-1, 1.0e-5, 1.0e-5)

                val theta = thetasInit.clone()

                var t = 0.0

                while (t <= tMax) {

                    val r = calculateR(theta)

                    for (i in 0 until n) {
                        out.println("$n,%.2f,%.3f,%.4f,$i,%.4f".format(k, t, r, theta[i]))
                    }

                    val tNext = t + dt

                    integrator.integrate(model, t, theta, tNext, theta)

                    t = tNext
                }


            }
        }

        out.close()

    }

    fun task2() {

        val dirName = "output"
        val fileName = "zad2.csv"
        val directory = File(dirName)
        if (!directory.exists()) {directory.mkdirs()}



        val out = File(dirName, fileName).printWriter()

        val nArr = listOf(10, 20, 50)

        val kArr = (25..500 step 50).map { it * 0.01}

        val dt = 0.1
        val tMax = 100.0

        out.println("n,k,r_avg")

        for (n in nArr) {

            for (k in kArr) {

                val nRep = 10

                var rSum = 0.0

                for (rep in 0 until nRep) {

                    var t = 0.0

                    val omegas = KuramotoGenerator.generateRandomOmegasNormal(n, sigma=0.5)

                    val thetasInit = KuramotoGenerator.generateRandomThetasUniform(n)

                    val model = KuramotoModel(n, k, omegas)

//                    val integrator = ClassicalRungeKuttaIntegrator(dt)
                    val integrator = DormandPrince54Integrator(1.0e-8, 1.0e-1, 1.0e-5, 1.0e-5)

                    val theta = thetasInit.clone()


                    while (t <= tMax) {
                        val tNext = t + dt
                        integrator.integrate(model, t, theta, tNext, theta)
                        t = tNext
                    }

                    val r = calculateR(theta)

                    rSum += r
                }

                val rAvg = rSum / nRep

                out.println("$n,%.2f,%.4f".format(k, rAvg))

            }
        }

        out.close()

    }

    fun task3() {
        val dirName = "output"
        val fileName = "zad3.csv"
        val directory = File(dirName)
        if (!directory.exists()) {directory.mkdirs()}



        val out = File(dirName, fileName).printWriter()

        val nArr = listOf(50)

        val kArr = listOf(0.01, 0.8, 2.0)

        val dt = 1.0
        val tMax = 100.0

//        out.println("n, k, t, r, theta_i, theta_val")

        out.println("n,k,run,t,r")

        for (n in nArr) {
            for (k in kArr) {


                for (run in 0..2) {
                    val omegas = KuramotoGenerator.generateRandomOmegasNormal(n, sigma=0.5)

                    val thetasInit = KuramotoGenerator.generateTask3InitialThetas(n)

                    val model = KuramotoModel(n, k, omegas)

//                val integrator = ClassicalRungeKuttaIntegrator(dt)
                    val integrator = DormandPrince54Integrator(1.0e-8, 1.0e-1, 1.0e-5, 1.0e-5)

                    val theta = thetasInit.clone()

                    var t = 0.0

                    while (t <= tMax) {

                        val r = calculateR(theta)

//                    for (i in 0 until n) {
//                        out.println("$n, %.2f, %.2f, %.4f, $i, %.4f".format(k, t, r, theta[i]))
//                    }

                        out.println("$n,%.2f,$run,%.2f,%.4f".format(k, t, r))

                        val tNext = t + dt

                        integrator.integrate(model, t, theta, tNext, theta)

                        t = tNext
                    }
                }



                println("End for n = $n, k = $k")

            }
        }

        out.close()
    }

}