package domain.money

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should

class YenTest extends AnyWordSpec with should.Matchers {

  "YenTest" when {
    "toString" should {
      "カンマ区切りの文字列を返す" in {
        val yen = Yen(3800)
        yen.toString shouldBe "3,800"
      }
    }
  }
}
