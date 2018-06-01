package dao

import java.sql.ResultSet
import dto.Request.Product
import scala.util.{Failure, Success, Try}

object Products extends Connect[models.Product] with Read[Product] with ReadAll[Product] {
  val tableName = "products"

  override def read(id: Long): Try[Product] = {
    var data: Try[Product] = Try(throw new Exception(s"Product id=$id Not Found"))
    withDatabase{ database =>
      val connection = database.getConnection()
      val sql: String = s"select * from $tableName where id = $id"
      val stmt = connection.prepareStatement(sql)

      val rs: Try[ResultSet] = Try(stmt.executeQuery)
      rs match {
        case Success(v) => {
          if (v.next()) {
            val prod = models.Product.maker(v)
            data = Try(Product(prod.id, prod.name, prod.description, prod.price))
          }
        }
        case Failure(e) => println(e.getMessage)//TODO: Log Error
      }
    }

    data
  }

  override def readAll(): Try[Set[Product]] = {
    var set: Set[Product] = Set()
    withDatabase { database =>
      val connection = database.getConnection()
      val sql: String = s"select * from $tableName"

      val stmt = connection.prepareStatement(sql)
      val rs: ResultSet = stmt.executeQuery

      while (rs.next()) {
        val prod = models.Product.maker(rs)
        set += Product(prod.id, prod.name, prod.description, prod.price)
      }
    }

    Try(set)
  }
}