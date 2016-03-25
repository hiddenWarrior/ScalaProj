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




  import UserFields._




import UserFields._
import UserObject._


object TweetFields{

import UserObject.UserBSONWriter
//import UserObject.UserBSONReader



  
  object TweetSimple {

  import HelloAkkaScala.MyJsonProtocol._
  def jsonList(j:List[TweetSimple]): String = {

    def baseJsonList(app:String , jsonList:List[TweetSimple]):String = {
      if(jsonList.length > 1){
         baseJsonList(app + jsonList.head.toJson.prettyPrint+",",jsonList.tail)
      }
      else if(jsonList.length == 1){
        app+jsonList.head.toJson.prettyPrint+"]"        
      }
      else{

        app+"]"        

      }
    }
    baseJsonList("[",j)
  }

    
          implicit object UserBSONReader extends BSONDocumentReader[TweetSimple] {
            def read(doc: BSONDocument): TweetSimple = {
            TweetSimple(
              id = doc.getAs[BSONObjectID]("_id").get,
              body = doc.getAs[String]("body").get,
              author = doc.getAs[String]("author").get
            )
        }

      }

      implicit object UserBSONWriter extends BSONDocumentWriter[TweetSimple] {
        def write( u:TweetSimple ): BSONDocument ={
        BSONDocument(
        "_id" -> u.id,
        "body" -> u.body,
        "author" -> u.author)
        }

      }




    def fromBson(doc: BSONDocument): TweetSimple = {
      TweetSimple(
        id = doc.getAs[BSONObjectID]("_id").get,
        body = doc.getAs[String]("body").get,
        author = doc.getAs[String]("author").get
        
            )
        }

    def getByUser(u :String) = {
      //DbAdapter.get("tweet",BSONDocument()).cursor[TweetSimple].collect[List]()
      //DbAdapter.get("tweet",BSONDocument( "author"-> BSONDocument("$exists" -> 1) ) ).cursor[BSONDocument].collect[List]()//.toList()
      DbAdapter.get("tweet",BSONDocument( "author"-> u ) ).cursor[BSONDocument].collect[List]()//.toList()

    }

    def getById(u :String)={
      DbAdapter.get("tweet" , BSONDocument("_id" ->  BSONObjectID(u) ,"author" -> BSONDocument("$exists" -> 1) ) /*BSONDocument( "_id" -> BSONObjectID(u) )*/ ).one[BSONDocument]

    }
    
    def removeById(u :String)={
      DbAdapter.remove("tweet" , BSONDocument( "_id" -> BSONObjectID(u) , "author" -> BSONDocument("$exists" -> 1)) )

    }


    
  }
  case class TweetSimple(id:BSONObjectID ,body:String , author:String){
    def add() = {
      DbAdapter.add("tweet",toBson())

    }


    

    def toBson(): BSONDocument ={
      BSONDocument(
        "_id" -> id,
        "body" -> body,
        "author" -> author)
      }

  }



}





object UserFields{

  case class User(name: String , email: String , password: String ){
    def toFullUser() = {
      new FullUser(name,email,password,List[String](), List[String]())
    }
    def toObjFullUser() = {
      new UserObject(BSONObjectID.generate,name,email,password,List[String](), List[String]())
    }


    
    /*def toFullObjectUser() = {
      new UserObject(name,email,password,List[String](), List[String]())
    }*/


  }
}




class Jsonable{
}

  



object ObjectsHandler{

}
  
  case class UserSignin(password: String , email: String){
  

  }


  
  case class FullUser(name: String , email: String , password: String , followers: List[String] , followings: List[String] )

  object User  {

    
  }



  case class UserObject(id: BSONObjectID = BSONObjectID.generate,name: String , email: String , password: String , followers: List[String] , followings: List[String]) extends Jsonable{
       def toBson(): BSONDocument ={
      BSONDocument(
        "_id" -> id,
        "name" -> name,
        "email" -> email,
        "password" -> password,
        "followers" -> followers,
        "followings" -> followings)
      }


  }
  object UserObject{



      def rawUser(u: FullUser) = {
        UserObject(name = u.name,email = u.email, password = u.password,followers = u.followers , followings = u.followings)
      }
      val nonUser = UserObject(id = BSONObjectID("0"),name = "a" , email = "" , password = ""  , followers = List("a") , followings = List("a") )

      
      implicit object UserBSONReader extends BSONDocumentReader[UserObject] {
            def read(doc: BSONDocument): UserObject = {
            UserObject(
              id = doc.getAs[BSONObjectID]("_id").get,
              name = doc.getAs[String]("name").get,
              email = doc.getAs[String]("email").get,
              password = doc.getAs[String]("password").get,
              /*followers = List[String](),
              followings = List[String]()*/
              followers = doc.getAs[List[String]]("followers").get.toList,
              followings = doc.getAs[List[String]]("followings").get.toList
            )
        }

      }
                  def read(doc: BSONDocument): UserObject = {
            UserObject(
              id = doc.getAs[BSONObjectID]("_id").get,
              name = doc.getAs[String]("name").get,
              email = doc.getAs[String]("email").get,
              password = doc.getAs[String]("password").get,
              /*followers = List[String](),
              followings = List[String]()*/
              followers = doc.getAs[List[String]]("followers").get.toList,
              followings = doc.getAs[List[String]]("followings").get.toList
            )
        }

        implicit object UserBSONWriter extends BSONDocumentWriter[UserObject] {
        def write( u:UserObject ): BSONDocument ={
          BSONDocument(
            "_id" -> u.id,
            "name" -> u.name,
            "email" -> u.email,
            "password" -> u.password,
            "followers" -> u.followers,
            "followings" -> u.followings)
          }
        }
      def getUser(id: String) = {
        DbAdapter.get("user", BSONDocument( "_id" -> BSONObjectID(id) ) ).one[UserObject]

      } 

      }

    






  








