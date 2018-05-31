package services

trait Read[T] {
  def read(id: Long): T
}