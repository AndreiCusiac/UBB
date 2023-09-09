package rpcprotocol;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponsesType responsesType;
    private Object data;

    private Response() {

    }

    public ResponsesType responsesType() {
        return this.responsesType;
    }

    public Object data() {
        return this.data;
    }

    private void type (ResponsesType responsesType) {
        this.responsesType = responsesType;
    }

    private void data (Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "Response{" +
                "type='" + responsesType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static class Builder{
        private Response response=new Response();

        public Builder type(ResponsesType type) {
            response.type(type);
            return this;
        }

        public Builder data() {
            response.data();
            return this;
        }

        public Builder data(Object data) {
            response.data(data);
            return this;
        }

        public Response build() {
            return response;
        }

    }
}
