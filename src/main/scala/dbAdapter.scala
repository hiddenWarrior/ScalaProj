import play.api.libs.iteratee.Iteratee

import reactivemongo.api.collections.bson.BSONCollection
//import reactivemongo.api.collections.default.BSONCollection

import reactivemongo.api.QueryOpts
import reactivemongo.core.commands.Count

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

import scala.util.{ Failure, Success }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import spray.json._
import scala.util._
import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}

import reactivemongo.api.commands.WriteResult
import reactivemongo.api._



object DbAdapter{

	val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))
    
    
    // Gets a reference to the database "plugin"
    val db = connection("tweets")
    val collections = Map("user" -> db[BSONCollection]("user"),"tweet" -> db[BSONCollection]("tweet"))
    
} 