package usecase.books.update

import domain.book.BookId
import domain.money.Yen
import usecase.Input

case class UpdateBookInput(id: BookId, title: String, price: Yen) extends Input[UpdateBookOutput]
