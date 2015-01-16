object Exercise_10_3_2 {

  case class Book(title: String, authors: String*)

  def main(args: Array[String]) {
    val books: List[Book] =
      List(
        Book(
          "Structure and Interpretation of Computer Programs",
          "Abelson, Harold", "Sussman, Gerald J."
        ),
        Book(
          "Principles of Compiler Design",
          "Aho, Alfred", "Ullman, Jeffrey"
        ),
        Book(
          "Programming in Modula-2",
          "Wirth, Niklaus"
        ),
        Book(
          "Elements of ML Programming",
          "Ullman, Jeffrey"
        ),
        Book(
          "The Java Language Specification", "Gosling, James",
          "Joy, Bill", "Steele, Guy", "Bracha, Gilad"
        )
      )

    val titles1 = for (b <- books; a <- b.authors if a startsWith "Ul") yield b.title 
    val titles2 = for (b <- books if (b.title indexOf "Program") >= 0) yield b.title

//    assert(titles1.length == 0)
    println (titles1.length +" "+ titles1.toString())
    assert(titles2.length == 3)
    println (titles2.length )

    def filter(books: List[Book], cond: Book => Boolean): List[String] =
      books.filter(cond(_)).foldLeft(List[String]()) {
        (titles, book) => book.title :: titles
      }

    val titles1a = filter(books,
      b => b.authors.filter(_.startsWith("Bird")).length > 0)
    val titles2a = filter(books,
      b => (b.title indexOf "Program") >= 0)

    assert(titles1 == titles1a)
    assert(titles2 == titles2a.reverse)
  }
}