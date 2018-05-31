package services

trait Create[T] {
  def create(obj: T): T
}