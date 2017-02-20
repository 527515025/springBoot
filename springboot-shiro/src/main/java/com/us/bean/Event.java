package com.us.bean;

import java.util.Date;


public class Event {
	private Integer id;
	private Integer rawEventId;
	private String host;
	private String ip;
	private String source;
	private String type;
	private Date startTime;
	private Date endTime;
	private String content;
	private String dataType;
	private String suggest;
	private Integer businessSystemId;
	private Integer departmentId;
	private String status;
	private Integer occurCount;
	private String owner;
	private Date responsedTime;
	private String responsedBy;
	private Date resolvedTime;
	private String resolvedBy;
	private Date closedTime;
	private String closedBy;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRawEventId() {
		return rawEventId;
	}
	public void setRawEventId(Integer rawEventId) {
		this.rawEventId = rawEventId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public Integer getBusinessSystemId() {
		return businessSystemId;
	}
	public void setBusinessSystemId(Integer businessSystemId) {
		this.businessSystemId = businessSystemId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOccurCount() {
		return occurCount;
	}
	public void setOccurCount(Integer occurCount) {
		this.occurCount = occurCount;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getResponsedTime() {
		return responsedTime;
	}
	public void setResponsedTime(Date responsedTime) {
		this.responsedTime = responsedTime;
	}
	public String getResponsedBy() {
		return responsedBy;
	}
	public void setResponsedBy(String responsedBy) {
		this.responsedBy = responsedBy;
	}
	public Date getResolvedTime() {
		return resolvedTime;
	}
	public void setResolvedTime(Date resolvedTime) {
		this.resolvedTime = resolvedTime;
	}
	public String getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public Date getClosedTime() {
		return closedTime;
	}
	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}
	@Override
	public String toString() {
		return "Event{" +
			"id=" + id +
			", rawEventId=" + rawEventId +
			", host=" + host +
			", ip=" + ip +
			", source=" + source +
			", type=" + type +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", content=" + content +
			", dataType=" + dataType +
			", suggest=" + suggest +
			", businessSystemId=" + businessSystemId +
			", departmentId=" + departmentId +
			", status=" + status +
			", occurCount=" + occurCount +
			", owner=" + owner +
			", responsedTime=" + responsedTime +
			", responsedBy=" + responsedBy +
			", resolvedTime=" + resolvedTime +
			", resolvedBy=" + resolvedBy +
			", closedTime=" + closedTime +
			", closedBy=" + closedBy +
			'}';
		}
}