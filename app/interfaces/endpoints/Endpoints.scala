package interfaces.endpoints

import akka.stream.Materializer
import Library._
import io.circe.generic.auto._
import play.api.routing.Router.Routes
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.play.PlayServerInterpreter

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.Future

object Endpoints {
  val helloEndpoint: PublicEndpoint[String, Unit, String, Any] = endpoint.get
    .in("hello")
    .in(query[String]("name"))
    .out(stringBody)

  val helloServerEndpoint: ServerEndpoint[Any, Future] = helloEndpoint.serverLogicSuccess { name =>
    Future.successful(s"Hello $name!")
  }

  val bookListingEndpoint: PublicEndpoint[Unit, Unit, List[Book], Any] = endpoint.get
    .in("books" / "list" / "all")
    .out(jsonBody[List[Book]])

  val bookListingServerEndpoint: ServerEndpoint[Any, Future] = bookListingEndpoint.serverLogicSuccess { _ =>
    Future.successful(books.get())
  }

  def allRoutes(implicit mat: Materializer): Routes = PlayServerInterpreter().toRoutes(List(helloServerEndpoint, bookListingServerEndpoint))
}

object Library {
  case class Author(name: String)
  case class Book(title: String, year: Int, author: Author)

  val books: AtomicReference[List[Book]] = new AtomicReference(
    List(
      Book("The Sorrows of Young Werther", 1774, Author("Johann Wolfgang von Goethe")),
      Book("Nad Niemnem", 1888, Author("Eliza Orzeszkowa")),
      Book("The Art of Computer Programming", 1968, Author("Donald Knuth")),
      Book("Pharaoh", 1897, Author("Boleslaw Prus"))
    )
  )
}
