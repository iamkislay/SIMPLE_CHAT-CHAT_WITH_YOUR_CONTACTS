package chat.app;

import java.util.Scanner;
import java.util.Stack;


public class test {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);

        int n = sc.nextInt();
        int arr[] = new int[n];
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int res[] = sol.asteroidCollision(arr);
        if(res.length == 0){
            System.out.println("-1");
        }
        else{
            for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]+" ");
        }
        }
        sc.close();
        
    }
}
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
    //   Write Code here
    
        Stack<Integer> s= new Stack<Integer>();
        for(int arrow:asteroids){
        	if(s.empty()) {
        		s.push(arrow);
        		
        	}
        	else if(s.peek() * arrow>=0) {
        		s.push(arrow);
        	}
        	else {
        		if(Math.abs(s.peek())<Math.abs(arrow)) {
        			s.pop();
        			s.push(arrow);
        		}
        		else if(Math.abs(s.peek())==Math.abs(arrow)) {
        			s.pop();
        		}
//        		else {
//        			s.push(arrow);
//        		}
        	}
            
        }
        Stack<Integer> temp= new Stack<Integer>();
        int size=s.size();
        for(int j=0;j<size;j++) {
        	temp.push(s.pop());
        }
        int[] result=new int[temp.size()];
        for(int i=0;i<size;i++) {
        	result[i]=temp.pop();
        }
        return result;
    }
}