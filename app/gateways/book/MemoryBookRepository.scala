package gateways.book

import domain.book.{Book, BookId, BookRepository, ISBN}

import scala.collection.mutable

object MemoryBookRepository extends BookRepository {

  private val books: mutable.ArrayBuffer[Book] = mutable.ArrayBuffer.empty

  override def findById(id: BookId): Option[Book] = books.find(_.id == id)

  override def findByISBN(isbn: ISBN): Option[Book] = books.find(_.isbn == isbn)

  override def insert(book: Book): Unit = {
    require(!books.exists(_.isbn == book.isbn))
    books.addOne(book)
  }

  override def update(book: Book): Unit = {
    require {
      !books.exists { b => b.isbn == book.isbn && b.id != book.id }
    }
    delete(book.id)
    insert(book)
  }

  override def delete(id: BookId): Unit = {
    books.remove(books.view.indexWhere(_.id == id))
  }
}
