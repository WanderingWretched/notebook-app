package com.notebook;

import com.notebook.entities.RecordEntity;
import com.notebook.service.RecordService;
import com.notebook.service.impl.RecordServiceImpl;

import java.util.*;

public class Main {
    private static Map<String, Integer> dailyCount = new HashMap<>();

    public static void main(String[] args) {
        RecordService recordService = new RecordServiceImpl();

        printHelpInfo();

        printCommand();

        Scanner scanner = new Scanner(System.in);


        while (true) {
            String command = scanner.nextLine();

            if (!isKnowCommand(command)) {
                System.out.println("---Такая команда не найдена!---");
                printHelpInfo();
            }

            if (command.equalsIgnoreCase("read")) {
                Collection<RecordEntity> records = recordService
                        .getListRecords();

                if (records.isEmpty()) {
                    System.out.println("Список записей пуст");
                }

                for (RecordEntity record : records) {
                    System.out.println(record);
                }
            }

            if (command.toLowerCase().startsWith("write ")) {
                String note = command.substring(6);
                RecordEntity record = recordService
                        .writeRecord(note);
                dailyCount.put(record.getCreatedAt(), dailyCount.getOrDefault(record.getCreatedAt(), 0) + 1);
                System.out.println(record);
            }

            if (command.toLowerCase().startsWith("note ")) {
                String recordIdAsString = command.substring(5);
                int recordId = Integer.parseInt(recordIdAsString);
                Optional<RecordEntity> record = recordService
                        .getRecordById(recordId);
                if (record.isPresent()) {
                    System.out.println(record);
                } else {
                    System.out.println("Запись с id " + recordId + " не найдена");
                }
            }

            if (command.toLowerCase().startsWith("delete ")) {
                String recordIdAsString = command.substring(7);
                int recordId = Integer.parseInt(recordIdAsString);
                recordService
                        .deleteRecord(recordId);
            }

            if (command.toLowerCase().startsWith("date ")) {
                String dateRecord = command.substring(5);
                List<RecordEntity> records = recordService
                        .getRecordByDate(dateRecord);
                System.out.println(records);
            }

            if (command.equalsIgnoreCase("statistics")) {
                Collection<RecordEntity> records = recordService
                        .getListRecords();

                System.out.println("Количество записей: " + records.size());

                System.out.println("Общее количество символов: " + getTotalCharacters(records));

                System.out.println("Самый активный день: " + getMostActiveDay());

            }

            if (command.equalsIgnoreCase("help")) {
                printHelpInfo();
            }

            if (command.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
        }
    }

    private static int getTotalCharacters(Collection<RecordEntity> records) {
        return records.stream().mapToInt(RecordEntity::getNoteLength).sum();
    }

    private static String getMostActiveDay() {
        return Collections.max(dailyCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    private static boolean isKnowCommand(String command) {
        String commandInLowerCase = command.toLowerCase();
        return commandInLowerCase.equals("read")
                || commandInLowerCase.startsWith("write ")
                || commandInLowerCase.startsWith("note ")
                || commandInLowerCase.startsWith("delete ")
                || commandInLowerCase.startsWith("statistics")
                || commandInLowerCase.startsWith("date ")
                || commandInLowerCase.equals("help")
                || commandInLowerCase.equals("exit");
    }

    private static void printHelpInfo() {
        System.out.println("---Команды приложения---");
        System.out.println("help - Помощь");
        System.out.println("read - Вывести список записей");
        System.out.println("write - Добавить новую запись");
        System.out.println("note 'id' - Получить запись по id");
        System.out.println("delete 'id' - Удалить запись по id");
        System.out.println("statistics - Показать статистику использования записной книжки");
    }

    private static void printCommand() {
        System.out.println("Введите команду:");
    }
}