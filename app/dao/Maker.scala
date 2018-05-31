package dao

import java.sql.ResultSet

trait Maker[T] {
  def maker(resultSet: ResultSet): T
}