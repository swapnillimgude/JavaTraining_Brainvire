package nestedPatterns;

public class Pattern_6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=1;i<=5;i++)
		{
			for(int j=1;j<=5-i+1;j++)
			{
			System.out.print("* ");
			//System.out.println(j+ " ");
			}
			System.out.println(" ");
		}
	}

}
