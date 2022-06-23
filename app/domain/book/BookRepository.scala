package domain.book

trait BookRepository {

  def list(): Seq[Book]

  def findById(id: BookId): Option[Book]

  def findByISBN(isbn: ISBN): Option[Book]

  def insert(book: Book): Unit

  def update(book: Book): Unit

  def delete(id: BookId): Unit
}
