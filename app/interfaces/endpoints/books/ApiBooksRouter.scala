package interfaces.endpoints.books

import akka.stream.Materializer
import interfaces.endpoints.books.delete.DeleteBookEndpoint
import interfaces.endpoints.books.list.ListBooksApiEndpoint
import interfaces.endpoints.books.post.PostBookApiEndpoint
import interfaces.endpoints.books.put.PutBookApiEndpoint
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.tapir.server.play.PlayServerInterpreter

import javax.inject.Inject

class ApiBooksRouter @Inject() (
    listBooksApiEndpoint: ListBooksApiEndpoint,
    postBookApiEndpoint: PostBookApiEndpoint,
    putBookApiEndpoint: PutBookApiEndpoint,
    deleteBookEndpoint: DeleteBookEndpoint
)(implicit mat: Materializer)
    extends SimpleRouter {

  override def routes: Routes = PlayServerInterpreter().toRoutes(
    List(
      postBookApiEndpoint.postBookApiServerEndpoint,
      putBookApiEndpoint.putBookApiServerEndpoint,
      listBooksApiEndpoint.listBooksApiServerEndpoint,
      deleteBookEndpoint.deleteBookApiServerEndpoint
    )
  )
}
