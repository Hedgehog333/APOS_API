package services

trait ReadAll[T] {
  def read: T
}