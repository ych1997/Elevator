import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Test{
	public static void main(String args[]){
		Elevator elevator = new Elevator();

		Scanner scan = new Scanner(System.in);
		/*System.out.println("How many floors ?");
		int Floor_Num=scan.nextInt();
		while(Floor_Num<2){
			System.out.println("Please enter the floors which is bigger than 2");
			System.out.print("How many floors ? ");
			Floor_Num=scan.nextInt();
		}
		*/
		elevator.setFloor(6);

		Thread ProducerThread = new Thread(new Producer(elevator));

		Thread ConsumerTread = new Thread(new Consumer(elevator));
		ProducerThread.start();
		ConsumerTread.start();
	}
}
