import java.util.Random;


public class Particle
{
	public double x_position;
	public double y_position;
	public double z_position;
	
	public double x_velocity;
	public double y_velocity;
	public double z_velocity;
	public double mass;
	
	Particle(Random r, double speed, double radius, double m)
	{
		this.mass = m;
		double tempx;
		double tempy;
		double tempz;
		do{
		tempx = (r.nextDouble()*(2*radius-1)-radius);
		tempy = (r.nextDouble()*(2*radius-1)-radius);
		tempz = (r.nextDouble()*(2*radius-1)-radius);
		}while(Math.sqrt((Math.pow(tempx, 2))+(Math.pow(tempy,2)+Math.pow(tempz,2)))<radius);
		
		this.x_position = tempx;
		this.y_position = tempy;
		this.z_position = tempz;
		this.x_velocity = speed;
		this.y_velocity = speed;
		this.z_velocity = speed;
	}
	
	public double max()
	{
		return Math.max(Math.max(x_velocity, y_velocity), z_velocity);
	}
	
	public void wallColide()
	{
		double norm = getNorm();
		double vel =  Math.abs(x_velocity*(x_position/norm))
					+ Math.abs(y_velocity*(y_position/norm))
					+ Math.abs(z_velocity*(z_position/norm));
		IGLSim.incTotalMomentum(mass*vel);
		double dot_x = -x_position*x_velocity;
		double dot_y = -y_position*y_velocity;
		double dot_z = -z_position*z_velocity;
		
		double n_x = dot_x*-x_position;
		double n_y = dot_y*-y_position;
		double n_z = dot_z*-z_position;
		
		x_velocity = n_x - x_velocity;
		y_velocity = n_y - y_velocity;
		z_velocity = n_z - z_velocity;
	}
	
	public double getNorm()
	{
		return Math.sqrt(
				Math.pow(x_position,2)
				+Math.pow(y_position,2)
				+Math.pow(z_position,2)
				);
	}
}