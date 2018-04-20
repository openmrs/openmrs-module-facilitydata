package org.openmrs.module.facilitydata.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the data entered for a particular form, for a particular location and period
 */
public class FacilityDataReport extends BaseOpenmrsData {
	
	//***** PROPERTIES *****
	
    private FacilityDataFormSchema schema;
    private Location location;
    private Date startDate;
    private Date endDate;
    private Map<FacilityDataFormQuestion, FacilityDataValue> values;
    
    //***** CONSTRUCTORS *****
    
    public FacilityDataReport() {}
    
    //***** INSTANCE METHODS *****
    
    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FacilityDataReport)) return false;

        FacilityDataReport that = (FacilityDataReport) o;
        
        if (!this.getSchema().equals(that.getSchema())) return false;
        if (!this.getLocation().equals(that.getLocation())) return false;
        if (!this.getStartDate().equals(that.getStartDate())) return false;
        if (!this.getEndDate().equals(that.getEndDate())) return false;
        
        return true;
    }

    /**
     * @see Object#hashCode()
     */
	@Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + (getSchema() != null ? getSchema().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }

    //***** PROPERTY ACCESS *****

    /**
	 * @return the schema
	 */
	public FacilityDataFormSchema getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(FacilityDataFormSchema schema) {
		this.schema = schema;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
    /**
     * @param value the value to add
     */
	public void addValue(FacilityDataValue value) {
		getValues().put(value.getQuestion(), value);
	}
	
	/**
	 * @return the value for the passed question
	 */
	public FacilityDataValue getValue(FacilityDataFormQuestion question) {
		return getValues().get(question);
	}

	/**
	 * @return the values
	 */
	public Map<FacilityDataFormQuestion, FacilityDataValue> getValues() {
		if (values == null) {
			values = new HashMap<FacilityDataFormQuestion, FacilityDataValue>();
		}
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(Map<FacilityDataFormQuestion, FacilityDataValue> values) {
		this.values = values;
	}

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public void setId(Integer id) {

	}
}
