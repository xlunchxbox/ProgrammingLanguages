import scala.io.Source._
import scala.xml._
import scala.actors._
import scala.actors.Futures._
import scala.collection.immutable._
 
def readFile(filename: String): String = scala.io.Source.fromFile(filename).mkString

def fetchXML(code: String): Elem = XML.load("http://weather.yahooapis.com/forecastrss?w="+code+"&u=f")

def parseXML(xml: Elem): (String, List[String]) = ((xml \\ "@region").text + " \t " + (xml \\ "@city").text,
		List((xml \\ "@temp").text, ((xml \\ "@text").head).text, ((xml \\ "@high").head).text, ((xml \\ "@low").head).text))

def max(a: (String, List[String]), b: (String, List[String])) = if(a._2(0).toInt > b._2(0).toInt) a else b

def min(a: (String, List[String]), b: (String, List[String])) = if(a._2(0).toInt < b._2(0).toInt) a else b

def singleThread(cities: List[String]) = {
	val first_city = parseXML(fetchXML(cities(0)))
	(1 to cities.length-1).foldLeft((Map(first_city._1->first_city._2), first_city, first_city))((result, i) => {
			val ith_city = parseXML(fetchXML(cities(i)))
			(result._1 ++ Map(ith_city._1->ith_city._2), max(ith_city, result._2), min(ith_city, result._3)) })
}

def multiThread(cities: List[String]) = {
	val first_city = parseXML(fetchXML(cities(0)))	
	val tasks = for (i <- 1 to cities.length - 1) yield future {
		parseXML(fetchXML(cities(i)))
	}
	tasks.foldLeft((Map(first_city._1->first_city._2), first_city, first_city))((result, fun) => { 
			val ith_city = fun()
			(result._1 ++ Map(ith_city._1->ith_city._2), max(ith_city, result._2), min(ith_city, result._3)) })
}

def output(weathers: Map[String, List[String]], max: (String, List[String]), min: (String, List[String])) = {
	println("State \t City \t\t High \t Low \t Current Condition")
	weathers.foreach{ case (k,v) => println(k + " \t " + v(2) + " \t " + v(3) + " \t " + v(0) + " \t " + v(1)) }
	println("The city with the highest temperature:")
	println(max._1 + " \t " + max._2(2) + " \t " + max._2(3) + " \t " + max._2(0) + " \t " + max._2(1))
	println("The city with the lowest temperature:")
	println(min._1 + " \t " + min._2(2) + " \t " + min._2(3) + " \t " + min._2(0) + " \t " + min._2(1))
}

def timeIt(function:  List[String] => (Map[String, List[String]], (String, List[String]), (String, List[String])), cities:  List[String]) = {
	val startTime = System.nanoTime
	val result = function(cities)
	val sortMap = TreeMap(result._1.toSeq:_*)
	val endTime = System.nanoTime
	val executionTime = (endTime - startTime)/1e9
	println("Total time is " + executionTime + "s")
	output(sortMap, result._2, result._3)
}

val cities = readFile("cities.txt").split(",").toList
println("\nSingle Thread")
timeIt(singleThread, cities)
println("\nMultithread")
timeIt(multiThread, cities)