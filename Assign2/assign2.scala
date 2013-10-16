import scala.annotation.tailrec

def time(position: Int)(function: Int => BigInt) ={
	val timeBefore = System.currentTimeMillis
	print("Position: "+position+" Value: "+function(position)+" Time: "+
	(System.currentTimeMillis - timeBefore)+"ms\n")
}


def fibIterativeIterative(position: Int) = {
	var elementone = BigInt(0)
	var elementtwo = BigInt(1)
	
	def next(elementone: BigInt , elementtwo: BigInt) = Pair(elementtwo, elementone + elementtwo);
	var counter = 0
	for(counter <- 1 to position){
		val Pair(first, second) = next(elementone, elementtwo);
		elementone = first
		elementtwo = second
	}
	elementone
}


def fibRecursiveRecursive( position: Int): Int = position match {
    case 0 | 1 => position
    case _ => fibRecursiveRecursive(position -1) + fibRecursiveRecursive(position-2)
  }


def callTailFib(pos: Int): BigInt = {
	@tailrec def tailFib(oldVal: BigInt, newVal: BigInt, pos: Int): BigInt = {
		pos match {
		case 1 => newVal
		case _ => tailFib(newVal, oldVal + newVal, pos - 1)
		}
	}
	tailFib(BigInt(0), BigInt(1), pos)
}


def fibOptomized(pos:Int):BigInt = {
   def fibopt(pos:Int):(BigInt,BigInt) = if (pos == 1) (1,0) else {
     val (first,second) = fibopt(pos/2)
     val evalone = (2*second+first)*first
     val evaltwo = first*first + second*second
     if(pos % 2 == 0) (evalone,evaltwo) else (evalone+evaltwo,evalone)
   }
   fibopt(pos)._1
}


val position = 40
time(position)(fibIterativeIterative)
time(position)(fibRecursiveRecursive)
time(position)(callTailFib)
time(position)(fibOptomized)


