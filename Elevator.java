import java.util.Arrays;
public class Elevator{
	private int[][] user_inf=new int [1000][4]; //等待搭電梯的人 0:在哪一樓層等電梯 1:要往哪一樓層 2:方向(上或下) 3:為第幾名乘客
	private int[][] in_Ele = new int [1000][4]; //在電梯裡的人 後面同上
 	int Waiting=0,Floor_Num=0,index=0,num_in_ele=0;
	int now_floor=1,Direction=0,count=0;
	public synchronized void setConsumer(int[] arr){
		user_inf[Waiting][0]=arr[0];
		user_inf[Waiting][1]=arr[1];
		user_inf[Waiting][2]=arr[2];
		user_inf[Waiting][3]=index;
		System.out.println("Set! "+user_inf[Waiting][0]+" "+user_inf[Waiting][1]+" "+user_inf[Waiting][2]+" "+user_inf[Waiting][3]);

		Waiting++;
		index++;
	}
	public void setFloor(int a){
		Floor_Num=a;
	}
	public int getFloor(){
		return Floor_Num;
	}
	public synchronized boolean go_up(){ //檢查上面的人有沒有要搭電梯
		for(int i=0;i<Waiting;i++){
			if(user_inf[i][0]>=now_floor){
				return true;
			}
		}
		return false;
	}
	public synchronized boolean go_down(){//檢查下面的人有沒有要搭電梯
		for(int i=0;i<Waiting;i++){
			if(user_inf[i][0]<=now_floor){
				return true;
			}
		}
		return false;
	}
	public synchronized void check_out(){//有沒有人要下電梯
		boolean get_off=false;
		for(int i=0;i<num_in_ele;i++){
			//System.out.println(now_floor+" "+user_inf[i][0]+" "+user_inf[i][2]+" "+Direction);
			if(in_Ele[i][1]==now_floor){
				System.out.println("Number "+in_Ele[i][3]+" get off elevator");
				for(int j=i;j<=num_in_ele;j++){
					 System.arraycopy(in_Ele[j+1],0,in_Ele[j],0,4);
				}
				num_in_ele--;
				get_off=true;
				i=-1;
			}
		}
		if(get_off==true){
				sleep(500);
			}
	}
	public synchronized void up_in(){//往上的人有沒有要進電梯
		boolean get_in=false;
		for(int i=0;i<Waiting;i++){
		//	System.out.println(now_floor+" "+user_inf[i][0]+" "+user_inf[i][2]+" "+Direction);
			if(user_inf[i][0]==now_floor&&user_inf[i][2]==Direction/*!!!*/){
				System.out.println("Number "+user_inf[i][3]+" get in elevator");
				System.arraycopy(user_inf[i],0,in_Ele[num_in_ele],0,4);
				num_in_ele++;
				for(int j=i;j<Waiting;j++){
					System.arraycopy(user_inf[j+1],0,user_inf[j],0,4);
				}
				get_in=true;
				Waiting--;
				i=-1;
		}
		if(get_in==true){
				sleep(500);
			}		
		}
	}

	public synchronized void down_in(){//往下的人有沒有要進電梯
		for(int i=0;i<Waiting;i++){
		//	System.out.println(now_floor+" "+user_inf[i][0]+" "+user_inf[i][2]+" "+Direction);
			if(user_inf[i][0]==now_floor&&user_inf[i][2]==Direction/*!!!*/){
				System.out.println("Number "+user_inf[i][3]+" get in elevator");
				System.arraycopy(user_inf[i],0,in_Ele[num_in_ele],0,4);
				num_in_ele++;
				for(int j=i;j<Waiting;j++){
					System.arraycopy(user_inf[j+1],0,user_inf[j],0,4);
				}
				Waiting--;
				i=-1;	
			}
		}
	}

	public synchronized boolean up_user(){//檢查上面的人有沒有要搭電梯
		for(int i=0;i<Waiting;i++){
			if(user_inf[i][0]>now_floor){
				return true;
			}
		}
		return false;
	}

	public synchronized boolean down_user(){//檢查下面的人有沒有要搭電梯
		for(int i=0;i<Waiting;i++){
			if(user_inf[i][0]<now_floor){
				return true;
			}
		}
		return false;
	}
	public void start(){
		while(true){
		if(Direction==0){
			if(Waiting==0){
				//System.out.println("Elevator stay at "+now_floor+" floor");
				sleep(1000);
			}
			else{
				if(go_up()==true){ //如果上面有人要搭電梯					
					Direction=1;
				}
				else if(go_down()==true){ //如果下面有人要搭電梯
					Direction=-1;
				}
				else{
					sleep(1000);
				}
			}
		}
		else if(Direction==1){ 
		//	System.out.println("Elevator arrive at "+now_floor+" floor (go up)");
			check_out();//check if anyone arrive their floor
			if(go_up()==true){ //上面有人要搭電梯的話 確認該樓層是否有人要搭電梯
				System.out.println("go_up");
				up_in();
				if(num_in_ele==0&&up_user()==false){
					Direction=-1;
					down_in();
				}	
			}
			else if(Waiting==0&&num_in_ele==0){ //沒有人在等待電梯 電梯裡也沒有人 電梯休息	
				//now_floor--;
				Direction=0;
			}
			else if(go_up()==false&&down_user()==true&&num_in_ele==0){
				System.out.println("ooo");
				Direction=-1;
			}
		//System.out.println(Direction+" "+now_floor);
			now_floor+=Direction;
			if(now_floor>=Floor_Num){
				now_floor=Floor_Num;
				Direction=-1;
			}
		}
		else if(Direction==-1){
		//	System.out.println("Elevator arrives at "+now_floor+" floor (go down)");
			check_out();
			if(go_down()==true){ //如果下面有人要搭電梯
				System.out.println("go_down");
				down_in();
				if(num_in_ele==0&&down_user()==false){
					Direction=1;
					up_in();
				}
			}
			else if(Waiting==0&&num_in_ele==0){ //沒有人在等電梯 而且電梯裡沒有人 電梯休息
				Direction=0;
				//now_floor++;
			}
			else if(go_down()==false&&up_user()==true&&num_in_ele==0){
				//System.out.println("ooo");
				Direction=1;
			}
			now_floor+=Direction;
			if(now_floor<=1){
				Direction=1;
				now_floor=1;
			}
			System.out.println(Direction+" "+now_floor);  
		}
		Print();
		count++;
		sleep(1000); 
		}	
	}
	public void Print(){
		System.out.println("******************");
		System.out.println("Time: "+count);
		for(int i=0;i<Floor_Num;i++){
			if(i!=now_floor-1){
				System.out.print(" ");
			}
			else
				System.out.print("E");
		}
		System.out.print("\n");
		for(int i=0;i<Floor_Num;i++){
			System.out.print("-");
		}
		System.out.print("\n");
		System.out.println("Passanger(in elevator):");
		for(int i=0;i<num_in_ele;i++){
			System.out.println("Number "+in_Ele[i][3]+" from "+in_Ele[i][0]+" to "+in_Ele[i][1]);
		}
		System.out.println("Waiting in line:");
		for(int i=0;i<Waiting;i++){
			System.out.print("Number "+user_inf[i][3]+" wait at "+user_inf[i][0]+" floor");
			if(user_inf[i][2]==1){
				System.out.println("(up)");
			}
			else{
				System.out.println("(down)");
			}

		}
		System.out.println("******************");
	}
	public void sleep(int a){
		try {
			Thread.sleep(a);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}