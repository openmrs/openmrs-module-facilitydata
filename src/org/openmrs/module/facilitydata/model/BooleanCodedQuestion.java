package org.openmrs.module.facilitydata.model;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A Boolean Coded question is one which has a true,false, or not applicable answer. Typically this will
 * be represented as radio buttons on a report form.
 */
public class BooleanCodedQuestion extends CodedQuestion {

    /**
     * Return the coded options which will be stored in the {@link FacilityDataValue} valueText field.
     *
     * @return an immutable {@link List} containing the coded options.
     * @see FacilityDataValue
     * @see StockQuestion
     */
    @Override
    public List<String> getCodedOptions() {
        return Collections.unmodifiableList(Arrays.asList("t", "f", "not_applicable"));
    }
}