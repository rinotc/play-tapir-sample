package domain.book

import domain.money.Yen

final class Book(
    val id: BookId,
    val isbn: ISBN,
    val title: String,
    val price: Yen
) {

  override def equals(other: Any): Boolean = other match {
    case that: Book => id == that.id
    case _          => false
  }

  override def hashCode(): Int = 31 * id.##
}

object Book {

  private var _id = 0

  private def generateId = {
    _id += 1
    BookId(_id)
  }

  def create(isbn: ISBN, title: String, price: Yen): Book = {
    new Book(generateId, isbn, title, price)
  }
}
