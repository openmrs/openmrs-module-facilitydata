/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.facilitydata.web.taglib;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.time.DateUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.FacilityDataFormSchema;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.enums.Frequency;
import org.openmrs.module.facilitydata.service.FacilityDataService;
import org.openmrs.module.facilitydata.util.FacilityDataDateUtils;

public class CalendarTag extends TagSupport {
    private int month, year;
    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private FacilityDataFormSchema schema;
    private Integer schemaId, locationId;
    private Location location;
    private String startDate, endDate;

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Context.getLocale());
        String[] daysOfWeek = dateFormatSymbols.getWeekdays();
        String[] months = dateFormatSymbols.getMonths();
        schema = Context.getService(FacilityDataService.class).getFacilityDataFormSchema(schemaId);
        location = Context.getLocationService().getLocation(locationId);
        startDate = String.format("%s/%s/%s", year, month, "01");
        try {
            endDate = String.format("%s/%s/%s", year, month,
                    FacilityDataDateUtils.getDateFormat().format(FacilityDataDateUtils.getLastOfMonthDate(FacilityDataDateUtils.getDateFormat().parse(startDate))));
            startCal.setTime(FacilityDataDateUtils.getDateFormat().parse(startDate));
            endCal.setTime(FacilityDataDateUtils.getDateFormat().parse(endDate));
        } catch (ParseException e) {
            throw new JspException(e);
        }
        StringBuilder sb = new StringBuilder();
        if (schema != null && schema.getFrequency() == Frequency.DAILY) {
            String nextLink = getNextLink(startCal);
            sb.append("<table cellspacing=\"1\" cellpadding=\"2\">");
            sb.append("<tr>").append("<th style=\"background:#8ac;color:#fff;\" colspan=\"7\">").append(String.format("%s %s %d %s", getPrevLink(startCal),months[month - 1], year,nextLink)).append("</th></tr>");
            sb.append(getDaysOfWeekHeading(daysOfWeek));
            sb.append(getCalendarTableForDaily(startCal, endCal, schema, location)).append("</table>");
        } else if (schema != null && schema.getFrequency() == Frequency.MONTHLY) {
            try {
                sb.append(getCalendarTableForMonthly(schema, location, months));
            } catch (ParseException e) {
                throw new JspException(e);
            }
        }
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    private String getCalendarTableForDaily(Calendar startCalendar, Calendar endCalendar,
                                            FacilityDataFormSchema schema, Location location) {
        startCal.set(Calendar.DAY_OF_WEEK, startCal.getMinimum(Calendar.DAY_OF_WEEK));
        StringBuilder sb = new StringBuilder();
        String MISSING_COLOR = "#f00";
        String MISSING_FONT = "#fff";
        String PARTIAL_COLOR = "#ffff66";
        String PARTIAL_FONT = "#000";
        String COMPLETE_COLOR = "#080";
        String COMPLETE_FONT = "#fff";
        Calendar iterCal = (Calendar) startCalendar.clone();
        iterCal.set(Calendar.DAY_OF_WEEK, iterCal.getMinimum(Calendar.DAY_OF_WEEK));
        Calendar lastDayCal = (Calendar) endCalendar.clone();
        lastDayCal.set(Calendar.DAY_OF_WEEK, lastDayCal.getMaximum(Calendar.DAY_OF_WEEK));
        int i = 0;
        while (iterCal.getTime().compareTo(lastDayCal.getTime()) <= 0) {
            if (i == 42) break; //TODO: hack to prevent it from going haywire and drawing past 1 calendar month.
            boolean closeRow = i % 7 == 0 && i > 0;
            boolean openRow = i % 7 == 0;
            String bgColor = "#aaa";
            String fontColor = "#000";
            boolean showDayNumber = iterCal.getTime().compareTo(startCalendar.getTime()) >= 0 && iterCal.getTime().compareTo(endCalendar.getTime()) <= 0;
            boolean inFuture = iterCal.compareTo(Calendar.getInstance()) > 0;
            boolean showEdit = true;
            boolean showView = true;
            if (showDayNumber) {
                Date rounded = DateUtils.round(iterCal.getTime(), Calendar.HOUR);
                FacilityDataReport formData = Context.getService(FacilityDataService.class).getFacilityDataReportFormData(schema, rounded,
                        rounded, location);
                int numFilled = Context.getService(FacilityDataService.class).getNumberOfQuestionedFilledOut(formData, schema);
                if (numFilled < 1) {
                    showView = false;
                    if (inFuture) {
                        showEdit = false;
                        bgColor = "#aaa";
                        fontColor = "#000";
                    } else {
                        bgColor = MISSING_COLOR;
                        fontColor = MISSING_FONT;
                    }
                } else if (numFilled < Context.getService(FacilityDataService.class).getNumberOfQuestionsInReport(schema)) {
                    bgColor = PARTIAL_COLOR;
                    fontColor = PARTIAL_FONT;
                } else {
                    bgColor = COMPLETE_COLOR;
                    fontColor = COMPLETE_FONT;

                }
            }
            if (closeRow) sb.append("</tr>");
            if (openRow) sb.append("<tr>");
            sb.append(String.format("<td style=\"vertical-align:top;width:75px;height:75px;background:%s;color:%s\">", bgColor, fontColor));
            if (showDayNumber) {
                sb.append(String.format(inFuture ? "%s" : "<b>%s</b>", iterCal.get(Calendar.DAY_OF_MONTH)));
                if (showEdit) {
                    sb.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    sb.append(String.format("<a style=\"color:%s\" href=\"report.form?id=%d&startDate=%s&endDate=%s&site=%d&editable=true\">%s</a>", fontColor, schemaId,
                            FacilityDataDateUtils.getDateFormat().format(iterCal.getTime()), FacilityDataDateUtils.getDateFormat().format(iterCal.getTime()), locationId,
                            Context.getMessageSourceService().getMessage("general.edit")));
                }
                if (showView) {
                    sb.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    sb.append(String.format("<a style=\"color:%s\" href=\"report.form?id=%d&startDate=%s&endDate=%s&site=%d\">%s</a>", fontColor, schemaId,
                            FacilityDataDateUtils.getDateFormat().format(iterCal.getTime()), FacilityDataDateUtils.getDateFormat().format(iterCal.getTime()), locationId,
                            Context.getMessageSourceService().getMessage("general.view")));
                }
            } else {
                sb.append(String.format(inFuture ? "<b>%d</b>" : "%d)", iterCal.get(Calendar.DAY_OF_MONTH)));
            }
            sb.append("</td>");
            i++;
            iterCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String getCalendarTableForMonthly(FacilityDataFormSchema schema, Location location, String... months) throws ParseException {
        StringBuilder sb = new StringBuilder();
        String MISSING_COLOR = "#f00";
        String MISSING_FONT = "#fff";
        String PARTIAL_COLOR = "#ffff66";
        String PARTIAL_FONT = "#000";
        String COMPLETE_COLOR = "#080";
        String COMPLETE_FONT = "#fff";
        sb.append("<table style=\"background: white;\" cellspacing=\"1\" cellpadding=\"1\" style=\"border: black; 1px; solid;\">");
        sb.append("<tr style=\"background:#8ac;color:#fff;\">")
                .append("<td style=\"vertical-align:top;font-weight:bold;text-align:center;\" colspan=\"3\">")
                .append(getPrevYearLink(startCal))
                .append(year)
                .append(getNextYearLink(startCal));
        for (int i = 1; i <= 12; ++i) {
            String ymd = String.format("%s/%s/%s", year, (i < 10 ? "0" + i : i), "01");
            Date firstOfMonth = FacilityDataDateUtils.getDateFormat().parse(ymd);
            Date lastOfMonth = FacilityDataDateUtils.getLastOfMonthDate(firstOfMonth);
            boolean inFuture = lastOfMonth.after(new Date());
            boolean showEdit = true;
            boolean showView = true;
            String fontColor = "#000";
            String bgColor = "#fff";
            Date roundedFirstOfMonth = DateUtils.round(firstOfMonth, Calendar.HOUR);
            Date roundedLastOfMonth = DateUtils.round(lastOfMonth, Calendar.HOUR);
            FacilityDataReport formData = Context.getService(FacilityDataService.class).getFacilityDataReportFormData(schema, roundedFirstOfMonth,
                    roundedLastOfMonth, location);
            int numFilled = Context.getService(FacilityDataService.class).getNumberOfQuestionedFilledOut(formData, schema);
            if (inFuture) {
                bgColor = "#aaa";
                fontColor = "#000;";
                showEdit = false;
                showView = false;
            } else {
                if (numFilled < 1) {
                    bgColor = MISSING_COLOR;
                    fontColor = MISSING_FONT;
                } else if (numFilled < Context.getService(FacilityDataService.class).getNumberOfQuestionsInReport(schema)) {
                    bgColor = PARTIAL_COLOR;
                    fontColor = PARTIAL_FONT;
                } else {
                    bgColor = COMPLETE_COLOR;
                    fontColor = COMPLETE_FONT;
                }
            }
            sb.append(String.format("<tr style=background:%s;color:%s>", bgColor, fontColor)).append("<td>");
            sb.append(String.format("<tr style=\" background:%s;color:%s\">", bgColor, fontColor)).append("<td>");
            sb.append(inFuture ? months[i-1] : String.format("<b>%s</b>",months[i-1]));
            sb.append("<td>").append(showEdit ? String.format("<a style=\"color:%s\" href=\"report.form?id=%d&startDate=%s&endDate=%s&site=%d&editable=true\">%s</a>", fontColor, schemaId,
                        FacilityDataDateUtils.getDateFormat().format(firstOfMonth), FacilityDataDateUtils.getDateFormat().format(lastOfMonth), locationId,
                        Context.getMessageSourceService().getMessage("general.edit")) : "").append("</td>");
            sb.append("<td>").append(showView ? String.format("<a style=\"color:%s\" href=\"report.form?id=%d&startDate=%s&endDate=%s&site=%d\">%s</a>", fontColor, schemaId,
                    FacilityDataDateUtils.getDateFormat().format(firstOfMonth), FacilityDataDateUtils.getDateFormat().format(lastOfMonth), locationId,
                    Context.getMessageSourceService().getMessage("general.view")) : "").append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * Utility method to simply print a hyperlink containing ">>>" to advance the calendar one month.
     * @param c current date as a {@link Calendar} instance.
     * @return a hyperlink containing >>> which allows the user to click it and view the next month.
     */
    private String getNextLink(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<a href=\"manage.form?schema=%s&site=%s&", schemaId, locationId));
        if (c.get(Calendar.MONTH) == 11) {
            String month = FacilityDataDateUtils.incrementMonth(c,1);
            sb.append(String.format("month=%s&year=%s\">&gt;&gt;&gt;</a>", month,year+1));
        } else {
            sb.append(String.format("month=%s&year=%s\">&gt;&gt;&gt;</a>",FacilityDataDateUtils.incrementMonth(c,1),year));
        }

        return sb.toString();
    }

    /**
     * Utility method to simply print a hyperlink containing "<<<" to advance the calendar back one month.
     * @param c current date as a {@link Calendar} instance.
     * @return a hyperlink containing >>> which allows the user to click it and view the previous month. 
     */
    private String getPrevLink(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<a href=\"manage.form?schema=%s&site=%s&", schemaId, locationId));
        if (c.get(Calendar.MONTH) == 0) {
            String month = FacilityDataDateUtils.incrementMonth(c, -1);
            sb.append(String.format("month=%s&year=%s\">&lt;&lt;&lt;</a>", month, year-1));
        } else {
            sb.append(String.format("month=%s&year=%s\">&lt;&lt;&lt;</a>", FacilityDataDateUtils.incrementMonth(c, -1), year));
        }

        return sb.toString();
    }

    /**
     * Utility method to advance the year for the monthly reports.
     * @param c the {@link Calendar} representing the current date.
     * @return the next year.
     */
    private String getNextYearLink(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<a href=\"manage.form?schema=%s&site=%s&year=%d\">&gt;&gt;&gt;</a>", schemaId, locationId,year+1));
        return sb.toString();
    }

    /**
     * Utility method to advance the year backwards for the monthly reports.
     * @param c the {@link Calendar} representing the current date.
     * @return the previous year.
     */
    private String getPrevYearLink(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<a href=\"manage.form?schema=%s&site=%s&year=%d\">&lt;&lt;&lt;</a>", schemaId, locationId, year-1));
        return sb.toString();
    }
    /**
     * Private utility method to print out the days of the week.
     *
     * @param daysOfWeek an array containing the locale specific days of the week
     * @return
     */
    private String getDaysOfWeekHeading(String... daysOfWeek) {
        StringBuilder sb = new StringBuilder();        
        String rowStart = "<tr style=\"background:#8ac;color:#fff;\">\t\n";
        sb.append(rowStart);
        for (int i = 1; i < daysOfWeek.length; i++) {
            sb.append("<th>").append(daysOfWeek[i]).append("</th>");
        }
        sb.append("</tr>");
        return sb.toString();
    }
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}