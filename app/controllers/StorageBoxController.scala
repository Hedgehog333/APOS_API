package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.json.{ReturnedJson, StorageBoxService}

class StorageBoxController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def check = Action { implicit request =>
    val body = request.body.asJson match {
      case Some(value) => StorageBoxService.check(value)
      case None => {
        request.body.asFormUrlEncoded match {
          case Some(value) => {
            val checkCode: dto.Request.CheckCode = dto.Request.CheckCode.formUrlEncoded(value)

            StorageBoxService.check(Json.toJson(checkCode))
          }
          case None => ReturnedJson.bedRequest("Need send only JSON format!")
        }
      }
    }

    Ok(body)
  }
}