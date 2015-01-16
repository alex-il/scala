package ttt

import scala.collection.mutable.HashMap

object Main1 {
  println( "Nww Yooo GoGoS".partition(_.isUpper))
//  -----------------------------------
  val keys = List("2", "*", ">")
  val vals = Array(2,5,3)
  val pairs = keys.zip(vals)
  println(pairs)
  val pMap = pairs.toMap
  for((k,v)<-pairs) (print(k*v),  print(" . "))
  println(pMap)
  println ("\n-------------------------")
//  -----------------------------------
  val t = new Test1
  
  def main(args: Array[String]) {

    println("fdsaadsfsdf")
  }

  class Test1 {
    println("Test1")
    println("Test1.2")
    Inner.hhh.put("q1","v1")
    Inner.hhh.put("q2","v22")
    Inner.hhh.put("q3","v133")
 
    for( k<- Inner.hhh.keySet){
      println("Key:"+k+" , val:"+Inner.hhh.apply(k))
    }
      
  }
  
  private object Inner{
    println( "Inner")
    val hhh = HashMap.empty[String, String]
 
  } 
}