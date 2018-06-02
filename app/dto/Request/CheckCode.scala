package dto.Request

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CheckCode (orderId: Long, code: String)

object CheckCode {
  implicit  val reads: Reads[CheckCode] = (
    (JsPath \ "orderId").read[Long] and
      (JsPath \ "code").read[String]
    )(CheckCode.apply _)

  implicit val write = new Writes[CheckCode] {
    def writes(product: CheckCode): JsValue = {
      Json.obj(
        "orderId" -> product.orderId,
        "code" -> product.code
      )
    }
  }

  def fromJson(success: JsSuccess[CheckCode]): CheckCode = {
    val orderId = success.get.orderId
    val code = success.get.code

    CheckCode(orderId, code)
  }

  def formUrlEncoded(map: Map[String, Seq[String]]): CheckCode = {
    def getRequireVal(k: String): String = map.get(k).get(0)

    val orderId: Long = getRequireVal("order_id").toLong
    val code: String = getRequireVal("code")

    CheckCode(orderId, code)
  }
}