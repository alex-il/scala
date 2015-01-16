package ttt

import java.util.Formatter.DateTime

object Test2 extends App {
	//	val xml = "** @Updated by:    0000"
	val xml = scala.io.Source.fromFile( "d:/2.xml" ).getLines.mkString
	println( xml )
	val nnn = xml.replace( "@Updated by:", " @Updated by: =Moishe=" )
	println( nnn )
	val format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SS ")
	System.err.println( format.format(new java.util.Date()) )
}
 