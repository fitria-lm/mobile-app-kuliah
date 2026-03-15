fun main() {

    println("Kalkulator Multi Operasi")
    print("Masukkan soal (contoh: 5+3*2-8/4): ")

    val inputsoal = readLine()!!

    val daftarangka = mutableListOf<Double>()
    val daftaroperator = mutableListOf<Char>()

    var number = ""

    for (char in inputsoal) {
        if (char.isDigit() || char == '.') {
            number += char
        } else {
            daftarangka.add(number.toDouble())
            daftaroperator.add(char)
            number = ""
        }
    }

    daftarangka.add(number.toDouble())

    // Prioritas * dan /
    var i = 0
    while (i < daftaroperator.size) {
        if (daftaroperator[i] == '*' || daftaroperator[i] == '/') {

            val result = if (daftaroperator[i] == '*')
                daftarangka[i] * daftarangka[i + 1]
            else
                daftarangka[i] / daftarangka[i + 1]

            daftarangka[i] = result
            daftarangka.removeAt(i + 1)
            daftaroperator.removeAt(i)

        } else {
            i++
        }
    }

    // Operasi + dan -
    var result = daftarangka[0]

    for (j in daftaroperator.indices) {
        result = when (daftaroperator[j]) {
            '+' -> result + daftarangka[j + 1]
            '-' -> result - daftarangka[j + 1]
            else -> result
        }
    }

    println("Hasil akhir: $result")
}