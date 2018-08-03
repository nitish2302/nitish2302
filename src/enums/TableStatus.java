package enums;

public enum TableStatus {
	
	A("Active"), 
	I("Inactive"), 
	
	;
	
	private String tableStatus;
	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
		
	public String getTableStatus() {
		return tableStatus;
	}

	

	TableStatus(String tableStatus)
	{
		this.tableStatus = tableStatus;
	}
	
   
}
