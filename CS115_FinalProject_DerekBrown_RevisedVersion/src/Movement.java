
public class Movement 
{
	private double x_count;
	private double y_count;
	private double z_count;
	
	public Particle part;
	
	public Movement(Particle p)
	{
		this.x_count = (Math.abs(p.x_velocity));
		this.y_count = (Math.abs(p.y_velocity));
		this.z_count = (Math.abs(p.z_velocity));
		this.part = p;
	}
	
	
	
	public void move()
	{
		double m = Math.sqrt(
				Math.pow(part.x_position+x_count,2)
				+Math.pow(part.y_position+y_count,2)
				+Math.pow(part.z_position+z_count,2)
				);
		if(m>=IGLSim.volumeUnit)
		{
			//using dot product to produce interior angle to container
			double a = part.getNorm();
			double b = IGLSim.volumeUnit;
			double ANG_B = Math.acos(part.dot()/(part.getNorm()*part.getVectorUnit()));
			double ANG_A = Math.asin(a*Math.sin(ANG_B)/b);
			double ANG_C = 180 - (ANG_A+ANG_B);
			double c = Math.sin(ANG_C)*a/Math.sin(ANG_A);
			double ratio = c/m;
			//using * to produce distance from curr. postion to container
			if(Double.isNaN(ratio))
			{}
			else
			{
			x_count = x_count*ratio;
			y_count = y_count*ratio;
			z_count = z_count*ratio;
			part.wallColide();
			this.move();
			}
		}
		else
		{
			part.x_position += x_count;
			part.y_position += y_count;
			part.z_position += z_count;
		}
//		if(x_count > 0)
//		{
//			if(part.x_velocity > 0)
//			{
//				part.x_position++;
//				x_count--;
//			}
//			else if(part.x_velocity < 0){part.x_position--; x_count--;}
//		}
//			
//		if(y_count > 0)
//		{
//			if(part.y_velocity > 0)
//			{
//				part.x_position++;
//				y_count--;
//			}
//			else if(part.y_velocity < 0){part.y_position--;y_count--;}
//		}
//		
//		if(z_count > 0)
//		{
//			if(part.z_velocity > 0)
//			{
//				part.z_position++;
//				z_count--;
//			}
//			else if(part.z_velocity < 0){part.z_position--;z_count--;}
//		}	
//		
//		if(x_count == 0 && y_count == 0 && z_count == 0)
//			return false;
//		else return true;
	}
}
