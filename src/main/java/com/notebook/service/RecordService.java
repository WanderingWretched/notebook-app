package com.notebook.service;

import com.notebook.entities.RecordEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RecordService {

    RecordEntity writeRecord(String note);

    Collection<RecordEntity> getListRecords();

    void deleteRecord(int id);

    Optional<RecordEntity> getRecordById(int id);

    List<RecordEntity> getRecordByDate(String date);
}
