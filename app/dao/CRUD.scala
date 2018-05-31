package dao

trait CRUD[T] extends Create[T] with Read[T] with Update[T] with Delete[T]{

}