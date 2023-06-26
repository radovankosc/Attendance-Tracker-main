package com.ctrlaltelite.backend.models;

public enum RecordType {
    WORKED_HOURS ("worked hours"),
    PUBLIC_HOLIDAY ("public holiday"),
    VACATION("vacation"),
    DOCTOR_VISIT("doctor visit"),
    DOCTOR_ACCOMPANY("doctor accompany"),
    LEAVE_FOR_FAMILY_CARE("leave for family care"),
    UNPAID_LEAVE("unpaid leave"),
    OTHER("other");

    public final String label;

    RecordType(String label) {
        this.label=label;
    }

    public static RecordType fromLabel(String label){
        for( RecordType recordType : RecordType.values()){ //The enum type has a values() method, which returns an array of all enum constants. This method is useful when you want to loop through the constants of an enum:
            if(recordType.label.equals(label)){
                return recordType;
            }
        }
        return null;
    }
}
