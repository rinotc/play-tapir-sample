package usecase.books.add

import domain.book.{Book, ISBN}
import usecase.Output

sealed abstract class AddBookOutput extends Output

object AddBookOutput {

  final case class Success(newBook: Book) extends AddBookOutput

  final case class ConflictISBN(isbn: ISBN) extends AddBookOutput
}
