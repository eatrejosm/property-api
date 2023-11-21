package mch.asset.view;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.sql.Timestamp;
@Data
public class PropertyView {
    private int id;
    private String application;
    private String label;
    private String profile;
    private String propKey;
    private String propValue;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
