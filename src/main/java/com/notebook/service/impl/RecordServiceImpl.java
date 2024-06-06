package com.notebook.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notebook.data.DataProvider;
import com.notebook.data.impl.DataProviderImpl;
import com.notebook.entities.RecordEntity;
import com.notebook.service.RecordService;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordServiceImpl implements RecordService {
    private Map<Integer, RecordEntity> records;
    private int counter = 0;
    private final DataProvider dataProvider;
    private final ObjectMapper mapper;
    private final static Path FILE_PATH = Path.of("./data.json");

    public RecordServiceImpl() {
        dataProvider = new DataProviderImpl();
        mapper = new ObjectMapper();
        initStorage();
    }

    @Override
    public RecordEntity writeRecord(String note) {
        counter++;
        RecordEntity record = new RecordEntity(counter, note);
        this.records.put(record.getId(), record);
        saveRecordToFile();
        return record;
    }

    @Override
    public Collection<RecordEntity> getListRecords() {
        return this.records.values();
    }

    @Override
    public void deleteRecord(int recordId) {
        if (!this.records.containsKey(recordId)) {
            throw new NoSuchElementException("Запись c id " + recordId + " не найдена");
        }

        this.records.remove(recordId);
        saveRecordToFile();
        System.out.println("Запись с id " + recordId + " успешно удалена!");
    }

    @Override
    public Optional<RecordEntity> getRecordById(int recordId) {
        return Optional.ofNullable(this.records.get(recordId));
    }

    @Override
    public List<RecordEntity> getRecordByDate(String date) {
        List<RecordEntity> matchingRecords = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date enteredDate = dateFormat.parse(date);

            List<RecordEntity> recordsList = mapper.readValue(FILE_PATH.toFile(),
                    new TypeReference<List<RecordEntity>>() {
                    });

            for (RecordEntity record : recordsList) {
                try {
                    Date fileDate = dateFormat.parse(record.getCreatedAt().substring(0, 10));
                    if (enteredDate.equals(fileDate)) {
                        matchingRecords.add(record);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return matchingRecords;
    }

    private void initStorage() {
        Collection<RecordEntity> existingRecords = Collections.EMPTY_LIST;

        try {
            existingRecords = dataProvider.loadListRecords();
        } catch (Exception e) {
            System.out.println("Ошибка при чтении данных из файла!" + e);
        }

        records = new HashMap<>();

        for (RecordEntity record : existingRecords) {
            records.put(record.getId(), record);
            if (record.getId() > counter) {
                counter = record.getId();
            }
        }
    }

    private void saveRecordToFile() {
        try {
            this.dataProvider.save(records.values());
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении данных в файл!" + e);
        }
    }
}
