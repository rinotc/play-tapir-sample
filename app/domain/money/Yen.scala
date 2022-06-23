package domain.money

case class Yen(value: Int) {
  require(value >= 0)

  override def toString: String = "%,d".format(value)
}
