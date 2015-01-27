package santeclair.portal.vaadin.deprecated.module.uri;

import java.util.StringTokenizer;

@Deprecated
public class ModuleUriFragment extends ContainerUriFragment {

	private static final long serialVersionUID = -7332768717923938254L;

	public static final String MODULES = "modules";

	private final String moduleCode;

	public ModuleUriFragment(String tabPosition, String moduleCode) {
		super(tabPosition);
		this.moduleCode = moduleCode;
	}

	public ModuleUriFragment(ContainerUriFragment containerUriFragment, String moduleCode) {
		super(containerUriFragment);
		this.moduleCode = moduleCode;
	}

	public ModuleUriFragment(ModuleUriFragment moduleUriFragment) {
		super(moduleUriFragment);
		this.moduleCode = moduleUriFragment.getModuleCode();
	}

	public String getModuleCode() {
		return moduleCode;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append(super.toString()).append(MODULES).append(SEPARATOR).append(this.getModuleCode()).append(SEPARATOR);
		return sb.toString();
	}

	public static ModuleUriFragment newModuleUriFragment(String uriFragment) {
		ContainerUriFragment containerUriFragment = ContainerUriFragment.newContainerUriFragment(uriFragment);
		String moduleCode = null;
		StringTokenizer stringTokenizer = new StringTokenizer(uriFragment, "/");
		int index = 1;
		while (stringTokenizer.hasMoreElements()) {
			String token = stringTokenizer.nextToken();
			switch (index) {
			case 4:
				moduleCode = token;
				break;
			}
			index++;
		}
		return new ModuleUriFragment(containerUriFragment, moduleCode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((moduleCode == null) ? 0 : moduleCode.hashCode());
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
		ModuleUriFragment other = (ModuleUriFragment) obj;
		if (moduleCode == null) {
			if (other.moduleCode != null)
				return false;
		} else if (!moduleCode.equals(other.moduleCode))
			return false;
		return true;
	}

}
