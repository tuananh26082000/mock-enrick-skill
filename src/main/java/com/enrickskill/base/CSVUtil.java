package com.enrickskill.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class CSVUtil {
    public static String TYPE = "text/csv";

    public static boolean isCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static <T> List<T> csvToList(InputStream is, Class<T> targetClass) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().build())) {
            final ObjectMapper mapper = new ObjectMapper();

            Optional<CSVRecord> recordHeader = csvParser.stream().findFirst();
            List<CSVRecord> records = csvParser.getRecords();
            List<T> result = null;

            if (recordHeader.isPresent()) {
                result = records.stream().map(
                        record -> convertCSVRecordToObj(record, recordHeader.get(), mapper, targetClass)
                ).collect(Collectors.toList());
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }

    }

    public static <T> T convertCSVRecordToObj(CSVRecord record, CSVRecord header, ObjectMapper mapper, Class<T> targetClass) {
        int index = 0;
        Map<String, String> map = new HashMap<>();
        for (String field : header.values()) {
            map.put(field, record.get(index));
            index++;
        }
        return mapper.convertValue(map, targetClass);
    }


    public static <T> ByteArrayInputStream exportCSV(List<T> data) {
        final CSVFormat format = CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL).build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {


            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());
            Map<String, Object> map = objectMapper.convertValue(data.get(0), new TypeReference<>() {
            });
            csvPrinter.printRecord(map.keySet());


            for (T item : data) {
                map = objectMapper.convertValue(item, new TypeReference<>() {
                });
                csvPrinter.printRecord(map.values());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}