package santeclair.portal.vaadin.deprecated.module;

@Deprecated
public class ModuleMapKey implements Comparable<ModuleMapKey> {

	private final String code;
	private final Integer order;

	public ModuleMapKey(String code, Integer order) {
		this.code = code;
		this.order = order;
	}

	/**
	 * M�thode de lecture de l'attribut : {@link ModuleMapKey#code}
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * M�thode de lecture de l'attribut : {@link ModuleMapKey#order}
	 * 
	 * @return order
	 */
	public Integer getOrder() {
		return order;
	}

	@Override
	public int compareTo(ModuleMapKey o) {
		int compareResult = 0;
		if (this.getOrder() != null) {
			compareResult = this.getOrder().compareTo(o.getOrder());
		}
		// C'est fait expr�s de planter si code null
		if (compareResult == 0) {
			compareResult = this.getCode().compareTo(o.getCode());
		}
		return compareResult;
	}

}
