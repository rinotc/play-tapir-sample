package usecase.books.update

import domain.book.BookRepository

import javax.inject.Inject

class UpdateBookInteractor @Inject() (bookRepository: BookRepository) extends UpdateBookUseCase {
  override def handle(input: UpdateBookInput): UpdateBookOutput = {
    bookRepository.findById(input.id) match {
      case None => UpdateBookOutput.BookNotFound(input.id)
      case Some(book) =>
        val updatedBook = book
          .updateTitle(input.title)
          .updatePrice(input.price)
        bookRepository.update(updatedBook)
        UpdateBookOutput.Success(updatedBook)
    }
  }
}
