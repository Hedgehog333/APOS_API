package models
import java.sql.ResultSet

case class StorageBox (id: Long, number: String, statusId: Long, code: String, orderId: Long)

object StorageBox extends dao.Maker[StorageBox] {
  override def maker(resultSet: ResultSet): StorageBox = {
    val id: Long = resultSet.getLong("id")
    val number: String = resultSet.getString("number")
    val statusId: Long = resultSet.getLong("status_id")
    val code: String = resultSet.getString("code")
    val orderId: Long = resultSet.getLong("order_id")

    StorageBox(id, number, statusId, code, orderId)
  }
}