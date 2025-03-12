package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlLogFormatter implements LogFormatter {

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public String format(LogRecord log) {
        try {
            return mapper.writeValueAsString(log);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
