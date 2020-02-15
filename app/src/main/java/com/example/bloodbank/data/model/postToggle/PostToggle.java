
package com.example.bloodbank.data.model.postToggle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostToggle {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostToggleData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PostToggleData getData() {
        return data;
    }

    public void setData(PostToggleData data) {
        this.data = data;
    }

}
