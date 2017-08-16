Newly construct a function, help, in order to compute intersection and union.
Also import the test cases derived from piazza.

Following are the functions in our code:

private static void help(final AttributeSet result,final AttributeSet table,final Set<FunctionalDependency> fds)
Realize the functionality as the instruction said 
“for each table in the decomposition
	t = result intersect table 
	t = closure(t) intersect table
	result = result union t”.

public static boolean checkDepPres(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds)
While result has not stabilized, call help. If result does not include b (i.e., fd.right), the dependency is not preserved. Vice-verse.

public static boolean checkLossless(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds)
Do natural join, if it is equal to the original table, then a decomposition do not lose information. Return format is consistent with what the instruction said “a decomposition is lossless if the common attributes of a super key for one of the tables”.


private static AttributeSet closure(AttributeSet attrs, Set<FunctionalDependency> fds)
In order to find the total set of attributes implied by attributes.


At this time, there is no bug found in our code.

