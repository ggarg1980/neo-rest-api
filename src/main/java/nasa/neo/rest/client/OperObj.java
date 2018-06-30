package nasa.neo.rest.client;

public class OperObj
{


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayMsg() {
		return displayMsg;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	protected String name;
	protected String displayMsg;
	
	protected Double numValue = new Double(-1);
	public Double getNumValue() {
		return numValue;
	}
	public void setNumValue(Double numValue) {
		this.numValue = numValue;
	}
	protected String path;
	protected String leafNode;
	protected String resultKey;
	public String getResultKey() {
		return resultKey;
	}
	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
	protected String operation;
	protected Integer index=0;
	protected String[] pathArr;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
		if(this.path.startsWith(IConstants.PATHSPLITTER))
			this.path = this.path.replaceFirst(IConstants.PATHSPLITTER, IConstants.EMPTYSTR);
		pathArr =  this.path.split(IConstants.PATHSPLITTER);
		leafNode = pathArr[pathArr.length-1];
	}
	public String getLeafNode() {
		return leafNode;
	}
	public void setLeafNode(String leafNode) {
		this.leafNode = leafNode;
	}
//	public JsonObject getJsonObjResult() {
//		return jsonObjResult;
//	}
//	public void setJsonObjResult(JsonObject jsonObjResult) {
//		this.jsonObjResult = jsonObjResult;
//	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String[] getPathArr() {
		return pathArr;
	}
	public void setPathArr(String[] pathArr) {
		this.pathArr = pathArr;
	}
	
	
	
}
