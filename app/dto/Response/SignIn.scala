package dto.Response

import play.api.libs.json.{JsPath, JsSuccess, Reads}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class SignIn (id: Long, email: String)

object SignIn {
  implicit  val reads: Reads[SignIn] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "email").read[String]
    )(SignIn.apply _)

  implicit val write = new Writes[SignIn] {
    def writes(user: SignIn): JsValue = {
      Json.obj(
        "id" -> user.id,
        "email" -> user.email
      )
    }
  }

  def fromJson(success: JsSuccess[SignIn]): SignIn = {
    val id = success.get.id
    val email = success.get.email

    new SignIn(id, email)
  }

  def formUrlEncoded(map: Map[String, Seq[String]]): SignIn = {
    def getRequireVal(k: String): String = map.get(k).get(0)

    val id: Long = getRequireVal("id").toLong
    val email: String = getRequireVal("email")

    new SignIn(id, email)
  }
}