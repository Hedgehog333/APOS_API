package services.json

import dao.StorageBoxes
import dto.Request.CheckCode
import play.api.libs.json.{JsValue, Json}
import scala.util.{Failure, Success}

object StorageBoxService {
  def check(obj: JsValue): JsValue = StorageBoxes.checkCode(obj.validate[CheckCode].get) match {
    case Success(s) => ReturnedJson.ok("Checking successful. ", Json.toJson(s))
    case Failure(e) => ReturnedJson.internalServerError("Error with check code. " + e.getMessage)//TODO: Log Error
  }
}
