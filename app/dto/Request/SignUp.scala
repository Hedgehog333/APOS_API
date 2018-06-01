package dto.Request

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class SignUp (name: String, email: String, password: String, cityId: Long, mobileCodeId: Long, phoneNumber: String)

object SignUp {
  implicit  val reads: Reads[SignUp] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String] and
      (JsPath \ "cityId").read[Long] and
      (JsPath \ "mobileCodeId").read[Long] and
      (JsPath \ "phoneNumber").read[String]
    )(SignUp.apply _)

  implicit val write = new Writes[SignUp] {
    def writes(user: SignUp): JsValue = {
      Json.obj(
        "name" -> user.name,
        "email" -> user.email,
        "password" -> user.password,
        "cityId" -> user.cityId,
        "mobileCodeId" -> user.mobileCodeId,
        "phoneNumber" -> user.phoneNumber
      )
    }
  }

  def fromJson(success: JsSuccess[SignUp]): SignUp = {
    val name = success.get.name
    val email = success.get.email
    val password = success.get.password
    val cityId = success.get.cityId
    val mobileCodeId = success.get.mobileCodeId
    val phoneNumber = success.get.phoneNumber

    new SignUp(name, email, password, cityId, mobileCodeId, phoneNumber)
  }

  def formUrlEncoded(map: Map[String, Seq[String]]): SignUp = {
    def getRequireVal(k: String): String = map.get(k).get(0)

    val name: String = getRequireVal("name")
    val email: String = getRequireVal("email")
    val password: String = getRequireVal("password")
    val cityId: Long = getRequireVal("cityId").toLong
    val mobileCodeId: Long  = getRequireVal("mobileCodeId").toLong
    val phoneNumber: String = getRequireVal("phoneNumber")

    new SignUp(name, email, password, cityId, mobileCodeId, phoneNumber)
  }
}