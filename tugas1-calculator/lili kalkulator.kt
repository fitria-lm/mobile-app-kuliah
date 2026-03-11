class Calculator {

    fun kali(a: Double, b: Double): Double {
        return a * b
    }

    fun bagi(a: Double, b: Double): Double {
        return a / b
    }

    fun tambah(a: Double, b: Double): Double {
        return a + b
    }

    fun kurang(a: Double, b: Double): Double {
        return a - b
    }

    fun hitungEkspresi(input: String): Double {

        val angka = mutableListOf<Double>()
        val operator = mutableListOf<Char>()

        var number = ""

        for (c in input) {
            if (c.isDigit() || c == '.') {
                number += c
            } else {
                angka.add(number.toDouble())
                operator.add(c)
                number = ""
            }
        }

        angka.add(number.toDouble())

        var i = 0
        while (i < operator.size) {
            if (operator[i] == '*' || operator[i] == '/') {

                val result = if (operator[i] == '*')
                    kali(angka[i], angka[i + 1])
                else
                    bagi(angka[i], angka[i + 1])

                angka[i] = result
                angka.removeAt(i + 1)
                operator.removeAt(i)

            } else {
                i++
            }
        }

        var hasil = angka[0]

        for (j in operator.indices) {
            hasil = when (operator[j]) {
                '+' -> tambah(hasil, angka[j + 1])
                '-' -> kurang(hasil, angka[j + 1])
                else -> hasil
            }
        }

        return hasil
    }
}

fun main() {

    val kalkulator = Calculator()

    print("Masukkan ekspresi: ")
    val ekspresi = readLine()!!

    val hasil = kalkulator.hitungEkspresi(ekspresi)

    println("Hasil = $hasil")
}