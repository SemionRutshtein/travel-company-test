package com.trip.app.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.trip.app.dto.CsvSuggestionDto;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Component
public class CsvSuggestionWriter {
    private CsvMapper csvMapper = new CsvMapper();
    private CsvSchema schema = csvMapper
            .schemaFor(CsvSuggestionDto.class)
            .withHeader()
            .sortedBy("_id", "name", "type", "latitude", "longitude");

    @SneakyThrows
    public void write(@NonNull String fileName, @NonNull List<CsvSuggestionDto> data) {
       @Cleanup Writer writer = new PrintWriter(new FileWriter(fileName));
        doWrite(writer, data);
    }

    @SneakyThrows
    private void doWrite(@NonNull Writer writer,@NonNull List<CsvSuggestionDto> data) {
        csvMapper.writer().with(schema).writeValues(writer).writeAll(data);    }
}
