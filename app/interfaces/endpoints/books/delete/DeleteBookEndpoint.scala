package interfaces.endpoints.books.delete

import domain.book.{BookId, BookRepository}
import interfaces.EndpointSyntaxOps
import sttp.model.StatusCode
import sttp.model.StatusCode.NoContent
import sttp.tapir._
import sttp.tapir.server.ServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class DeleteBookEndpoint @Inject() (bookRepository: BookRepository) extends EndpointSyntaxOps {

  private val deleteBookApiEndpoint: PublicEndpoint[Int, Unit, StatusCode, Any] =
    endpoint.delete
      .in(path[Int])
      .out(emptyOutputAs(NoContent))

  val deleteBookApiServerEndpoint: ServerEndpoint[Any, Future] = deleteBookApiEndpoint.serverLogicSuccess { bookId =>
    bookRepository.delete(BookId(bookId))
    NoContent.future
  }
}
