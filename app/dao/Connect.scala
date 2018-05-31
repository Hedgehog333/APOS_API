package dao

import play.api.db.{Database, Databases}

trait Connect[T] {
  def withDatabase[T](block: Database => T): Unit = {
    Databases.withDatabase(
      driver = "com.mysql.jdbc.Driver",
      url = "jdbc:mysql://localhost:3306/APOS",

      //TODO: User database credentials
      config = Map(
        "username" -> "root",
        "password" -> "123"
      )
    )(block)
  }
}