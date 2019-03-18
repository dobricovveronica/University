package com.amsoftgroup.model;

import java.time.LocalDate;
import java.util.Set;

public class Person {

    private long id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private char gender;

    private byte[] picture;

    private String mail;

    private Address addresses;

    private LibraryAbonament libraryAbonament;

    private Set<Phone> phones;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public LibraryAbonament getLibraryAbonament() {
        return libraryAbonament;
    }

    public void setLibraryAbonament(LibraryAbonament libraryAbonament) {
        this.libraryAbonament = libraryAbonament;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
}
