import java.util.*;

public class FDChecker {

	private static void help(final AttributeSet result,final AttributeSet table,final Set<FunctionalDependency> fds){
		AttributeSet t=new AttributeSet(result);
		t.retainAll(table);
		AttributeSet c=closure(t, fds);
		c.retainAll(table);
		result.addAll(c);
	}
	
	/**
	 * Checks whether a decomposition of a table is dependency
	 * preserving under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is dependency preserving, false otherwise
	 **/
	public static boolean checkDepPres(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		//your code here
		//a decomposition is dependency preserving, if local functional dependencies are
		//sufficient to enforce the global properties
		//To check a particular functional dependency a -> b is preserved, 
		//you can run the following algorithm
		//result = a
		//while result has not stabilized
		//	for each table in the decomposition
		//		t = result intersect table 
		//		t = closure(t) intersect table
		//		result = result union t
		//if b is contained in result, the dependency is preserved
		for(FunctionalDependency fd:fds){
			AttributeSet result=new AttributeSet(fd.left);
			int oldSize=-1;
			while(oldSize!=result.size()){
				oldSize=result.size();
			    help(result,t1,fds);
			    help(result,t2,fds);
			}
			if(!result.contains(fd.right)){
				return false;
			}
			
		}
		return true;
	}

	/**
	 * Checks whether a decomposition of a table is lossless
	 * under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is lossless, false otherwise
	 **/
	public static boolean checkLossless(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		//your code here
		//Lossless decompositions do not lose information, the natural join is equal to the 
		//original table.
		//a decomposition is lossless if the common attributes for a superkey for one of the
		//tables.
		AttributeSet inter=new AttributeSet(t1);
		inter.retainAll(t2);
		AttributeSet closure=closure(inter, fds);
		return closure.containsAll(t1)||closure.containsAll(t2);
		
	}

	//recommended helper method
	//finds the total set of attributes implied by attrs
	private static AttributeSet closure(AttributeSet attrs, Set<FunctionalDependency> fds) {
		AttributeSet closure=new AttributeSet(attrs);
		int oldSize=-1;
		while(oldSize!=closure.size()){
			oldSize=closure.size();
			for(FunctionalDependency fd:fds){
				if(closure.containsAll(fd.left)){
					closure.add(fd.right);
				}
			}
		}
		return closure;
	}
}
