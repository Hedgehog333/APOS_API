package models
import java.sql.ResultSet

case class Product (id: Long, name: String, description: String, price: Float, categoryId: Long)

object Product extends dao.Maker[Product] {
  override def maker(resultSet: ResultSet): Product = {
    val id: Long = resultSet.getLong("id")
    val name: String = resultSet.getString("name")
    val description: String = resultSet.getString("description")
    val price: Float = resultSet.getFloat("price")
    val categoryId: Long = resultSet.getLong("category_id")

    new Product(id, name, description, price, categoryId)
  }
}