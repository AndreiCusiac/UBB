package main.java.common;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType responseType;
    private Object data;

    private Response() {

    }

    public ResponseType responseType() {
        return responseType;
    }

    public void responseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public Object data() {
        return data;
    }

    public void data (Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type='" + responseType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static class Builder {
        private Response response = new Response();

        public Builder type (ResponseType type) {
            response.responseType(type);
            return this;
        }

        public Builder data (Object data) {
            response.data(data);
            return this;
        }

        public Builder data () {
            response.data();
            return this;
        }

        public Response build() {
            return response;
        }

    }
}