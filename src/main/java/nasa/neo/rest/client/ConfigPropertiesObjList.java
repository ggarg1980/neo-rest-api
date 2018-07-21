package nasa.neo.rest.client;

import java.util.List;
import java.util.Properties;

/** This is wrapper object class, contains all the objects which are created after reading property file
 * @author GGARG
 *
 */
/**
 * @author GGARG
 *
 */
public class ConfigPropertiesObjList {
	/**
	 * This variable is used to store properties from configuration file e.g. config.properties 
	 */
	protected Properties prop; 

	/**
	 * This variable is used to store parent object properties e.g.
	 *  nasa.neo.rest.parent.node=neo_reference_id
	 *  nasa.neo.rest.parent.name=NEO Reference ID
	 */
	protected OperObj parentObj; 
	/**
	 *  This variable is used to store countElement Object e.g.
	 *  nasa.neo.rest.elementcount.path=element_count
	 *  nasa.neo.rest.elementcount.displaymsg=The total number of Near Earth Objects (NEO) are {0}.
	 */
	protected OperObj countElement; 
	/**
	 * This variable is used to store list of children for which result operations needs to be performed for example
	 * nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
	 * nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
	 * nasa.neo.rest.child.1.operation=smallest
	 * nasa.neo.rest.child.1.displaymsg=Displaying closest object to earth, details as below
	 * nasa.neo.rest.child.1.displayunitmsg=Miss Distance (in Kilometers) 
	 */
	protected List<OperObj> childObjList; 
	/**
	 * This variable is used to store consolidated error message string  
	 */
	protected StringBuilder errorMsg; 
	
	/** 
	 * @return - Getter for prop parameter
	 */
	public  Properties getProp() {
		return prop;
	}

	/** 
	 * @return - Getter for parentObj parameter
	 */
	public OperObj getParentObj() {
		return parentObj;
	}

	/** 
	 * @return - Getter for childObjList parameter
	 */
	public List<OperObj> getChildObjList() {
		return childObjList;
	}

	/** 
	 * @return - Getter for countElement parameter
	 */
	public OperObj getCountElement() {
		return countElement;
	}
	/**
	 * @param prop - Setter for prop parameter
	 */
	public void setProp(Properties prop) {
		this.prop = prop;
	}

	/**
	 * @param parentObj - Setter for parentObj parameter
	 */
	public void setParentObj(OperObj parentObj) {
		this.parentObj = parentObj;
	}
	/**
	 * @param countElement - Setter for count element parameter
	 */
	public void setCountElement(OperObj countElement) {
		this.countElement = countElement;
	}
	/**
	 * @param childObjList - Setter for child object list
	 */
	public void setChildObjList(List<OperObj> childObjList) {
		this.childObjList = childObjList;
	}
	
}
