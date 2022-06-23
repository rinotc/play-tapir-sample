package usecase.books.add

import domain.book.ISBN
import domain.money.Yen
import usecase.Input

case class AddBookInput(
    isbn: ISBN,
    title: String,
    price: Yen
) extends Input[AddBookOutput]
