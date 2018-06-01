package dao

import java.sql.ResultSet
import models.Setting
import scala.util.{Failure, Success, Try}

object Settings extends Connect[models.Setting] with Create[models.Setting] {
  val tableName = "settings"

  override def create(obj: Setting): Try[Long] = {
    var data: Try[Long] = Try(throw new Exception("Error with insert user"))
    withDatabase { database =>
      val connection = database.getConnection
      val sql: String = s"INSERT INTO `$tableName` " +
        "(`language_id`) " +
        "VALUES (?)"
      val stmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)
      stmt.setLong(1, obj.languageId)

      data = Try(stmt.executeUpdate)
      data match {
        case Success(s) => {
          val lastIdSet: ResultSet = stmt.getGeneratedKeys
          if(lastIdSet.next()) {
            data = Try(lastIdSet.getLong(1))
          }
        }
        case Failure(e) => println(e.getMessage)//TODO: Log Error
      }
    }

    data
  }
}