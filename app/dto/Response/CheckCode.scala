package dto.Response

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CheckCode (isCodeCorrect: Boolean, orderId: Long)

object CheckCode {
  implicit  val reads: Reads[CheckCode] = (
    (JsPath \ "isCodeCorrect").read[Boolean] and
      (JsPath \ "orderId").read[Long]
    )(CheckCode.apply _)

  implicit val write = new Writes[CheckCode] {
    def writes(product: CheckCode): JsValue = {
      Json.obj(
        "isCodeCorrect" -> product.isCodeCorrect,
        "orderId" -> product.orderId
      )
    }
  }
}