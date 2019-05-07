package com.std.springboot.common.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用json返回结果对象
 *
 * @author zhaojy
 * @date 2019-05-07
 */
@Data
public class CommonJsonResult<T> implements Serializable {

    private static final long serialVersionUID = 7813541301831869173L;

    private String requestId = "";
    private String code = "";
    private String message = "";
    private Boolean success = false;
    private List<T> data = new ArrayList<>(0);
    private Integer total = 0;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CommonJsonResult{" +
                "requestId='" + requestId + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
