package mch.asset.model;

import mch.asset.view.PropertyView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse {
    private List<PropertyView> properties;
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
}
