/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2011 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.tools.spectrum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opennms.core.utils.LogUtils;
import org.opennms.netmgt.xml.eventconf.Decode;
import org.opennms.netmgt.xml.eventconf.Varbindsdecode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class SpectrumUtils {
    private String m_modelTypeAssetField = "manufacturer";
    private Map<String,EventTable> m_eventTableCache;
    
    public SpectrumUtils() {
        m_eventTableCache = new HashMap<String,EventTable>();
    }
    
    public String translateAllSubstTokens(EventFormat format) {
        String untranslated = format.getContents();
        String translated = untranslated;
        Matcher m = Pattern.compile("(?s)(\\{.*?\\})").matcher(untranslated);
        while (m.find()) {
            LogUtils.debugf(this, "Found a token [%s], replacing it with token [%s]", m.group(1), translateFormatSubstToken(m.group(1)));
            translated = translated.replace(m.group(1), translateFormatSubstToken(m.group(1)));
            LogUtils.debugf(this, "New translated string: %s", translated);
        }
        return translated;
    }
    
    /**
     * 
     * @param inToken the substitution token from the Spectrum event format
     * @return the OpenNMS event XML equivalent for the inToken
     */
    public String translateFormatSubstToken(String inToken) {
        if (inToken == null) {
            throw new IllegalArgumentException("The input token must be non-null");
        }
        String outToken = inToken;
        
        if (inToken.startsWith("{d")) {
            outToken = "%time%";
        } else if (inToken.equals("{t}")) {
            outToken = "%asset[" + m_modelTypeAssetField + "]%";
        } else if (inToken.equals("{m}")) {
            outToken = "%nodelabel%";
        } else if (inToken.equals("{e}")) {
            outToken = "%uei%";
        } else if (inToken.startsWith("{I") || inToken.startsWith("{S") || inToken.startsWith("{T")) {
            Matcher m = Pattern.compile("^\\{\\s*[IST]\\s+(\\w+\\s+)?(\\d+)\\s*\\}$").matcher(inToken);
            if (m.matches()) {
                outToken = "%parm[#" + m.group(2) + "]%";
            }
        }
        
        return outToken;
    }
    
    public List<Varbindsdecode> translateAllEventTables(EventFormat ef, String eventTablePath) throws IOException {
        List<Varbindsdecode> vbds = new ArrayList<Varbindsdecode>();
        Pattern pat = Pattern.compile("^\\{\\s*T\\s+(\\w+)\\s+(\\d+)\\s*\\}");
        for (String token : ef.getSubstTokens()) {
            Matcher mat = pat.matcher(token);
            if (mat.matches()) {
                LogUtils.debugf(this, "Token [%s] looks like an event-table, processing it", token);
                EventTable et = loadEventTable(eventTablePath, mat.group(1));
                String parmId = "parm[#" + mat.group(2) + "]";
                Varbindsdecode vbd = translateEventTable(et, parmId);
                LogUtils.debugf(this, "Loaded event-table [%s] with parm-ID [%s], with %d mappings", et.getTableName(), parmId, vbd.getDecodeCount());
                vbds.add(translateEventTable(et, parmId));
            } else {
                LogUtils.debugf(this, "Token [%s] does not look like an event-table, skipping it", token);
            }
        }
        LogUtils.debugf(this, "Translated %d event-tables for event-code [%s]", vbds.size(), ef.getEventCode());
        return vbds;
    }
    
    public Varbindsdecode translateEventTable(EventTable et, String parmId) {
        Varbindsdecode vbd = new Varbindsdecode();
        vbd.setParmid(parmId);
        for (Integer key : et.keySet()) {
            Decode decode = new Decode();
            decode.setVarbindvalue(key.toString());
            decode.setVarbinddecodedstring(et.get(key));
            vbd.addDecode(decode);
        }
        return vbd;
    }
    
    public String translateSeverity(int spectrumSeverity) {
        if (spectrumSeverity == 0)
            return "Normal";
        else if (spectrumSeverity == 1)
            return "Warning";
        else if (spectrumSeverity == 2)
            return "Minor";
        else if (spectrumSeverity == 3)
            return "Major";
        else if (spectrumSeverity == 4)
            return "Critical";
        else
            return "Indeterminate";
    }
    
    private EventTable loadEventTable(String eventTablePath, String tableName) throws IOException {
        if (m_eventTableCache.containsKey(tableName)) {
            LogUtils.debugf(this, "Retrieving event-table [%s] from cache", tableName);
            return m_eventTableCache.get(tableName);
        }
        
        Resource tableFile = new FileSystemResource(eventTablePath + File.separator + tableName);
        EventTableReader etr = new EventTableReader(tableFile);
        LogUtils.debugf(this, "Attempting to load event-table [%s] from [%s]", tableName, tableFile);
        EventTable et = etr.getEventTable();
        LogUtils.debugf(this, "Storing event-table [%s] in cache", tableName);
        return et;
    }
    
    public void setModelTypeAssetField(String fieldName) {
        m_modelTypeAssetField = fieldName;
    }
    
    public String getModelTypeAssetField() {
        return m_modelTypeAssetField;
    }
}
