package com.vaskka.fun.huffman;

import com.vaskka.fun.huffman.core.Compress;
import com.vaskka.fun.huffman.core.Unpack;
import com.vaskka.fun.huffman.exceptions.*;
import com.vaskka.fun.huffman.ui.part.MainFrame;
import com.vaskka.fun.huffman.utils.Util;

import javax.swing.*;
import java.util.*;
import java.io.IOException;

import static javax.swing.SwingUtilities.invokeLater;

public class Main {


    private static void go() {
        invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            MainFrame f = new MainFrame("Huffman 压缩解压示例");
        });
    }

    public static void main(String[] args) throws IOException {
	// write your code here

        go();

    }
}
