
public class Fibonacci {
	
	public static long algoritmoRecurs(int n, long[] val) {
		if(val[n]!=0)
			return val[n];
		else if(n==1 || n==2)
			val[n]=1;
		else
			val[n]=algoritmoRecurs(n-1, val)+algoritmoRecurs(n-2, val);
		return val[n];
	}
	
	public static long algoritmoNOrecurs(int n) {
		if(n==1 || n==2)
			return 1;
		long[] val=new long[n+1];
		val[1]=1;
		val[2]=1;
		for(int i=3; i<=n; i++) {
			val[i]=val[i-1]+val[i-2];
		}
		return val[n];
	}
	
	
	public static void main(String [] argcs) {
		System.out.println(algoritmoRecurs(7849, new long[7850]));
		System.out.println(algoritmoNOrecurs(7849));
	}
}
