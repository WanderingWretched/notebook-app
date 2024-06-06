package com.notebook.data;

import com.notebook.entities.RecordEntity;

import java.io.IOException;
import java.util.Collection;

public interface DataProvider {
    void save(Collection<RecordEntity> records) throws IOException;

    Collection<RecordEntity> loadListRecords() throws IOException;
}
