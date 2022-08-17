package aop.solution;
import aop.runner.TestObject;

public class Program {

	public static int findLowPoint(TestObject altitudes) {
		int high=altitudes.size()-1;
		int low=0;
		int mid=0;
		int midValue;
		boolean bool=false;
		while (!bool) {
			mid=(high+low)/2;
			midValue=altitudes.get(mid);
			if ((mid==0 || midValue<=altitudes.get(mid-1)) && (mid==altitudes.size()-1 || midValue<=altitudes.get(mid+1))) {
				bool=true;
				break;
			}
			else if (mid>0 && midValue>altitudes.get(mid-1))
				high=mid-1;
			else
				low=mid+1;
		}
		return mid;
	}
}
