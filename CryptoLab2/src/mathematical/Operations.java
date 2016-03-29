package mathematical;

import java.util.ArrayList;
import java.util.List;

public class Operations {
	
	// a*x = b mod(n)
	public static int[] findX(int a, int b, int n){
		if(a < 0)
			a = n + a;
		if(b < 0)
			b = n + b;
		if(gcd(a, n) == 1)
			return new int[]{(inverse(a, n) * b) % n};
		else 
			if(b % gcd(a,n) != 0)
				return new int[0];
			else{
				int d = gcd(a,n);
				int[] values = new int[d];
				
				int newA = a/d;
				int newB = b/d;
				int newN = n/d;
				
				values[0] = ( newB * inverse(newA, newN) ) % newN;
				
				for(int i = 1; i < values.length; i++)
					values[i] = values[i - 1] + newN;
				
				return values;
			}
	}
	
	public static int gcd(int a, int b){
		while(a != 0 && b != 0)
			if(a >= b)
				a = a % b;
			else 
				b = b % a;
		
		return a + b; 
	}
	
	//a^-1modb
	public static int inverse(int a, int b){
		int n = b;
		if(a < b){
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		List<Integer> q = new ArrayList<Integer>();
		List<Integer> p = new ArrayList<Integer>();
		p.add(1);
		
		while(b > 0){
			q.add(a/b);
			if(q.size() == 1)
				p.add(q.get(0) * p.get(0));
			else
				p.add(q.get(q.size()-1)*p.get(p.size()-1) + p.get(p.size()-2));
			
			int tmpB = b;
			b = a%b;
			a = tmpB;
		}
		int answer = p.get(p.size() - 2);
		
		if((p.size() - 2) % 2 == 1)
			answer = n - answer;
		
		return answer;
	}
}
