package domain.book

trait BookRepository {

  def findById(id: BookId): Option[Book]

  def findByISBN(isbn: ISBN): Option[Book]

  def insert(book: Book): Unit

  def update(book: Book): Unit

  def delete(id: BookId): Unit
}
