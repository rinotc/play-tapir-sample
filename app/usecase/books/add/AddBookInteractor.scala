package usecase.books.add

import domain.book.{Book, BookRepository}

import javax.inject.Inject

class AddBookInteractor @Inject() (bookRepository: BookRepository) extends AddBookUseCase {
  override def handle(input: AddBookInput): AddBookOutput = {
    bookRepository.findByISBN(input.isbn) match {
      case Some(value) => AddBookOutput.ConflictISBN(input.isbn)
      case None =>
        val newBook = Book.create(input.isbn, input.title, input.price)
        bookRepository.insert(newBook)
        AddBookOutput.Success(newBook)
    }
  }
}
