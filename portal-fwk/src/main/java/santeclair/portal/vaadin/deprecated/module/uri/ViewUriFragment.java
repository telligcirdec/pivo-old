package santeclair.portal.vaadin.deprecated.module.uri;

import java.util.StringTokenizer;

@Deprecated
public class ViewUriFragment extends ModuleUriFragment {

	private static final long serialVersionUID = 2284122706484667115L;

	public static final String VIEWS = "views";

	private String viewCode;

	public ViewUriFragment(ViewUriFragment viewUriFragment) {
		super(viewUriFragment);
		this.viewCode = viewUriFragment.getViewCode();
	}

	public ViewUriFragment(ModuleUriFragment moduleUriFragment, String viewCode) {
		super(moduleUriFragment);
		this.viewCode = viewCode;
	}

	public ViewUriFragment(String tabPosition, String moduleCode, String viewCode) {
		super(tabPosition, moduleCode);
		this.viewCode = viewCode;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append(super.toString()).append(VIEWS).append(SEPARATOR).append(viewCode);
		return sb.toString();
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public ModuleUriFragment getModuleUriFragment() {
		return ViewUriFragment.getModuleUriFragmentFromViewUriFragment(this);
	}

	public static ModuleUriFragment getModuleUriFragmentFromViewUriFragment(ViewUriFragment viewUriFragment) {
		return ModuleUriFragment.newModuleUriFragment(viewUriFragment.toString());
	}

	public static ViewUriFragment newViewUriFragment(ViewUriFragment uriFragmentToCopy, String viewCode) {
		ViewUriFragment viewUriFragment = new ViewUriFragment(uriFragmentToCopy);
		viewUriFragment.setViewCode(viewCode);
		return viewUriFragment;
	}

	public static ViewUriFragment newViewUriFragment(String uriFragment) {
		ModuleUriFragment moduleUriFragment = ModuleUriFragment.newModuleUriFragment(uriFragment);
		String viewCode = null;
		StringTokenizer stringTokenizer = new StringTokenizer(uriFragment, "/");
		int index = 1;
		while (stringTokenizer.hasMoreElements()) {
			String token = stringTokenizer.nextToken();
			switch (index) {
			case 6:
				viewCode = token;
				break;
			}
			index++;
		}
		return new ViewUriFragment(moduleUriFragment, viewCode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((viewCode == null) ? 0 : viewCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewUriFragment other = (ViewUriFragment) obj;
		if (viewCode == null) {
			if (other.viewCode != null)
				return false;
		} else if (!viewCode.equals(other.viewCode))
			return false;
		return true;
	}

}
