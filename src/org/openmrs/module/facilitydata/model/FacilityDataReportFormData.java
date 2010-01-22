package org.openmrs.module.facilitydata.model;

import org.openmrs.Location;

import java.util.List;
import java.util.Date;

public class FacilityDataReportFormData {
    FacilityDataFormSchema schema;
    Location location;
    Date startDate;
    Date endDate;
    List<FacilityDataValue> values;

    public FacilityDataFormSchema getSchema() {
        return schema;
    }

    public void setSchema(FacilityDataFormSchema schema) {
        this.schema = schema;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<FacilityDataValue> getValues() {
        return values;
    }

    public void setValues(List<FacilityDataValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacilityDataReportFormData formData = (FacilityDataReportFormData) o;

        if (!endDate.equals(formData.endDate)) return false;
        if (!location.equals(formData.location)) return false;
        if (!schema.equals(formData.schema)) return false;
        if (!startDate.equals(formData.startDate)) return false;
        if (!values.equals(formData.values)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (values != null ? values.hashCode() : 0); 
        return result;
    }
}
