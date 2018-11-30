package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: CharMappingNotFoundException 字符编码未找到异常
 * @author: Vaskka
 * @create: 2018/11/30 6:45 PM
 **/

public class CharMappingNotFoundException extends Exception {
    private char errorChar;

    public char getErrorChar() {
        return errorChar;
    }

    public void setErrorChar(char errorChar) {
        this.errorChar = errorChar;
    }

    public CharMappingNotFoundException(String message, char errorChar) {
        this.errorChar = errorChar;
    }
}
