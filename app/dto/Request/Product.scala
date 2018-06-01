package dto.Request

import play.api.libs.functional.syntax._
import play.api.libs.json._

//TODO: add categoryId and categoryName or not
case class Product (id: Long, name: String, description: String, price: Float)

object Product {
  implicit  val reads: Reads[Product] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "price").read[Float]
    )(Product.apply _)

  implicit val write = new Writes[Product] {
    def writes(product: Product): JsValue = {
      Json.obj(
        "id" -> product.id,
        "name" -> product.name,
        "description" -> product.description,
        "price" -> product.price.toString
      )
    }
  }

  def fromJson(success: JsSuccess[Product]): Product = {
    val id = success.get.id
    val name = success.get.name
    val description = success.get.description
    val price = success.get.price

    Product(id, name, description, price)
  }

  def formUrlEncoded(map: Map[String, Seq[String]]): Product = {
    def getRequireVal(k: String): String = map.get(k).get(0)

    val id: Long = getRequireVal("id").toLong
    val name: String = getRequireVal("name")
    val description: String = getRequireVal("description")
    val price: Float = getRequireVal("price").toFloat

    Product(id, name, description, price)
  }
}