package services.json

import services.Create
import dto.Request.{SignUp, SignIn}
import dao.Users
import play.api.libs.json._
import scala.util.{Success, Failure}

object UserService extends Create[JsValue] {
  override def create(obj: JsValue): JsValue = {
    obj.validate[SignUp] match {
      case success: JsSuccess[SignUp] => Users.create(success.get) match {
        case Success(s) => ReturnedJson.ok("User created!", Json.obj("id" -> s.toLong))
        case Failure(e) => ReturnedJson.internalServerError("Error with create user! " + e.getMessage)//TODO: Log Error
      }
      case JsError(error) => ReturnedJson.bedRequest//TODO: Log Error
    }
  }

  def signIn(obj: JsValue): JsValue = Users.getByEmailAndPassword(obj.validate[SignIn].get) match {
    case Success(s) => ReturnedJson.ok("Sign In user. ", Json.toJson(s))
    case Failure(e) => ReturnedJson.internalServerError("Error with signIn user. " + e.getMessage)//TODO: Log Error
  }
}