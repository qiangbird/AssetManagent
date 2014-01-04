package com.augmentum.ams.model.enumeration;

public enum ProcessTypeEnum {
	
	SHARED("shared"),
	
	NONSHARE("nonShared");
	
	private String processType;
	
	private ProcessTypeEnum (String processType) {
		this.processType = processType;
	}
	public String getProcessType() {
    	return processType;
    }
	
	public static ProcessTypeEnum getProcessTypeEnum(String processType) {
		ProcessTypeEnum processTypeEnum = null;
		for(ProcessTypeEnum at: ProcessTypeEnum.values()) {
			if (at.getProcessType().equalsIgnoreCase(processType)) {
			    processTypeEnum = at;
	            break;
            }
		}
		return processTypeEnum;
	}
}
