package com.comas.grush.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Product {

    @PrimaryKey
    @NonNull
    private String id;
    private String ownerId;

    private String name;
    private String desc;
    private String image;

    private Boolean isRemoved = false;
    private Long lastUpdated;

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("id", id);
            put("ownerId", ownerId);
            put("name", name);
            put("desc", desc);
            put("image", image);
            put("isRemoved", isRemoved);
            put("lastUpdated", FieldValue.serverTimestamp());
        }};
    }

    public void fromMap(Map<String, Object> map) {
        id = (String) map.get("id");
        ownerId = (String) map.get("ownerId");
        name = (String) map.get("name");
        desc = (String) map.get("desc");
        image = (String) map.get("image");
        isRemoved = (Boolean) map.get("isRemoved");
        lastUpdated = ((Timestamp) map.get("lastUpdated")).toDate().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
