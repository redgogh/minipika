package test.compare

class QueryCompareTest {

  static void main(String[] args) {
    println eq("username")
  }

  static def eq(column) {
    "and $column = ?"
  }

}
