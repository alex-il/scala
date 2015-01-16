package sc.singleton
import sc.singleton.Sheep._

object Runner extends App {
	println( getJ )
	val s = Sheep( "Dolly", true )
	services.put("k", "value")
	services.put("k1", "value1")
	services.put("k11", "value11")
	for(s<-services){
		println(s._1 +":"+s._2 )
	}
	val ss = new Sheep( "wwww" )
	println( services )
	println( ss.getJJJ )
	val mm = ss.getClass().getDeclaredMethods()
	System.err.println( mm.length );

	val ss2 = new Sheep( "Dolllll" )
	Sheep( "Dolllll" )
	Sheep( "Dolllll" )
	println( getJ )
	println( ss.getJJJ )
}