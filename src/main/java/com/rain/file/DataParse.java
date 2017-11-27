package com.rain.file;

import java.io.*;

public class DataParse {
    public static void main(String[] args) {
        parse("C:\\Users\\Administrator\\Desktop\\1.txt");
    }

    public static void parse(String fileName) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            String s= null;
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println(s.split("\\s")[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
