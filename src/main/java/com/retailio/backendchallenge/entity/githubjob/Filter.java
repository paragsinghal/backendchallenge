package com.retailio.backendchallenge.entity.githubjob;

public class Filter {

	private FilterType filterType;
	private String value;
	
	public FilterType getFilterType() {
		return filterType;
	}
	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Filter [filterType=" + filterType + ", value=" + value + "]";
	}
	
}
