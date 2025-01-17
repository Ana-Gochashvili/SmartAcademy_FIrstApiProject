package dataObject;

public enum StatusCodesData {
    SUCCESS_200(200),
    CREATED_201(201),
    BED_REQUEST_400(400),
    USER_EXISTS_ERROR_406(406);

    private final int value;

    StatusCodesData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}