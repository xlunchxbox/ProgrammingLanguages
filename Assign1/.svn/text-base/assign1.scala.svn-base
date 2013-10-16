// Immutability
val listOfNames = List("Amar", "Ananta", "Camilo", "Christopher", "Cody", 
					"Dylan", "Hieu", "Jesus", "Jose", "Joseph", "Josue", 
					"Kayla", "Mary", "Michael", "Oscar", "Owais", "Ryan", 
					"Suman", "Varun", "Venkat", "Wei")
			

// Problem 3 
println(listOfNames.foldLeft(0.0) {(x: Double, y: String) => x + y.length}/listOfNames.length)

// Problem 4
listOfNames.zipWithIndex.foreach{ case(e, i) => 
	if(listOfNames.length > i+1) {
		if(e(0) != listOfNames(i+1)(0))
			println(e(0) + "=" + listOfNames.count(x=> x(0) == e(0)))
	}else
		println(e(0) + "=" + listOfNames.count(x=> x(0) == e(0)))		
}
