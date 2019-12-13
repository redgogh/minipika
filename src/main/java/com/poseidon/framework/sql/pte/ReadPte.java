package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.PteString;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取pte模板文件
 * Create by 2BKeyboard on 2019/12/13 0:52
 */
public class ReadPte {

    /**
     * 获取pte文件列表
     * @return
     */
    private List<File> getPteFiles() {
        return PoseidonUtils.getPteFiles();
    }

    /**
     * 从Buffer流中读取
     * @return List PteString
     */
    public List<PteString> getPteString() {
        BufferedReader br = null;
        ArrayList<PteString> pteStrings = new ArrayList<>();
        try {
            for (File file : getPteFiles()) {
                String str = null;
                br = new BufferedReader(new FileReader(file));
                PteString pteString = new PteString();
                while ((str = br.readLine()) != null) {
                    pteString.appendLine(str);
                }
                pteStrings.add(pteString);
                close(br);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(br);
        }
        return pteStrings;
    }

    private void close(Closeable closeable) {
        try {
            if (closeable != null) closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadPte readPte = new ReadPte();
        for (PteString pteString : readPte.getPteString()) {
            System.out.println(pteString.toString());
        }
    }

}
