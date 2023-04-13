package public_Api_tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicPojo {
    @JsonProperty("count")
    private long count;
    @JsonProperty("entries")
    private List<Entry> entries;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {

        @JsonProperty("API")
        private String api;
        @JsonProperty("Description")
        private String description;
        @JsonProperty("Auth")
        private String auth;
        @JsonProperty("HTTPS")
        private boolean https;
        @JsonProperty("Cors")
        private String cors;
        @JsonProperty("Link")
        private String link;
        @JsonProperty("Category")
        private String category;

        @Override
        public String toString() {
            return "Entry{" +
                    "api='" + api + '\'' +
                    ", description='" + description + '\'' +
                    ", auth='" + auth + '\'' +
                    ", https=" + https +
                    ", cors='" + cors + '\'' +
                    ", link='" + link + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }
    }

}
