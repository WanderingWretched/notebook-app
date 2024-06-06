package com.notebook.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RecordEntity extends BaseEntity {
    private String note;

    public RecordEntity(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public RecordEntity() {
    }

    public int getNoteLength() {
        return note.length();
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordEntity record = (RecordEntity) o;
        return Objects.equals(note, record.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note);
    }

    @Override
    public String toString() {
        return "RecordEntity{" +
                "note='" + note + '\'' +
                ", id=" + id +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
