package interfaces.endpoints.books.list

import io.circe.generic.auto._
import sttp.tapir.generic.auto._
import domain.book.BookRepository
import interfaces.EndpointSyntaxOps
import interfaces.endpoints.books.BookResponse
import sttp.tapir.{PublicEndpoint, endpoint}
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class ListBooksApiEndpoint @Inject() (bookRepository: BookRepository) extends EndpointSyntaxOps {

  private val listBooksApiEndpoint: PublicEndpoint[Unit, Unit, Seq[BookResponse], Any] = endpoint.get
    .out(jsonBody[Seq[BookResponse]])

  val listBooksApiServerEndpoint: ServerEndpoint[Any, Future] = listBooksApiEndpoint.serverLogicSuccess { _ =>
    val books = bookRepository.list()
    books.map(BookResponse.from).future
  }
}
