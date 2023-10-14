package com.company;


//import java.util.Scanner;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void changeArray(int[] arr1) {
        for (int i = 0; i < 10; i++) {
            if (arr1[i] % 2 != 0) {
                System.out.println("arr[" + i + "]: " + arr1[i]);
                arr1[i] = -1;
            }
        }
//        return arr1;
    }

    public static String changeString(String str, int position, char c) {
        String s1 = str.substring(0, position);
        String s2 = str.substring(position + 1);
        return s1+c+s2;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        int k = in.nextInt();
        int k = 3;
        int[] arr = new int[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = i + i % 3;
        }
        changeArray(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println("arr[" + i + "]: " + arr[i]);
        }
        //sout
        String s = changeString("123",2, '4');
        System.out.println(s);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.get(0);
    }

}
