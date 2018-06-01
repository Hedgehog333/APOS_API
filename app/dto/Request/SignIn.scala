package dto.Request

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class SignIn (email: String, password: String)

object SignIn {
  implicit  val reads: Reads[SignIn] = (
    (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String]
  )(SignIn.apply _)

  implicit val write = new Writes[SignIn] {
    def writes(user: SignIn): JsValue = {
      Json.obj(
        "email" -> user.email,
        "password" -> user.password
      )
    }
  }

  def fromJson(success: JsSuccess[SignIn]): SignIn = {
    val email = success.get.email
    val password = success.get.password

    new SignIn(email, password)
  }

  def formUrlEncoded(map: Map[String, Seq[String]]): SignIn = {
    def getRequireVal(k: String): String = map.get(k).get(0)

    val email: String = getRequireVal("email")
    val password: String = getRequireVal("password")

    new SignIn(email, password)
  }
}