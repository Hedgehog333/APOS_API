package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import services.json.ProductService

class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getAll = Action {
    Ok(ProductService.read)
  }

  def getOne(id: Long) = Action {
    Ok(ProductService.read(id))
  }
}