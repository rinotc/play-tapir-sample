package interfaces

import scala.concurrent.Future

trait EndpointSyntaxOps {
  implicit class FutureOps[T](value: T) {
    def future: Future[T] = Future.successful(value)
  }
}
