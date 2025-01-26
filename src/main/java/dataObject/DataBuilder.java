package dataObject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataBuilder {
    private String userName;
    private String password;
}
