package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: ParseJsonMappingException 解析json映射文件异常
 * @author: Vaskka
 * @create: 2018/12/1 1:22 AM
 **/

public class ParseJsonMappingException extends Exception {

    private String json;

    private String errorString;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    /**
     * 实例化json解析遗产
     * @param message 异常信息
     * @param json json字符串
     * @param errorString 发生错误的字符串
     */
    public ParseJsonMappingException(String message, String json, String errorString) {
        super(message);
        this.json = json;
        this.errorString = errorString;
    }
}
