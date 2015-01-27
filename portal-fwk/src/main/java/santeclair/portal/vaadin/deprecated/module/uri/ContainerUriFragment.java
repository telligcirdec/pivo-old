package santeclair.portal.vaadin.deprecated.module.uri;

import java.util.StringTokenizer;

@Deprecated
public class ContainerUriFragment extends UriFragment {

	private static final long serialVersionUID = -1282941154559211134L;

	public static final String CONTAINER = "container";
	public static final String NEW = "new";

	private final String containerId;

	public ContainerUriFragment(String containerId) {
		this.containerId = containerId;
	}

	public ContainerUriFragment(ContainerUriFragment containerUriFragment) {
		this.containerId = containerUriFragment.getContainerId();
	}

	public String getContainerId() {
		return containerId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append(CONTAINER).append(SEPARATOR).append(this.getContainerId()).append(SEPARATOR);
		return sb.toString();
	}

	public static ContainerUriFragment newContainerUriFragment(String uriFragment) {
		String containerId = null;
		StringTokenizer stringTokenizer = new StringTokenizer(uriFragment, "/");
		int index = 1;
		while (stringTokenizer.hasMoreElements()) {
			String token = stringTokenizer.nextToken();
			switch (index) {
			case 2:
				containerId = token;
				break;
			}
			index++;
		}
		return new ContainerUriFragment(containerId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContainerUriFragment other = (ContainerUriFragment) obj;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		return true;
	}

}
