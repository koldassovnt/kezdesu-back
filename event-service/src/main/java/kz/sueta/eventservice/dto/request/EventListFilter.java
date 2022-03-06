package kz.sueta.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventListFilter {
    public Integer limit;
    public Integer offset;
    public String categoryId;
    public String labelSearch;
    public String clientId; // creator client id
    public Boolean actual;
    public Boolean blocked;
}
