
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;
import java.util.List;
interface PartialSolution {

	// if a state is not valid it should not be further
	// explored and can be discarded
	public boolean isValid();

	// if a state is valid and final then it is
	// considered a solution
	public boolean isFinal();

	// states which are valid but not final
	// must be further developed in order to
	// find a solution
	public List<PartialSolution> successors();
}

public class Backtracking {

	// search and return all the solutions to a given problem
	public static List<PartialSolution> allSolutions(PartialSolution problem) {

		// we store the frontier of the search tree in a stack
		Stack<PartialSolution> frontier = new Stack<PartialSolution>();
		// the initial frontier only contains the problem given
		frontier.add(problem);

		// we will store the solutions found in a list
		List<PartialSolution> sols = new ArrayList<PartialSolution>();

		// tree traversal
		PartialSolution current;
		// the exit condition is that the frontier is empty
		while (!frontier.isEmpty()) {
			// we know that there is some element
			// we look at the top element
			current = frontier.peek();
			frontier.pop();
			// if it is not valid, just remove it (do nothing)
			if (current.isValid()) {
				if (current.isFinal()) {
					// if it is valid and final, it is a solution
					// add it to sols
					sols.add(current);
				} else {
					// if it is not final, expand it
					List<PartialSolution> nextones = current.successors();
					// and add the successors to the frontier
					Iterator<PartialSolution> cursor = nextones.iterator();
					while (cursor.hasNext()) {
						frontier.push(cursor.next());
						cursor.remove();
					}
				}
			}
		}
		// no more states to explore => return solutions found
		return sols;
	} // END allSolutions 


}
