package com.test.consumertestapp.model;

public class ExampleObj2 {
    private String name;
    private int age;

    private String special;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "ExampleObj2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", special='" + special + '\'' +
                '}';
    }

    public ExampleObj2() {
    }
}
