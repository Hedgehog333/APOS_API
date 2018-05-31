package dao

import scala.util.Try

trait Read[T] {
  def read(id: Long): Try[T]
}