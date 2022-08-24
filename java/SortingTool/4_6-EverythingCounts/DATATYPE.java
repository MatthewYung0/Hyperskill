package sorting;

public enum DATATYPE {

    NUMBERS("long"),
    WORDS("word"),
    LINES("line");

    final String dataType;

    DATATYPE(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
