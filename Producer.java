
public class Producer implements Runnable{
	private Elevator elevator;

	public Producer(Elevator elevator){
		this.elevator=elevator;
	}
	public void run(){
		
		elevator.start();
	
	}
}