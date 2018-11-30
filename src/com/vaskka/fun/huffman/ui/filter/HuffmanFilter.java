package com.vaskka.fun.huffman.ui.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @program: HuffmanCodeTree
 * @description: HuffmanFilter 哈夫曼压缩文件
 * @author: Vaskka
 * @create: 2018/12/1 3:10 AM
 **/

public class HuffmanFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        return f.isDirectory() || name.toLowerCase().endsWith(".huffman"); // 仅显示目录和xls、xlsx文件


    }

    @Override
    public String getDescription() {
        return ".huffman";
    }
}
