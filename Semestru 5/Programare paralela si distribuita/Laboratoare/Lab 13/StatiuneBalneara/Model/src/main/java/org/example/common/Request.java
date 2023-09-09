package org.example.common;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType requestsType;
    private Object data;

    private Request() {

    }

    public RequestType requestsType() {
        return requestsType;
    }

    public void requestsType(RequestType requestsType) {
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

        public Builder type (RequestType type) {
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