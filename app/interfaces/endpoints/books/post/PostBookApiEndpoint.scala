package interfaces.endpoints.books.post

import domain.book.ISBN
import domain.money.Yen
import interfaces.EndpointSyntaxOps
import interfaces.endpoints.ErrorResponse
import interfaces.endpoints.books.BookResponse
import io.circe.generic.auto._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.{PublicEndpoint, endpoint}
import usecase.books.add.{AddBookInput, AddBookOutput, AddBookUseCase}

import javax.inject.Inject
import scala.concurrent.Future

class PostBookApiEndpoint @Inject() (addBookUseCase: AddBookUseCase) extends EndpointSyntaxOps {

  private case class PostBookRequest(
      isbn: String,
      title: String,
      price: Int
  )

  private val postBookApiEndpoint: PublicEndpoint[PostBookRequest, ErrorResponse, BookResponse, Any] = endpoint.post
    .in(jsonBody[PostBookRequest])
    .errorOut(jsonBody[ErrorResponse])
    .out(jsonBody[BookResponse])

  val postBookApiServerEndpoint: ServerEndpoint[Any, Future] = postBookApiEndpoint.serverLogic { request =>
    val input = AddBookInput(
      isbn = ISBN(request.isbn),
      title = request.title,
      price = Yen(request.price)
    )

    addBookUseCase.handle(input) match {
      case AddBookOutput.ConflictISBN(isbn) =>
        ErrorResponse(
          code = "books.add.conflictISBN",
          description = s"${isbn.value} is already exists."
        ).left.future
      case AddBookOutput.Success(newBook) =>
        BookResponse.from(newBook).right.future
    }
  }
}
