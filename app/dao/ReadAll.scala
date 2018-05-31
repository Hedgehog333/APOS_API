package dao

import scala.util.Try

trait ReadAll[T] {
  def readAll(): Try[Set[T]]
}