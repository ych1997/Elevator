import java.util.Random;
public class Consumer implements Runnable{
	private Elevator e;
	Random ran=new Random();
	public Consumer(Elevator e){
		this.e=e;
	}
	public void run(){
		while(true){
			int x=Poisson(5.0)*1000;
			//System.out.println(x);
		try{
			Thread.sleep(x);
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
public int Poisson(double lambda){
	double L = Math.exp(-lambda);
	double p =1.0;
	int k=0;
	while(true) {
		p=p*Math.random();
		k++;
		if(p<=L) {
			break;
		}
	}			
	//	System.out.println(Math.random()+" "+L);

	return k-1;
}

}