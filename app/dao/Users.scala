package dao

import java.sql.ResultSet
import dto.Request.{SignUp, SignIn}
import dto.Response
import org.mindrot.jbcrypt.BCrypt
import scala.util.{Failure, Success, Try}

object Users extends Connect[models.User] with Create[SignUp]{
  val tableName = "users"

  override def create(obj: SignUp): Try[Long] = {
    var data: Try[Long] = Try(throw new Exception("Error with insert user"))
    withDatabase { database =>
      val connection = database.getConnection
      var sql: String = s"SELECT * FROM `$tableName` where email like ?"
      var stmt = connection.prepareStatement(sql)
      stmt.setString(1, obj.email)

      val rs = stmt.executeQuery

      if(!rs.next) {
        sql = s"INSERT INTO `$tableName` " +
          "(`name`, `email`, `password`, `city_id`, `settings_id`, `phone_number`, `mobile_code_id`) " +
          "VALUES (?,?,?,?,?,?,?)"

        stmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, obj.name)
        stmt.setString(2, obj.email)
        stmt.setString(3, BCrypt.hashpw(obj.password, BCrypt.gensalt(7)))
        stmt.setLong(4, obj.cityId)

        val settingId: Long = Settings.create(new models.Setting(-1, 1)) match {
          case Success(s) => s
          case Failure(e) => {
            //TODO: Log Error
            println(e.getMessage)
            -1
          }
        }

        stmt.setLong(5, settingId)
        stmt.setString(6, obj.phoneNumber)
        stmt.setLong(7, obj.mobileCodeId)

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
      } else {
        data = Try(throw new Exception(s"User with email ${obj.email} already exists."))
      }
    }

    data
  }

  def getByEmailAndPassword(obj: SignIn): Try[Response.SignIn] = {
    var user: Try[Response.SignIn] = Try(throw new Exception("User Not Found"))
    withDatabase{ database =>
      val connection = database.getConnection()
      val sql: String = s"select * from $tableName where email like ?"
      val stmt = connection.prepareStatement(sql)
      stmt.setString(1, obj.email)

      val rs: Try[ResultSet] = Try(stmt.executeQuery)
      rs match {
        case Success(v) => {
          if (v.next()) {
            val tmpUser: models.User = models.User.maker(v)
            if(BCrypt.checkpw(obj.password, tmpUser.password))
              user = Try(new Response.SignIn(tmpUser.id, tmpUser.email))
          }
        }
        case Failure(e) => println("Failure: " + e.getMessage)//TODO: Log Error
      }
    }

    user
  }

}