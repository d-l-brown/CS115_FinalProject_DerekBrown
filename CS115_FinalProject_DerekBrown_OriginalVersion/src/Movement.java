
public class Movement 
{
	private int x_count;
	private int y_count;
	private int z_count;
	
	public Particle part;
	
	public Movement(Particle p)
	{
		this.x_count = (int) Math.floor(Math.abs(p.x_velocity));
		this.y_count = (int) Math.floor(Math.abs(p.y_velocity));
		this.z_count = (int) Math.floor(Math.abs(p.z_velocity));
		this.part = p;
	}
	
	public boolean move()
	{
		if(x_count > 0)
		{
			if(part.x_velocity > 0)
			{
				part.x_position++;
				x_count--;
			}
			else if(part.x_velocity < 0){part.x_position--; x_count--;}
		}
			
		if(y_count > 0)
		{
			if(part.y_velocity > 0)
			{
				part.x_position++;
				y_count--;
			}
			else if(part.y_velocity < 0){part.y_position--;y_count--;}
		}
		
		if(z_count > 0)
		{
			if(part.z_velocity > 0)
			{
				part.z_position++;
				z_count--;
			}
			else if(part.z_velocity < 0){part.z_position--;z_count--;}
		}	
		
		if(x_count == 0 && y_count == 0 && z_count == 0)
			return false;
		else return true;
	}
}
