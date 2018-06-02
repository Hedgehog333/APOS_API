package dao

import java.sql.ResultSet

import scala.util.{Failure, Success, Try}
import dto.{Request, Response}

object StorageBoxes extends Connect[models.StorageBox] {
  val tableName = "storage_boxes"

  def checkCode(obj: Request.CheckCode ): Try[Response.CheckCode] = {
    var data: Try[Response.CheckCode] = Try(new Response.CheckCode(false, obj.orderId))

    withDatabase{ database =>
      val connection = database.getConnection()
      val sql: String = s"select * from $tableName where code like ? and order_id = ?"
      val stmt = connection.prepareStatement(sql)
      stmt.setString(1, obj.code)
      stmt.setLong(2, obj.orderId)

      val rs: Try[ResultSet] = Try(stmt.executeQuery)
      rs match {
        case Success(v) => {
          if (v.next()) {
            val storageBox: models.StorageBox = models.StorageBox.maker(v)
            data = Try(new Response.CheckCode(true, storageBox.orderId))
          }
        }
        case Failure(e) => println("Failure: " + e.getMessage)//TODO: Log Error
      }
    }

    data
  }
}