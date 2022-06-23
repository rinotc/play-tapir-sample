package modules

import com.google.inject.AbstractModule
import domain.book.BookRepository
import gateways.book.MemoryBookRepository
import usecase.books.add.{AddBookInteractor, AddBookUseCase}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[BookRepository]).toInstance(MemoryBookRepository)

    bind(classOf[AddBookUseCase]).to(classOf[AddBookInteractor])
  }
}
