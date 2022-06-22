package routers

import akka.actor.ActorSystem
import endpoints.Endpoints
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

import javax.inject.Inject

class ApiRouter @Inject() (implicit system: ActorSystem) extends SimpleRouter {
  override def routes: Routes = {
    Endpoints.allRoutes
  }
}
