package sc.singleton

class Sheep( val s: String ) {
	private var isBlack = false
	private var i = 0
	def getJJJ = { Sheep.j }
}

object Sheep {
	var j = 0
	var map = collection.mutable.HashMap.empty[String, Any]
	var map1 = collection.mutable.HashMap.empty[String, Any]
	var map2 = collection.mutable.HashMap.empty[String, String]

	def apply( name: String, isBlack: Boolean ) {
		val s = new Sheep( name )
		s.isBlack = isBlack
		j += 1
		println( "count:" + j + "; " + s.isBlack );
	}

	def apply( name: String ) {
		j += 1
		val s = new Sheep( name )
		println( "count:" + j + "; " + s.isBlack );
	}

	def getJ = j
	def services = map2
}