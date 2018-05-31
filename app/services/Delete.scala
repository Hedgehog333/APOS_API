package services

trait Delete[T] {
  def delete(id: Long): T
}