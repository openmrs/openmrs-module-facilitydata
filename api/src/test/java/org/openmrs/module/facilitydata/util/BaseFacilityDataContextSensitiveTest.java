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
package org.openmrs.module.facilitydata.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Overrides the default {@link BaseModuleContextSensitiveTest} to allow for better handling
 * of non-specified default values within test data sets
 */
@ContextConfiguration(locations = { "classpath:applicationContext-service.xml", "classpath*:TestingApplicationContext.xml",
									"classpath*:moduleApplicationContext.xml" }, inheritLocations = false)
public abstract class BaseFacilityDataContextSensitiveTest extends BaseModuleContextSensitiveTest {

	private static Map<String, IDataSet> cachedDatasets = new HashMap<String, IDataSet>();
	
	/**
	 * Overrides superclass by specifying default values for common columns if needed
	 * @see BaseModuleContextSensitiveTest#executeDataSet(String)
	 */
	@Override
	public void executeDataSet(String datasetFilename) throws Exception {
		
		// try to get the given filename from the cache
		IDataSet xmlDataSetToRun = cachedDatasets.get(datasetFilename);
		
		// if we didn't find it in the cache, load it
		if (xmlDataSetToRun == null) {
			
			File file = new File(datasetFilename);
			InputStream fileInInputStreamFormat = null;
			if (file.exists()) {
				fileInInputStreamFormat = new FileInputStream(datasetFilename);
			}
			else {
				fileInInputStreamFormat = getClass().getClassLoader().getResourceAsStream(datasetFilename);
				if (fileInInputStreamFormat == null) {
					throw new FileNotFoundException("Unable to find '" + datasetFilename + "' in the classpath");
				}
			}
			
			StringReader reader = null;
			try {
				String xmlFile = IOUtils.toString(fileInInputStreamFormat);
				
				StringBuilder openmrsObjectText = new StringBuilder();
				//openmrsObjectText.append("uuid=\"" + UUID.randomUUID().toString() + "\" ");
				openmrsObjectText.append("creator=\"1\" date_created=\"2005-08-07 00:00:00.0\" ");
				openmrsObjectText.append("changed_by=\"1\" date_changed=\"2007-10-24 14:51:53.0\" ");
				
				StringBuilder metadataObjectText = new StringBuilder(openmrsObjectText);
				metadataObjectText.append(" retired=\"false\" retire_reason=\"\"");
				
				StringBuilder dataObjectText = new StringBuilder(openmrsObjectText);
				dataObjectText.append(" voided=\"false\" void_reason=\"\"");
			
				xmlFile = xmlFile.replace("[METADATA]", metadataObjectText.toString());
				xmlFile = xmlFile.replace("[DATA]", dataObjectText.toString());
				
				reader = new StringReader(xmlFile);
				FlatXmlDataSet flatXml = new FlatXmlDataSet(reader, false, true, false);
				ReplacementDataSet replacementDataSet = new ReplacementDataSet(flatXml);
				replacementDataSet.addReplacementObject("[NULL]", null);
				
				xmlDataSetToRun = replacementDataSet;
			}
			finally {
				IOUtils.closeQuietly(fileInInputStreamFormat);
				IOUtils.closeQuietly(reader);
			}
		}
		
		cachedDatasets.put(datasetFilename, xmlDataSetToRun);
		
		executeDataSet(xmlDataSetToRun);
	}
}
