package mch.asset.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PropertyViewMatcher {
    Front(null),
    Rear(null),
    Living(null),
    Dining(null),
    MainBedroom("Main(.*)Bedroom"),
    Bedroom(null),
    MainBath("Main(.*)Bath"),
    Kitchen(null),
    Bath(null);

    private final String labelMatcher;

    public static PropertyViewMatcher lookupLabel(String label) {
        for (PropertyViewMatcher matcher: values()) {
            var labelName = (matcher.labelMatcher != null? matcher.labelMatcher: matcher.name()).toLowerCase();

            /**
              RegexLabelName -> matches function will check if the string contains this expression.
             * */
            var regexLabelName = "(.*)"+labelName+"(.*)";

            if (label.toLowerCase().matches(regexLabelName)) {
                return matcher;
            }
        }
        return null;
    }
}
