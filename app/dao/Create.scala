package dao

import scala.util.Try

trait Create[T] {
  def create(obj: T): Try[Long]
}