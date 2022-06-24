package usecase.books.update

import domain.book.{Book, BookId}
import usecase.Output

sealed abstract class UpdateBookOutput extends Output

object UpdateBookOutput {

  case class Success(book: Book) extends UpdateBookOutput

  case class BookNotFound(id: BookId) extends UpdateBookOutput
}
