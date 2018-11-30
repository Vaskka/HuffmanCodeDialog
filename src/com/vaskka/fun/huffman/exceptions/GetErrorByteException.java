package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: GetErrorByteException 得到json文件时出现非法字节
 * @author: Vaskka
 * @create: 2018/11/30 6:34 PM
 **/

public class GetErrorByteException extends Exception {
    private byte errorByte;

    public byte getErrorByte() {
        return errorByte;
    }

    public void setErrorByte(byte errorByte) {
        this.errorByte = errorByte;
    }

    public GetErrorByteException(String message, byte errorByte) {
        super(message);
        this.errorByte = errorByte;
    }
}
