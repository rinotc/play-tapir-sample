package interfaces

import scala.concurrent.Future

trait EndpointSyntaxOps {
  implicit class FutureOps[T](value: T) {
    def future: Future[T] = Future.successful(value)
  }

  implicit class EitherOps[T](value: T) {

    def left[R]: Either[T, R] = Left(value)

    def right[L]: Either[L, T] = Right(value)
  }
}
