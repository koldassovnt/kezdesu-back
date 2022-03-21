package kz.sueta.clientservice.dto.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientListFilter {
    public Integer limit;
    public Integer offset;
    public Boolean actual;
    public Boolean blocked;
}
