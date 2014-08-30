package patterns

trait Persist[A] {
  def value: A

  def map[B](f: A => B): Persist[B]
}

case class PersistFileImpl[A](path: Path, content: A) extends Persist[A] {
  //  override def value: String = content
  //
  //  override def map[String](f: (String) => String): Persist[String] = {
  //    Persist(path, f(content))
  //  }
  override def value: A = content

  override def map[B](f: (A) => B): Persist[B] = Persist(path, f(content))
}

object Persist extends WriteSupport {

  def apply[A](path: Path, content: A): Persist[A] = {
    withWriter(path)(_.write(content.toString))
    PersistFileImpl(path, content)
  }
}
