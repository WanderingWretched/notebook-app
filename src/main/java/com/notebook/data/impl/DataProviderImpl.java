package com.notebook.data.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notebook.data.DataProvider;
import com.notebook.entities.RecordEntity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class DataProviderImpl implements DataProvider {
    private ObjectMapper mapper = new ObjectMapper();

    private final static Path FILE_PATH = Path.of("./data.json");

    @Override
    public void save(Collection<RecordEntity> records) throws IOException {
        mapper.writeValue(FILE_PATH.toFile(), records);
    }

    @Override
    public Collection<RecordEntity> loadListRecords() throws IOException {
        Collection<RecordEntity> records =
                mapper.readValue(FILE_PATH.toFile(), new TypeReference<Collection<RecordEntity>>() {
                });
        return records;
    }
}


