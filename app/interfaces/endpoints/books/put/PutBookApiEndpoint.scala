package interfaces.endpoints.books.put

import domain.book.BookId
import domain.money.Yen
import interfaces.EndpointSyntaxOps
import io.circe.generic.auto._
import sttp.tapir.generic.auto._
import interfaces.endpoints.ErrorResponse
import interfaces.endpoints.books.BookResponse
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import usecase.books.update.{UpdateBookInput, UpdateBookOutput, UpdateBookUseCase}

import javax.inject.Inject
import scala.concurrent.Future

class PutBookApiEndpoint @Inject() (updateBookUseCase: UpdateBookUseCase) extends EndpointSyntaxOps {

  private case class PutBookRequest(
      title: String,
      price: Int
  )

  private val putBookApiEndpoint: PublicEndpoint[(Int, PutBookRequest), ErrorResponse, BookResponse, Any] =
    endpoint.put
      .in(path[Int])
      .in(jsonBody[PutBookRequest])
      .errorOut(jsonBody[ErrorResponse])
      .out(jsonBody[BookResponse])

  val putBookApiServerEndpoint: ServerEndpoint[Any, Future] = putBookApiEndpoint.serverLogic { case (bookId, request) =>
    val input = UpdateBookInput(
      id = BookId(bookId),
      title = request.title,
      price = Yen(request.price)
    )

    updateBookUseCase.handle(input) match {
      case UpdateBookOutput.BookNotFound(id) =>
        ErrorResponse(
          code = "book.update.notFound",
          description = s"${id.value} is not found"
        ).left.future
      case UpdateBookOutput.Success(book) =>
        BookResponse.from(book).right.future
    }
  }
}
