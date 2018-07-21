package nasa.neo.rest.client;

/** This class acts as POJO and used to store data related for parent, child objects. Also it stores reference to result NEOs.
 * @author GGARG
 *
 */
public class OperObj
{
	/**
	 * This variable is used to store the name of an object e.g CLOSEST_OBJECT_TO_EARTH
	 */
	private String name; 
	/**
	 * This variable is used to store display message of an object e.g Displaying closest object to earth, details as below
	 */
	private String displayMsg;  
	/**
	 * This variable is used to store operation that needs to be performed e.g. smallest/largest
	 */
	private String operation; 
	/**
	 * This array is used store relative path till leaf node e.g close_approach_data/miss_distance/kilometers is broken into String array 
	 * where pathArr[0] = close_approach_data, pathArr[1] = miss_distance, pathArr[2] = kilometers
	 */
	private String[] pathArr;   
	/**
	 * This variable is used to store the complete path, read from property e.g close_approach_data/miss_distance/kilometers
	 */
	private String path; 
	/**
	 * This variable is used to store leafNode (path is not included) e.g kilometers from close_approach_data/miss_distance/kilometers
	 */
	private String leafNode;  
	/**
	 * This variable is used to store the NEO Reference ID for resultant object
	 */
	private String resultKey; 
	/**
	 * This variable is used to store the value of the resultant object based on leaf node
	 */
	private Double numValue = new Double(-1); 
	/**
	 * This variable is used to traverse the pathArr
	 */
	private Integer index=0;  

	/**
	 * This variable is used to store display unit message of an object e.g Diameter (in Kms)
	 */
	private String displayUnitMsg;  
	
	/**
	 * @return - Getter for displayUnitMsg
	 */
	public String getDisplayUnitMsg() {
		return displayUnitMsg;
	}
	/**
	 * @param displayUnitMsg - Setter for displayUnitMsg
	 */
	public void setDisplayUnitMsg(String displayUnitMsg) {
		this.displayUnitMsg = displayUnitMsg;
	}
	/** 
	 * @return - Getter for name parameter
	 */
	public String getName() {
		return name;
	}
	/** 
	 * @param name - Setter for prop parameter
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** 
	 * @return - Getter for displayMsg parameter
	 */
	public String getDisplayMsg() {
		return displayMsg;
	}
	/** 
	 * @param displayMsg - Setter for displayMsg parameter
	 */
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	/** 
	 * @return - Getter for numValue parameter
	 */
	public Double getNumValue() {
		return numValue;
	}
	/** 
	 * @param numValue - Setter for numValue parameter
	 */
	public void setNumValue(Double numValue) {
		this.numValue = numValue;
	}
	/** 
	 * @return - Getter for resultKey parameter
	 */
	public String getResultKey() {
		return resultKey;
	}
	/** 
	 * @param resultKey - Setter for resultKey parameter
	 */
	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
	/** 
	 * @return - Getter for path parameter
	 */
	public String getPath() {
		return path;
	}
	/**  
	 * @param path - Setter for path parameter. This method is also used to pathArr and leafNode.
	 */
	public void setPath(String path)
	{
		this.path = path;
		// Check if path is starting with / remove it
		if(this.path.startsWith(IConstants.PATHSPLITTER))
			this.path = this.path.replaceFirst(IConstants.PATHSPLITTER, IConstants.EMPTYSTR);
		// Set pathArr and leafNode from path variable
		pathArr =  this.path.split(IConstants.PATHSPLITTER);
		leafNode = pathArr[pathArr.length-1];
	}
	/** 
	 * @return - Getter for leafNode parameter
	 */
	public String getLeafNode() {
		return leafNode;
	}
	/** 
	 * @return - Getter for operation parameter
	 */
	public String getOperation() {
		return operation;
	}
	/** 
	 * @param operation - Setter for operation parameter
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/** 
	 * @return - Getter for index parameter
	 */
	public Integer getIndex() {
		return index;
	}
	/** 
	 * @return - Getter for pathArr parameter
	 */
	public String[] getPathArr() {
		return pathArr;
	}
}
