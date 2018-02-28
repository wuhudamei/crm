package com.rocoinfo.entity.callCentre;

import java.sql.Timestamp;


/**
 * 
 * 功能描述:客户成功呼入话务中心记录
 * @author phil
 * 2017年6月29日
 */


public class CustomerCallInLog {
    private Integer id;

    private String jlid;

    private String ivrsp;

    private String callid;

    private Timestamp starttime;

    private Timestamp endtime;

    private Timestamp startqtime;

    private Timestamp endqtime;

    private Integer servicetype;

    private String caller;

    private String agentid;

    private String agentext;

    private String groupid;

    private String trunkcallee;

    private Integer companyId;
    
    private String createUser;
    
    private Timestamp createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJlid() {
        return jlid;
    }

    public void setJlid(String jlid) {
        this.jlid = jlid == null ? null : jlid.trim();
    }

    public String getIvrsp() {
        return ivrsp;
    }

    public void setIvrsp(String ivrsp) {
        this.ivrsp = ivrsp == null ? null : ivrsp.trim();
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid == null ? null : callid.trim();
    }

    public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Timestamp getStartqtime() {
		return startqtime;
	}

	public void setStartqtime(Timestamp startqtime) {
		this.startqtime = startqtime;
	}

	public Timestamp getEndqtime() {
		return endqtime;
	}

	public void setEndqtime(Timestamp endqtime) {
		this.endqtime = endqtime;
	}

	public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller == null ? null : caller.trim();
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid == null ? null : agentid.trim();
    }

    public String getAgentext() {
        return agentext;
    }

    public void setAgentext(String agentext) {
        this.agentext = agentext == null ? null : agentext.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getTrunkcallee() {
        return trunkcallee;
    }

    public void setTrunkcallee(String trunkcallee) {
        this.trunkcallee = trunkcallee == null ? null : trunkcallee.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
    
}