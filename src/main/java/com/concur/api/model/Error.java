package com.concur.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;


@ApiModel
@XmlRootElement(name = "error", namespace = "com.concur.api.model")
public class Error {

    private String message = null;


    @ApiModelProperty(required = false, value = "")
    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorDTO {\n");

        sb.append("  message: ").append(message).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
