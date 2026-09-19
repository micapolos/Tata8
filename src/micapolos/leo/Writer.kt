package micapolos.leo

interface Writer {
  fun write(char: Char)
  fun done()

  fun write(string: String) {
    string.forEach { write(it) }
  }
}