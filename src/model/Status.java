package model;

public enum Status {
    TODO("Не выполнено"),
    DONE("Выполнено");

    private final String description;

     Status(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
