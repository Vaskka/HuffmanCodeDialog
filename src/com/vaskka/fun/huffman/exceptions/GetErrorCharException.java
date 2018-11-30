package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: GetErrorCharException 得到Huffman字符映射时出现非法字符
 * @author: Vaskka
 * @create: 2018/11/30 6:30 PM
 **/

public class GetErrorCharException extends Exception {

    private char errorChar;

    public GetErrorCharException(String message, char errorChar) {
        super(message);
        this.errorChar = errorChar;
    }

    public char getErrorChar() {
        return errorChar;
    }

    public void setErrorChar(char errorChar) {
        this.errorChar = errorChar;
    }
}
