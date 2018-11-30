package com.vaskka.fun.huffman.ui.part;

import com.vaskka.fun.huffman.core.Compress;
import com.vaskka.fun.huffman.core.Unpack;
import com.vaskka.fun.huffman.exceptions.*;
import com.vaskka.fun.huffman.ui.filter.HuffmanFilter;
import com.vaskka.fun.huffman.ui.filter.JsonFilter;
import com.vaskka.fun.huffman.ui.layout.VerticalFlowLayout;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @program: HuffmanCodeTree
 * @description: MainFrame 主框体
 * @author: Vaskka
 * @create: 2018/12/1 2:47 AM
 **/

public class MainFrame extends JFrame {


    private JButton chooseFileCompress;

    private JButton chooseFileUnpack;

    /**
     * 构造窗体
     * @param title 标题
     */
    public MainFrame(String title) {
        super(title);

        initView();
        initAction();

        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * 初始化事件
     */
    private void initAction() {
        // 压缩按钮监听
        chooseFileCompress.addActionListener(e -> {
            String path = chooseFile("请选择要压缩的文件", null);

            if (path != null) {
                try {
                    Compress.getInstance().doHuffmanCompress(path);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "文件读写异常：" + e1.getMessage(), "出错啦", JOptionPane.ERROR_MESSAGE);

                } catch (FileIsEmptyException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "文件(" +  e1.getFile().getAbsolutePath() + ")内容为空", "出错啦" , JOptionPane.ERROR_MESSAGE);
                } catch (FileWriteFailedException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "文件无法写入", "出错啦" , JOptionPane.ERROR_MESSAGE);
                } catch (FileCreateFailedException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "文件(" +  e1.getPath() + ")无法新建", "出错啦" , JOptionPane.ERROR_MESSAGE);

                } catch (GetErrorCharException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "压缩时出现非法字符", "出错啦" , JOptionPane.ERROR_MESSAGE);

                } catch (GetErrorByteException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "压缩时出现非法字节", "出错啦" , JOptionPane.ERROR_MESSAGE);

                } catch (CharMappingNotFoundException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "压缩时未找到指定字符映射", "出错啦" , JOptionPane.ERROR_MESSAGE);

                }
                JOptionPane.showMessageDialog(MainFrame.this, "压缩成功", "成功!", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                // 路径无效
                JOptionPane.showMessageDialog(MainFrame.this, "文件不存在", "出错啦", JOptionPane.ERROR_MESSAGE);
            }
        });


        // 解压按钮监听
        chooseFileUnpack.addActionListener(e -> {

            String compressedPath = chooseFile("请选择要解压的文件", new HuffmanFilter());
            if (compressedPath != null) {
                // 压缩文件路路径有效
                String jsonPath = chooseFile("请选择压缩清单文件", new JsonFilter());

                if (jsonPath != null) {
                    // 两个路径均有效则进行解压
                    try {
                        Unpack.getInstance().doUnpack(compressedPath, jsonPath);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "文件读写异常：" + e1.getMessage(), "出错啦", JOptionPane.ERROR_MESSAGE);

                    } catch (GetErrorCharException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "压缩时出现非法字符" , "出错啦", JOptionPane.ERROR_MESSAGE);

                    } catch (ParseJsonMappingException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "json清单解析出错", "出错啦" , JOptionPane.ERROR_MESSAGE);

                    } catch (FileWriteFailedException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "文件无法写入", "出错啦", JOptionPane.ERROR_MESSAGE);

                    } catch (FileCreateFailedException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "文件(" +  e1.getPath() + ")无法新建", "出错啦" , JOptionPane.ERROR_MESSAGE);

                    }

                    JOptionPane.showMessageDialog(MainFrame.this, "解压成功", "成功!", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    // 路径无效
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);

                }
            }
            else {
                // 路径无效
                JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);

            }


        });

    }

    /**
     * 初始化视图
     */
    private void initView() {
        Container container = getContentPane();

        // 标题样式
        JLabel mainTitle = new JLabel("Huffman Compress");
        mainTitle.setSize(300, 300);
        mainTitle.setFont(new Font("Dialog", Font.ITALIC, 35));
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // 按钮样式
        chooseFileCompress = new JButton("选择要压缩的文件");
        chooseFileCompress.setPreferredSize(new Dimension(500, 50));

        chooseFileUnpack = new JButton("选择要解压的文件");
        chooseFileUnpack.setPreferredSize(new Dimension(500, 50));

        /* 布局填充 */
        // 上方留白
        for (int i = 0; i < 5; i++) {
            JLabel space = new  JLabel(" ");
            container.add(space);
        }

        container.add(mainTitle);

        // 中部留白
        for (int i = 0; i < 6; i++) {
            JLabel space = new  JLabel(" ");
            container.add(space);
        }

        container.add(chooseFileCompress);
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        container.add(chooseFileUnpack);
        container.setLayout(new VerticalFlowLayout());

    }


    /**
     * 文件打开窗体
     * @return 选择文件的绝对路径
     */
    private String chooseFile(String title, FileFilter fileFilter) {

        JFileChooser choose = new JFileChooser();
        choose.setDialogTitle(title);
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // 添加问价过滤
        if (fileFilter != null) {
            choose.setFileFilter(fileFilter);
        }


        int value = choose.showOpenDialog(MainFrame.this);

        if (value == JFileChooser.APPROVE_OPTION) {
            // 文件正常打开
            File file = choose.getSelectedFile();

            return file.getAbsolutePath();

        }
        else {
            JOptionPane.showMessageDialog(MainFrame.this, "文件打开失败，请重试", "出错啦", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }





}
