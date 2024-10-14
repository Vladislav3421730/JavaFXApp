package org.example.TCP;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.TCP.enums.RequestType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String message;
    private RequestType requestType;
}

