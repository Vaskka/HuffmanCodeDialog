package com.vaskka.fun.huffman.core.format;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: HuffmanCodeTree
 * @description: CodingMappingJsonData 生成编码表的json文件
 * @author: Vaskka
 * @create: 2018/11/30 3:29 PM
 **/

public class CodingMappingJsonData {

    public static class InnerMappingCoding {
        private String character;

        private String code;

        public String getC() {
            return character;
        }

        public void setC(String c) {
            this.character = c;
        }

        public String getCoding() {
            return code;
        }

        public void setCoding(String coding) {
            this.code = coding;
        }

        public InnerMappingCoding(String c, String coding) {
            this.character = c;
            this.code = coding;
        }
    }

    private List<InnerMappingCoding> codeMapping = new ArrayList<>();

    public List<InnerMappingCoding> getCodeMapping() {
        return codeMapping;
    }

    public void setCodeMapping(List<InnerMappingCoding> codeMapping) {
        this.codeMapping = codeMapping;
    }

    public CodingMappingJsonData(List<InnerMappingCoding> codeMapping) {
        this.codeMapping = codeMapping;
    }
}
