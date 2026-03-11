import java.util.*

// 1. Konsep OOP: Menggunakan Interface untuk mendefinisikan Operasi
interface MathOperation {
    val precedence: Int
    fun apply(a: Double, b: Double): Double
}

// Implementasi Operasi
class Add : MathOperation {
    override val precedence = 1
    override fun apply(a: Double, b: Double) = a + b
}

class Subtract : MathOperation {
    override val precedence = 1
    override fun apply(a: Double, b: Double) = a - b
}

class Multiply : MathOperation {
    override val precedence = 2
    override fun apply(a: Double, b: Double) = a * b
}

class Divide : MathOperation {
    override val precedence = 2
    override fun apply(a: Double, b: Double): Double {
        if (b == 0.0) throw ArithmeticException("Pembagian dengan nol tidak diperbolehkan")
        return a / b
    }
}

// 2. Class Kalkulator sebagai Orchestrator
class KabatakuCalculator {
    private val operations = mapOf(
        "+" to Add(),
        "-" to Subtract(),
        "*" to Multiply(),
        "/" to Divide()
    )

    fun calculate(expression: String): Double {
        // Membersihkan spasi dan memisahkan angka & operator menggunakan regex
        val tokens = expression.replace(" ", "").split(Regex("(?<=[-+*/])|(?=[-+*/])"))
        
        val values = Stack<Double>()
        val ops = Stack<String>()

        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]

            when {
                token.isDouble() -> {
                    values.push(token.toDouble())
                }
                token in operations.keys -> {
                    // Cek Precedence (Kabataku logic)
                    while (ops.isNotEmpty() && hasPrecedence(token, ops.peek())) {
                        values.push(processOperation(ops.pop(), values.pop(), values.pop()))
                    }
                    ops.push(token)
                }
            }
            i++
        }

        // Selesaikan sisa operasi di stack
        while (ops.isNotEmpty()) {
            values.push(processOperation(ops.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun hasPrecedence(op1: String, op2: String): Boolean {
        val p1 = operations[op1]?.precedence ?: 0
        val p2 = operations[op2]?.precedence ?: 0
        return p2 >= p1
    }

    private fun processOperation(op: String, b: Double, a: Double): Double {
        return operations[op]?.apply(a, b) ?: 0.0
    }

    private fun String.isDouble(): Boolean = this.toDoubleOrNull() != null
}

// Main function untuk menjalankan program
fun main() {
    val calculator = KabatakuCalculator()
    val scanner = Scanner(System.`in`)

    println("=== Kalkulator Kabataku Kotlin ===")
    print("Masukkan operasi matematika: ")
    val input = scanner.nextLine()

    try {
        val result = calculator.calculate(input)
        // Format output: jika desimalnya .0, tampilkan sebagai angka bulat
        val formattedResult = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        println("Hasil perhitungan: $formattedResult")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}