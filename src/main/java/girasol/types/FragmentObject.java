package girasol.types;

import girasol.dom.Fragment;

public class FragmentObject extends Value {
	
	private Fragment fragment;
	
	public FragmentObject(Fragment fragment)
	{
		this.fragment = fragment;
	}

	@Override
	public String asString() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Fragment getFragment() {
		return fragment;
	}

}
