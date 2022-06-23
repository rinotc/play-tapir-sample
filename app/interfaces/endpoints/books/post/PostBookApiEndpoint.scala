package interfaces.endpoints.books.post

import domain.book.ISBN
import domain.money.Yen
import interfaces.endpoints.ErrorResponse
import io.circe.generic.auto._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.{PublicEndpoint, endpoint}
import usecase.books.add.{AddBookInput, AddBookOutput, AddBookUseCase}

import javax.inject.Inject
import scala.concurrent.Future

class PostBookApiEndpoint @Inject() (addBookUseCase: AddBookUseCase) {

  implicit class FutureOps[T](value: T) {
    def future: Future[T] = Future.successful(value)
  }

  private case class PostBookRequest(
      isbn: String,
      title: String,
      price: Int
  )

  private case class PostBookResponse(
      id: Int,
      isbn: String,
      title: String,
      price: Int
  )

  private val postBookApiEndpoint: PublicEndpoint[PostBookRequest, ErrorResponse, PostBookResponse, Any] = endpoint.post
    .in(jsonBody[PostBookRequest])
    .errorOut(jsonBody[ErrorResponse])
    .out(jsonBody[PostBookResponse])

  val postBookApiServerEndpoint: ServerEndpoint[Any, Future] = postBookApiEndpoint.serverLogic { request =>
    val input = AddBookInput(
      isbn = ISBN(request.isbn),
      title = request.title,
      price = Yen(request.price)
    )

    addBookUseCase.handle(input) match {
      case AddBookOutput.ConflictISBN(isbn) =>
        Left(
          ErrorResponse(
            code = "books.add.conflictISBN",
            description = s"${isbn.value} is already exists."
          )
        ).future
      case AddBookOutput.Success(newBook) =>
        val response = PostBookResponse(
          newBook.id.value,
          newBook.isbn.value,
          newBook.title,
          newBook.price.value
        )
        Right(response).future
    }
  }
}
