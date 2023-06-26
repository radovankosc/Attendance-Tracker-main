package com.ctrlaltelite.backend.models;

public enum ResourceType {
    PNG ("image/png"),
    JPG ("image/jpg"),
    GIF("image/gif");

    public final String label;

    ResourceType(String label) {
        this.label=label;
    }

    public static ResourceType fromLabel(String label){
        for(ResourceType type : ResourceType.values()){
            if(type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }

}
