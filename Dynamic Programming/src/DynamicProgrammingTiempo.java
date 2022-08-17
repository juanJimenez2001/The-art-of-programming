import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Pair{
	int f;
	int h;
	public Pair(int f, int h) {
		this.f=f;
		this.h=h;
	}
}

public class DynamicProgrammingTiempo {
	
	public static void solve(int[] arr, int[] index) {
		char[] sol=new char[arr.length];
		List<Pair> list = new ArrayList<Pair>();
		int f=arr[0];
		int h=arr[0]*2;
		Pair p=new Pair(f,h);
		list.add(p);
		for(int i=1; i<arr.length; i++) {
			int a=f;
			f=arr[i]+h;
			h=Math.min(arr[i]*2+a,arr[i]*2+h);
			list.add(new Pair(f,h));
		}
		int minTime=Math.min(f, h);
		int time=minTime;
		for(int i=sol.length-1; i>=0; i--) {
			if(time==list.get(i).h) {
        		time=time-arr[i]*2;
		        sol[i]='H';
        	}
        	else {
        		time=time-arr[i];
    	        sol[i]='F';
        	}
		}
		System.out.println(toString(sol, index, minTime));
	}
	
	public static String toString(char[] car, int[] index, int time) {
		String s="    "+time+"\n";
		for(int i=0; i<index.length; i++) {
			s=s+"    "+index[i];
		}
		s=s+"\n";
		for(int i=0; i<index.length; i++) {
			s=s+"    "+car[i];
		}
		return s;
	}
	
	public static void main(String [] argcs) {
		Scanner sc=new Scanner(System.in);
		int x=sc.nextInt();
		int y=sc.nextInt();
		while(x!=0 && y!=0) {
			int[][] mat= new int [x][y];
			for(int i=0; i<x; i++) {
				for(int j=0; j<y; j++) {
					mat[i][j]=sc.nextInt();
				}
			}
			int[] arr=new int [y];
			int[] index=new int [y];
			for(int j=0; j<y; j++) {
				int aux=mat[0][j];
				int n=0;
				for(int i=1; i<x; i++) {
					if(aux>mat[i][j]) {
						aux=mat[i][j];
						n=i;
					}
				}
				arr[j]=aux;
				index[j]=n;
			}
			solve(arr, index);
			x=sc.nextInt();
			y=sc.nextInt();
			System.out.println();
		}
		sc.close();
	}
}
