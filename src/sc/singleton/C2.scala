package sc.singleton

import scala.collection.JavaConversions.propertiesAsScalaMap

object C2 extends Counter {
  def dec { c = c - 1 }

  def main(args: Array[String]): Unit = {}
  C2.inc
  C2.inc
  C2.inc
  val s = C2.inc
  System.out.println(s)
  System.out.println("===============\n\n")
  
  for( (k,v)<- System.getProperties() ){
    println(k+": "+v)
  }
  System.out.println("===============\n\n")
  for( ("sun.desktop", v)<- System.getProperties() ){
  	println(":__"+v)
  }
  for( (k, "amd64")<- System.getProperties() ){
  	println(k+":__")
  }
}