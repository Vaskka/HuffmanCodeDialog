package com.vaskka.fun.huffman.ui.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @program: HuffmanCodeTree
 * @description: JsonFilter Json文件过滤器
 * @author: Vaskka
 * @create: 2018/12/1 3:07 AM
 **/

public class JsonFilter extends FileFilter {
    @Override
    public boolean accept(File f) {

        String name = f.getName();

        return f.isDirectory() || name.toLowerCase().endsWith(".json");
    }

    @Override
    public String getDescription() {
        return ".json";
    }
}
