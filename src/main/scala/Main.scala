import spray.routing.SimpleRoutingApp
import akka.actor.ActorSystem
import spray.http.MediaTypes
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

import Themodel._




// import connect.connect._
import scala.concurrent.ExecutionContext

object HelloAkkaScala extends App with SimpleRoutingApp{
  implicit val actorSystem = ActorSystem()



  //import org.json4s.JsonDSL._
  //import org.json4s.JsonDSL.WithDouble._
  //import org.json4s.JsonDSL.WithBigDecimal._
  //import org.json4s._
  //import org.json4s.native.JsonMethods._


  import spray.json._
  import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)
  //import RootJsonFormat._ // if you don't supply your own Protocol (see below)
  import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller


  //case class Myuser(name: String)
  //object Myuser {
    
  //}

  import UserFields._

  import TweetFields._



import scala.util._
import reactivemongo.bson.BSONObjectID


implicit object BSONObjectIdProtocol extends RootJsonFormat[BSONObjectID] {
    override def write(obj: BSONObjectID): JsValue = JsString(obj.stringify)
    override def read(json: JsValue): BSONObjectID = json match {
      case JsString(id) => BSONObjectID.parse(id) match {
        case Success(validId) => validId
        case _ => deserializationError("Invalid BSON Object Id")
      }
      case _ => deserializationError("BSON Object Id expected")
    }
  }
import UserFields._
import UserObject._

  

  object MyJsonProtocol extends DefaultJsonProtocol {
    implicit val userFormat = jsonFormat3(User.apply)
    implicit val fullUserFormat = jsonFormat5(FullUser.apply)
    implicit val objectUserFormat = jsonFormat6(UserObject.apply)
    implicit val userSigninFormat = jsonFormat2(UserSignin)
    implicit val tweetSimpleFormat = jsonFormat2(TweetSimple)


    //implicit val user2Format = jsonFormat2(User2)
    //implicit val userFormat = jsonFormat4(User)
  }



  /*object MyJsonProtocol2 extends DefaultJsonProtocol {
    //implicit val colorFormat = jsonFormat4(Color)
    implicit val MyuserFormat = jsonFormat4(Myuser.apply)
  }*/

  import MyJsonProtocol._
  //import MyJsonProtocol2._
  
  import UserObject._
  //import OrderJsonProtocol._
  import akka.http.scaladsl.model.StatusCodes._

import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}
import scala.util.{Success, Failure}

  startServer(interface="localhost", port = 8080){
        
        path("users"/"signup"){
          post{
            entity(as[User]){user =>
                  Themodel.add(user.toObjFullUser)
                  //Future{List(1,2,3)}.map{ll => ll.map{a => println(a)} }
                  complete(Themodel.get() map{
                        user => OK -> user

                    } )

            }    
          }
        }~
        path("users"/"login"){
          post{
            entity(as[UserSignin]){(user)=>
              complete(user.toJson.prettyPrint)            
            }

            }    
        }~
        path("users"/IntNumber/"home"){uId=>
          get{(uId)=>
            respondWithMediaType(MediaTypes.`application/json`){

              complete("uid:\""+uId+"\"")
            }
          }

        }~
        path("tweets"){
          post{
            entity(as[TweetSimple]){(user)=>
              complete(user.toJson.prettyPrint)            
            }

            }    
        }~
        path("users"/IntNumber/"tweets"){ uId=>

          get{
            respondWithMediaType(MediaTypes.`application/json`){
              complete("uidoftweet:\""+uId+"\"")
            }
          }

        }~
        path("tweets"/IntNumber){userNum=>
          get{
            respondWithMediaType(MediaTypes.`application/json`){
              complete("realuidoftweet:\"ok\"")
            }
          
          }


        }~
        path("tweets"/IntNumber){(tweetNum)=>
          get{
            respondWithMediaType(MediaTypes.`application/json`){
              complete("\"realuidoftweet\":"+tweetNum+"\"ok\"")
            }

          }~
          delete{(tweetNum)=>
            complete("");
          }


        }~
        path("users"/IntNumber/"follow"/IntNumber){(user1,user2) =>
          get{

            respondWithMediaType(MediaTypes.`application/json`){
              complete("\"realuidoftweet\":\""+user1 + "------->" + user2 +"ok\"")
            }          
          }
        }~
        path("users"/IntNumber/"unfollow"/IntNumber){(user1,user2) =>
          get{
            respondWithMediaType(MediaTypes.`application/json`){
              complete("\"realuidoftweet\":\"ok\"")
            }          
          }
        }











        }
                

/*    get{
      path("hello"){
        respondWithMediaType(MediaTypes.`application/json`){
          complete(User("CadetBlue").toJson.prettyPrint)
        }
         // or .compactPrint

       // complete( compact(render(User("nasd"))) );
        //complete("welcome akka")
      }
    }~ 
    post {
      path("user"/IntNumber/"add"){num =>
        parameters("name".as[String]){(name)=>
          complete("welcome akka "+name+"  "+num+"\n")            
        }
        
      }
    }~
    post{
      path("test"){
      entity(as[User]){user =>
        complete("ok")

      }
    }

    }~
    post{
      path("/user/signup"){
      entity(as[User]){user =>
        complete("ok")

      }
    }

    }~
    post{
      path("test2"){
      entity(as[User2]){user =>
        complete("oks")

      }
    }

    }

    */




  
 // Post("/", HttpEntity(`application/json`, """{ "name": "Jane" }"""))


////////////////////////////////

///////////////////////

//connect()
  connect();

}