object Themodel{

    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))
    
    
    // Gets a reference to the database "plugin"
    val db = connection("tweets")
  
    val collection = db[BSONCollection]("tweet")



    case class Created(id: String)

import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}



def add(a:UserObject){

  //collection.insert(a.toBson);
  DbAdapter.add("user",a.toBson); 
}


def get(name :String , password: String) = {

  //DbAdapter.get("user",BSONDocument()).map(docOpt => docOpt.map(doc => "a"/*UserObject.read(doc)*/));
  
  DbAdapter.get("user",BSONDocument("email"-> name , "password" -> password)).one[UserObject]
  
  // val filter = BSONDocument("_id" -> 1)
}
def connect(){ 
    

    val query = BSONDocument("name" -> "mostafa")
  
  // val filter = BSONDocument("_id" -> 1)

 /* collection.
    find(query).
    cursor[BSONDocument].
    enumerate().apply(Iteratee.foreach { doc =>
    println(s"found document: ${BSONDocument pretty doc}")
  })

       val document = BSONDocument(
     "Name" -> "Stephane",
     "lastName" -> "Godbillon",
     "age" -> 29)
  */
   //val future1: Future[WriteResult] = collection.insert(document)
   

}

  def connects() {
      // gets an instance of the driver
      // (creates an actor system)
     // println("\n\n\n\n\n\n\n1\n\n\n\n\n\n\n")

    /*  val driver = new MongoDriver
      println("\n\n\n\n\n\n\n2\n\n\n\n\n\n\n")

      val connection = driver.connection(List("localhost"))
      // Gets a reference to the database "plugin"
      println("\n\n\n\n\n\n\n3\n\n\n\n\n\n\n")

      val db = connection("tweets")
      

      println("\n\n\n\n\n\n\n4\n\n\n\n\n\n\n")

     // val collection = db[BSONCollection]("tweet")
      
      println("\n\n\n\n\n\n\n5\n\n\n\n\n\n\n")

      val query = BSONDocument("body" -> "aaad")

      println("\n\n\n\n\n\n\n6\n\n\n\n\n\n\n")
    
    // val filter = BSONDocument("_id" -> 1)
    println("\n\n\n\n\n\n")
    collection.
      find(query).
      cursor[BSONDocument].
      enumerate().apply(Iteratee.foreach { doc =>
      println(s"found document: ${BSONDocument pretty doc}")})
    println("\n\n\n\n\n\n")

      case class MyUser(name: String ,_id: String)
      
      //println(collection.find(query).cursor[MyUser].toList)
    
   val document = BSONDocument(
     "Name" -> "Stephane",
     "lastName" -> "Godbillon",
     "age" -> 29)

   val future1: Future[WriteResult] = collection.insert(document)

   future1.onComplete {
     case Failure(e) => 
      println(s"\n\n\n\n\n\nsuccessfully inserted document with result: \n\n\n\n\n\n")

     throw e
     case Success(writeResult) =>
       println(s"\n\n\n\n\n\nsuccessfully inserted document with result: $writeResult\n\n\n\n\n\n")
   }*/


  //update
  /*
  val selector = BSONDocument("Name" -> "Mostafa")

  val modifier = BSONDocument(
    "$set" -> BSONDocument(
      "lastName" -> "Paris",
      "day" -> "sat"),
      "$unset" -> BSONDocument("name" -> 1))

  val futureUpdate1 = collection.update(selector, modifier)

    println("\n\n\n\n\n\nSuccessfullyyyyyyyyy Updated\n\n\n\n\n\n")
  */
  //driver.close()
  
  }

}






