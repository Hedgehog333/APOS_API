package services.json

import play.api.libs.json.{JsValue, Json}
import services.{Read, ReadAll}
import dao.Products
import scala.util.{Failure, Success}

object ProductService extends Read[JsValue] with ReadAll[JsValue] {
  override def read(id: Long): JsValue = Products.read(id) match {
    case Success(s) => ReturnedJson.ok(s"Product id=$id. ", Json.toJson(s))
    case Failure(e) => ReturnedJson.internalServerError(s"Error with return product id=$id. " + e.getMessage)//TODO: Log Error
  }

  override def read: JsValue = Products.readAll match {
    case Success(s) => ReturnedJson.ok("All products!", Json.toJson(s))
    case Failure(e) => ReturnedJson.internalServerError("Error with return all products! " + e.getMessage)//TODO: Log Error
  }
}