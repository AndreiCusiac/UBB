package rpcprotocol;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    private RequestsType requestsType;
    private Object data;

    private Request() {

    }

    public RequestsType requestsType() {
        return requestsType;
    }

    public void requestsType(RequestsType requestsType) {
        this.requestsType = requestsType;
    }

    public Object data() {
        return data;
    }

    public void data (Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + requestsType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static class Builder {
        private Request request = new Request();

        public Builder type (RequestsType type) {
            request.requestsType(type);
            return this;
        }

        public Builder data (Object data) {
            request.data(data);
            return this;
        }

        public Builder data () {
            request.data();
            return this;
        }

        public Request build() {
            return request;
        }

    }
}
