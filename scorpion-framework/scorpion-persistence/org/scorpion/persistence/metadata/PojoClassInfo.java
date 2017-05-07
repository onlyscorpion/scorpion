package org.scorpion.persistence.metadata;

import java.util.HashSet;
import java.util.Set;

/**
* 封装类信息
*
*/
public class PojoClassInfo {

	
	private String packagename;
	
	private String classname;
	
	private Set<String> imports;
	
	private PhysicalModel physicalModel;

	
	public PojoClassInfo() {
	}

	
	/**
	 * @param classname
	 * 
	 * @param imports
	 * 
	 * @param packagename
	 */
	public PojoClassInfo(String classname, Set<String> imports,String packagename) {
		super();
		this.classname = classname;
		this.imports = imports;
		this.packagename=packagename;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public Set<String> getImports() {
		
		if(imports == null)
			imports = new HashSet<String>();
		
		return imports;
	}

	public void setImports(Set<String> imports) {
		this.imports = imports;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public PhysicalModel getPhysicalModel() {
		return physicalModel;
	}

	public void setPhysicalModel(PhysicalModel physicalModel) {
		this.physicalModel = physicalModel;
	}
	
}
