package interfaces.endpoints.books

import domain.book.Book

case class BookResponse(
    id: Int,
    isbn: String,
    title: String,
    price: Int
)

object BookResponse {
  def from(book: Book): BookResponse = apply(
    book.id.value,
    book.isbn.value,
    book.title,
    book.price.value
  )
}
