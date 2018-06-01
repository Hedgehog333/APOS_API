package models
import java.sql.ResultSet

case class User (id: Long, name: String, email: String, password: String, cityId: Long, settingId: Long,
                 mobileCodeId: Long, phoneNumber: String)

object User extends dao.Maker[User] {
  override def maker(resultSet: ResultSet): User = {
    val id: Long = resultSet.getLong("id")
    val name: String = resultSet.getString("name")
    val email: String = resultSet.getString("email")
    val password: String = resultSet.getString("password")
    val settingId: Long = resultSet.getLong("settings_id")
    val cityId: Long = resultSet.getLong("city_id")
    val mobileCodeId: Long = resultSet.getLong("mobile_code_id")
    val phoneNumber: String = resultSet.getString("phone_number")

    new User(id, name,email, password, cityId, settingId, mobileCodeId, phoneNumber)
  }
}