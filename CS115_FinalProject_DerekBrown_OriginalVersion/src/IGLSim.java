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
	private static double volumeUnit;
	private static int numParticles;
	private static double massParticles;
	private static double tempK;
	private static double molarMass;
	
	//what we have
	private static Random r_temp;
	private static int samplingRate = 10;
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
	//1: Container radius in integers (in angstroms)
	//2: # particles
	//3: mass of each particle in kg 
	//4: temperature, in K
	public static void main(String[] args)
	{
		volumeUnit = 20;//Double.valueOf(args[1]);
		numParticles = 10;//Integer.valueOf(args[2]);
		massParticles = 1.007825;//Double.valueOf(args[3]);
		tempK = 300;//Double.valueOf(args[4]);
		molarMass = 2.02*(10^(-3));//Double.valueOf(args[5]);
		
		time = 0;
		r_temp = new Random();
		particles = new ArrayList<Particle>(numParticles);
		aveSpeed = Math.pow(10,10)*Math.sqrt(3*tempK*boltzmann);
		totalMomentum = 0;
		simInit();
		//boolean cont = true;
		while(time < 100000000)
		{
			step();
			//cont = step();
			if(time%samplingRate == 1)
			{
				pressure = calcPressure();
				R = calcR();
				System.out.println("Time:" + time +" - "+ R + " :: "+ variance());
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
		System.out.println("Step::"+cal.getTime());
		ParticleKeeper movements = new ParticleKeeper();
		ParticleKeeper moved;
		ParticleKeeper notFinished;
		for(Particle p: particles)
		{
			boolean b = movements.add(new Movement(p));
			int i = 3;
		}
		
		while(!movements.isEmpty())
		{
			
			notFinished = new ParticleKeeper();
			moved = new ParticleKeeper();
			for(Movement m: movements)
			{
				boolean b = m.move();
				if(!moved.add(m));
				{
					partColide(m.part, m.part);
				}
				if(m.part.getNorm() >= (double)volumeUnit)
				{
					m.part.wallColide();
				}
				if(b)
					notFinished.add(m);
			}
			System.out.println("Movement::"+notFinished.size+"::"+cal.getTime());
			movements = notFinished;
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