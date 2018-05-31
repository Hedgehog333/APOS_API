package dao

import scala.util.Try

trait Update[T] {
  def update(obj: T): Try[Boolean]
}