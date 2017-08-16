
import static org.junit.Assert.*;
import java.util.*;

public class Test {

	@org.junit.Test
	public void depPresBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("a")));

		// tables
		// a
		// b
		// fds
		// a -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t1, new Attribute("b")));
		// tables
		// a
		// b
		// fds
		// a -> a
		// a -> b
		assertFalse(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void losslessBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		// tables
		// a
		// b
		// fds
		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		t1.add(new Attribute("b"));
		// tables
		// a b
		// b
		// fds
		assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}

	
	/** test cases derived from piazza**/
	@org.junit.Test
	public void depPresFDtest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("b")));

		// tables
		// a b
		// b
		// fds
		// ab -> b
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t2, new Attribute("a")));
		// tables
		// a
		// b
		// fds
		// ab -> b
		// b -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
	}
	
	
	@org.junit.Test
	public void lossLessPiazza0() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	}

	@org.junit.Test
	public void losslesstest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		t2.add(new Attribute("c"));
		t2.add(new Attribute("d"));
		
		AttributeSet temp = new AttributeSet();
		temp.add(new Attribute("b"));
		fds.add(new FunctionalDependency(temp,new Attribute("c")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		fds.add(new FunctionalDependency(temp, new Attribute("d")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		// c -> d
		assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	@org.junit.Test
	public void lossLessPiazza1() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t1.add(new Attribute("d"));
	   t2.add(new Attribute("a"));
	   t2.add(new Attribute("c"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   temp0.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("c")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("e")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("d")));
	   AttributeSet temp3 = new AttributeSet();
	   temp3.add(new Attribute("e"));
	   fds.add(new FunctionalDependency(temp3,new Attribute("a")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void lossLessPiazza2() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t2.add(new Attribute("a"));
	   t2.add(new Attribute("c"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void lossLessPiazza3() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t2.add(new Attribute("b"));
	   t2.add(new Attribute("c"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   assertFalse(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void lossLessPiazza4() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("c"));
	   t1.add(new Attribute("z"));
	   t2.add(new Attribute("s"));
	   t2.add(new Attribute("z"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("c"));
	   temp0.add(new Attribute("s"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("z")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("z"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("c")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}

	@org.junit.Test
	public void lossLessPiazza5() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t2.add(new Attribute("a"));
	   t2.add(new Attribute("d"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("c")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("c"));
	   temp2.add(new Attribute("d"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("e")));
	   AttributeSet temp3 = new AttributeSet();
	   temp3.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp3,new Attribute("d")));
	   AttributeSet temp4 = new AttributeSet();
	   temp4.add(new Attribute("e"));
	   fds.add(new FunctionalDependency(temp4,new Attribute("a")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}

	@org.junit.Test
	public void lossLessPiazza6() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t2.add(new Attribute("c"));
	   t2.add(new Attribute("d"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("c")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("c"));
	   temp2.add(new Attribute("d"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("e")));
	   AttributeSet temp3 = new AttributeSet();
	   temp3.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp3,new Attribute("d")));
	   AttributeSet temp4 = new AttributeSet();
	   temp4.add(new Attribute("e"));
	   fds.add(new FunctionalDependency(temp4,new Attribute("a")));
	   AttributeSet temp5 = new AttributeSet();
	   temp5.add(new Attribute("e"));
	   fds.add(new FunctionalDependency(temp5,new Attribute("a")));
	   assertFalse(FDChecker.checkLossless(t1, t2, fds));
	}

	@org.junit.Test
	public void lossLessPiazza7() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t2.add(new Attribute("c"));
	   t2.add(new Attribute("d"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   temp0.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("c")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("d")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("e")));
	   assertFalse(FDChecker.checkLossless(t1, t2, fds));
	}

	@org.junit.Test
	public void lossLessPiazza8() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t1.add(new Attribute("d"));
	   t2.add(new Attribute("a"));
	   t2.add(new Attribute("b"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   temp0.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("c")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("d")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("e")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}

	@org.junit.Test
	public void lossLessPiazza9() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t1.add(new Attribute("d"));
	   t2.add(new Attribute("b"));
	   t2.add(new Attribute("e"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   temp0.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("c")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("d")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("e")));
	   assertTrue(FDChecker.checkLossless(t1, t2, fds));
	}
	
	@org.junit.Test
	public void depPresPiazza0() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("b"));
	   t1.add(new Attribute("c"));
	   t2.add(new Attribute("c"));
	   t2.add(new Attribute("d"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("c")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("d")));
	   assertTrue(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void depPresPiazza1() {
	   AttributeSet t1 = new AttributeSet();
	   AttributeSet t2 = new AttributeSet();
	   Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
	   t1.add(new Attribute("a"));
	   t1.add(new Attribute("c"));
	   t1.add(new Attribute("d"));
	   t2.add(new Attribute("b"));
	   t2.add(new Attribute("c"));
	   AttributeSet temp0 = new AttributeSet();
	   temp0.add(new Attribute("a"));
	   fds.add(new FunctionalDependency(temp0,new Attribute("b")));
	   AttributeSet temp1 = new AttributeSet();
	   temp1.add(new Attribute("b"));
	   fds.add(new FunctionalDependency(temp1,new Attribute("c")));
	   AttributeSet temp2 = new AttributeSet();
	   temp2.add(new Attribute("c"));
	   fds.add(new FunctionalDependency(temp2,new Attribute("d")));
	   assertFalse(FDChecker.checkDepPres(t1, t2, fds));
	}
	
}
