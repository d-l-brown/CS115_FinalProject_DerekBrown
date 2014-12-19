import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ParticleKeeper implements Collection<Movement>
{
	private HashSet<Movement> set;
	public int size;
	public ParticleKeeper()
	{
		super();
		set = new HashSet<Movement>();
		this.size = 0;
	}

	public boolean add(Movement m) {
		// TODO Auto-generated method stub
		if(contains(m))
			return false;
		else set.add(m);
		this.size++;
		return true;
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean contains(Movement m) 
	{
		if(set.isEmpty())
			return false;
		for(Movement s: set)
		{
			if(s.part.x_position == m.part.x_position)
				if(s.part.y_position == m.part.y_position)
					if(s.part.z_position == m.part.z_position)
						return true;
		}
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size == 0;
	}
	
	@Override
	public Iterator<Movement> iterator() {
		// TODO Auto-generated method stub
		return set.iterator();
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		return this.size;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAll(Collection<? extends Movement> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
}