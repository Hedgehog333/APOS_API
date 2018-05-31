package services

trait Update[T] {
  def update(obj: T, id: Long): T
}