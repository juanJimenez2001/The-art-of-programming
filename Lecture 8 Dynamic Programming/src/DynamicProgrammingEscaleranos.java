import java.util.Scanner;

public class DynamicProgrammingEscaleranos {

	public static long algoritmo(int n, int[] formas, long[] val) {
		if (n<0)  
			return 0;
		if (n==0) 
			return 1;
		if (val[n-1]==0) {
			for (int i=0; i<formas.length; i++) {
				val[n-1]=(val[n-1]+algoritmo(n-formas[i], formas, val))%1_000_000_007;
			}
		}
		return val[n-1];
	}

	public static void main(String [] argcs) {
		Scanner sc=new Scanner(System.in);
		int escaleras;
		int n;
		while (true) {
			escaleras=sc.nextInt();
			if(escaleras==0) 
				break;
			n=sc.nextInt();
			int[] formas=new int[n];
			for (int i=0; i<n; i++) {
				formas[i]=sc.nextInt();
			}
			System.out.println(algoritmo(escaleras, formas, new long[escaleras]));
		}
	}
}