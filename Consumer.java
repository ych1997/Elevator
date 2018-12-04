import java.util.Random;
public class Consumer implements Runnable{
	private Elevator e;
	Random ran=new Random();
	public Consumer(Elevator e){
		this.e=e;
	}
	public void run(){
		while(true){
		try{
			double x=(2)* Math.log(ran.nextInt(3)+1);
			//System.out.println("x:"+x);
			Thread.sleep((long)x*2000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		int[] temp=new int [4];
		temp[0]=ran.nextInt(e.getFloor())+1;
		temp[1]=ran.nextInt(e.getFloor())+1;
		while(temp[0]==temp[1]){
			temp[1]=ran.nextInt(e.getFloor())+1;
		}
		if(temp[1]-temp[0]>0){
			temp[2]=1;
		}
		else{
			temp[2]=-1;
		}
		e.setConsumer(temp);

	}
}
}