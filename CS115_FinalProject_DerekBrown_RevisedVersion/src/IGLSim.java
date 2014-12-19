import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
public class IGLSim 
{
	public static Calendar cal = Calendar.getInstance();
	//what we're looking for
	private static double pressure;
	private static double R;
	
	//what we are given
	public static double volumeUnit;
	private static int numParticles;
	private static double massParticles;
	private static double tempK;
	private static double molarMass;
	
	//what we have
	private static Random r_temp;
	private static int samplingRate = (int) Math.floor(Math.pow(10, 6));
	private static int time;
	private static double totalMomentum;
	
	//constants
	private static double boltzmann = 1.3806488 * Math.pow(10,-23);//boltzmann's constant
	private static double N_A = 6.02214129*Math.pow(10,23);//avagadro's number
	private static double true_R = 8.314462;
	//what we calculate
	private static double aveSpeed;
	
	//storage
	static ArrayList<Particle> particles;
	
	//Args:
	//1: Container radius in integers (in picometers)
	//2: # particles
	//3: mass of each particle in kg 
	//4: temperature, in K
	//5: the molar mass of the particles in kg
	public static void main(String[] args)
	{
		volumeUnit = Double.valueOf(args[1]);
		numParticles = Integer.valueOf(args[2]);
		massParticles = Double.valueOf(args[3]);
		tempK = Double.valueOf(args[4]);
		molarMass = Double.valueOf(args[5]);
		
		time = 0;
		r_temp = new Random();
		particles = new ArrayList<Particle>(numParticles);
		aveSpeed = Math.pow(10,10)*Math.sqrt(3*tempK*boltzmann);
		totalMomentum = 0;
		simInit();
		boolean cont = true;
		while(cont)
		{
			step();
			cont = step();
			if(time%samplingRate == 1)
			{
				pressure = calcPressure();
				R = calcR();
				System.out.println("Time:" + time +" :: "+ R + " :: "+ pressure);
			}
			time++;
		}
	}

	public static void simInit()
	{
		System.out.println("Simulation Start");
		for(int i = 0; i < numParticles;i++)
		{
			particles.add(new Particle(r_temp, aveSpeed, volumeUnit, massParticles));
		}
	}
	
	public static boolean step()//basic round of operations
	{
		for(Particle p: particles)
		{
			new Movement(p).move();
		}
		
		return true;
	}
	
	
	
	public static void partColide(Particle alpha, Particle beta)
	{
		//c
		double c_velocity_x = ((alpha.mass*alpha.x_velocity + beta.mass*beta.x_velocity)/(alpha.mass+beta.mass));
		double c_velocity_y = ((alpha.mass*alpha.y_velocity + beta.mass*beta.y_velocity)/(alpha.mass+beta.mass));
		double c_velocity_z = ((alpha.mass*alpha.z_velocity + beta.mass*beta.z_velocity)/(alpha.mass+beta.mass));
		
		//1 temps
		double v1x = (alpha.mass/(alpha.mass+beta.mass))*(alpha.x_velocity-beta.x_velocity)-c_velocity_x;
		double v1y = (alpha.mass/(alpha.mass+beta.mass))*(alpha.y_velocity-beta.y_velocity)-c_velocity_y;
		double v1z = (alpha.mass/(alpha.mass+beta.mass))*(alpha.z_velocity-beta.z_velocity)-c_velocity_z;
		
		//2 temps
		double v2x = (beta.mass/(alpha.mass+beta.mass))*(beta.x_velocity-alpha.x_velocity)-c_velocity_x;
		double v2y = (beta.mass/(alpha.mass+beta.mass))*(beta.y_velocity-alpha.y_velocity)-c_velocity_y;
		double v2z = (beta.mass/(alpha.mass+beta.mass))*(beta.z_velocity-alpha.z_velocity)-c_velocity_z;
			
		//inelastic collision velocity adjustments
		double max = Math.max(alpha.max(), beta.max());
		double dx = r_temp.nextDouble()*(max*2)-max;
		double dy = r_temp.nextDouble()*(max*2)-max;
		double dz = r_temp.nextDouble()*(max*2)-max;
		
		//final setting
		alpha.x_velocity = v1x + dx;
		beta.x_velocity = v2x - dx;
		alpha.y_velocity = v1y + dx;
		beta.y_velocity = v2y - dx;
		alpha.z_velocity = v1z + dx;
		beta.z_velocity = v2z - dx;
	}

	
	public static void incTotalMomentum(Double momentum)
	{
		totalMomentum += momentum;
	}
	
	public static double calcR()
	{
		double r = 1;
		r = pressure;
		r = r*Math.PI;
		r = r*3;
		r = r*volumeUnit;
		double n = numParticles/N_A;
		r = r/n;
		r = r/tempK;
				//(Math.PI*3*volumeUnit/((numParticles/N_A)*tempK));
		return r;
	}
	
	private static double calcPressure() {
		// TODO Auto-generated method stub
		return totalMomentum*massParticles/(time*Math.PI*3*volumeUnit);
	}
	
	private static double variance()
	{
		return Math.abs(true_R - R);
	}
	
	
}
//EOF