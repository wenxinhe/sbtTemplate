package patterns

import org.scalatest.{ShouldMatchers, FunSpec}

/**
 * Created by vincent on 8/30/14.
 */
class PersistTest extends FunSpec with ShouldMatchers with ReadSupport {

  case class Account(name: String, age: Int)

  val path = Path("./temp/test.txt")
  val content = "hello,world\n"
  val account = Account("Vincent", 30)

  describe("Persist") {
    it("can persist value into context") {
      Persist(path, content)
      read(path) shouldBe content
    }
    it("can get value") {
      Persist(path, content).value shouldBe content
      read(path) shouldBe content
    }
    it("can change value in context") {
      Persist(path, content).map(_.toUpperCase).value shouldBe content.toUpperCase
      read(path) shouldBe content.toUpperCase
    }
    it("can invoke map in chain") {
      Persist(path, "Hello").map(_ + ", world").map(_ + "!").value shouldBe "Hello, world!"
      read(path) shouldBe "Hello, world!"
    }

    it("can persis customed type value") {
      Persist(path, account)
      read(path) shouldBe account.toString
    }
    it("can get customed type value from context") {
      Persist(path, account).value shouldBe account
    }
    it("can change customed type value in context") {
      Persist(path, account).map(x => Account(x.name, x.age + 1)).value shouldBe Account("Vincent", 31)
      read(path) shouldBe Account("Vincent", 31).toString
    }
  }

}
