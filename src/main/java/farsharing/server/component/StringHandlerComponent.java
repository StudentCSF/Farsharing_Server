package farsharing.server.component;

import org.springframework.stereotype.Component;

@Component
public class StringHandlerComponent {

    public String emptyLikeNull(String str) {
        return str == null || str.length() == 0 ? null : str;
    }
}
