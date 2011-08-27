package bestfirst

import scala.collection.mutable.PriorityQueue

class BestFirstSearch[N](
  val init: List[N],
  val refine: N => List[N],
  val isComplete: N => Boolean,
  val evaluate: N => Double,
  val parameter: SearchParameter) {

  val queue = new PriorityQueue[ValuedNode[N]]()
  val stats = new SearchStats

  def search(): N =
    {
      val firstNodes = init
      println("init:" + init.mkString(" "))
      addToQueue(firstNodes)
      while (!queue.isEmpty) {
        val best = queue.dequeue().content
        if (isComplete(best)) return best
        val children = refine(best)
        addToQueue(children)
        if (stats.nodeVisited > parameter.limit)
          throw new Exception("Not found: Search limit exceeded")
      }
      // queue is empty but no valid solution found
      throw new Exception("Not found: Queue exhausted")

    }

  def addToQueue(nodes: List[N]) {
    for (node <- nodes) {
      val value = evaluate(node)
      queue += new ValuedNode[N](node, evaluate(node))
      stats.nodeVisited += 1
    }
  }
}

object BestFirstSearch {
  def main(args: Array[String]): Unit =
    {
      println("hello")
    }
}