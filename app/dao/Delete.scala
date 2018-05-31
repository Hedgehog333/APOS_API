package dao

import scala.util.Try

trait Delete[T] {
  def delete(id: Long): Try[Boolean]
}