package sorting;

public enum SORTTYPE {

    SORTINGTYPE("-sortingType"),
    NATURAL("natural"),
    COUNT("byCount");

    final String sortingType;

    SORTTYPE(String sortingType) {
        this.sortingType = sortingType;
    }

    public String getSortingType() {
        return sortingType;
    }

}
