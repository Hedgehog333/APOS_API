package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import services.json.{UserService, ReturnedJson}
import dto.Request.{SignUp, SignIn}
import play.api.libs.json._

class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def signIn = Action { implicit request =>
    val body = request.body.asJson match {
      case Some(value) => UserService.signIn(value)
      case None => {
        request.body.asFormUrlEncoded match {
          case Some(value) => {
            val user: SignIn = SignIn.formUrlEncoded(value)

            UserService.signIn(Json.toJson(user))
          }
          case None => ReturnedJson.bedRequest("Need send only JSON format!")
        }
      }
    }

    Ok(body)
  }

  def singUp = Action { implicit request =>
    val body = request.body.asJson match {
      case Some(value) => UserService.create(value)
      case None => {
        request.body.asFormUrlEncoded match {
          case Some(value) => {
            val user: SignUp = SignUp.formUrlEncoded(value)

            UserService.create(Json.toJson(user))
          }
          case None => ReturnedJson.bedRequest("Need send only JSON format!")
        }
      }
    }

    Ok(body)
  }

  def info(id: Long) = Action {

    Ok("")
  }
}