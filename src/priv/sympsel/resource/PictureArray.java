package priv.sympsel.resource;

import priv.sympsel.util.Util;

import java.util.Random;

import static priv.sympsel.Config.disruptStep;

public class PictureArray {
    public static int x = 3, y = 3;

    private static final int[] arr_1 =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    private static final int[][] arr_2 = {
            {1, 2, 3, 4}, {5, 6, 7, 8},
            {9, 10, 11, 12}, {13, 14, 15, 0}
    };

    public static int[][] data = new int[4][4];

    public static int[] getArr_1() {
        int[] ret = new int[arr_1.length];
        System.arraycopy(arr_1, 0, ret, 0, arr_1.length);
        return ret;
    }

    public static int[][] getArr_2() {
        int[][] ret = new int[arr_2.length][arr_2[0].length];
        for (int i = 0; i < arr_2.length; i++) {
            System.arraycopy(arr_2[i], 0, ret[i], 0, arr_2[0].length);
        }
        return ret;
    }

    public static int[] disrupt_1() {
        int[] result = getArr_1().clone();
        Random r = new Random();
        for (int i = 0; i < result.length; i++) {
            int index = r.nextInt(result.length);
            int temp = result[i];
            result[i] = result[index];
            result[index] = temp;
        }
        return result;
    }
    // 采用逆序打乱策略
    public static int[][] disrupt_2() {
        // 重置x，y的值，使打乱后理论可复原
        x = 3;
        y = 3;
        int[][] result = getArr_2().clone();
        Random r = new Random();

        for (int i = 0; i < disruptStep; i++) {
            int choice = r.nextInt(4) + 37;
            move(result, choice);
        }

        for (int i = 0; i < 16; i++) {
            if (result[i % 4][i / 4] == 0) {
                x = i % 4;
                y = i / 4;
            }

        }
        return result;
    }

    public static void move(int[][] data, int choice) {
        switch (choice) {

            // 由于原数组序列的顺序，所以需要将x,y进行转换
            // 即<-: 38, ^: 37, ->: 40, v: 39 以替换正常的<-: 37, ^: 38, ->: 39, v: 40
            // 对游玩无影响
            case 38:
                if (x != 0) {
                    x--;
                    Util.swap(data, x, y, x + 1, y);
                }
                break;
            case 37:
                if (y != 0) {
                    y--;
                    Util.swap(data, x, y, x, y + 1);
                }
                break;
            case 40:
                if (x != 3) {
                    x++;
                    Util.swap(data, x, y, x - 1, y);
                }
                break;
            case 39:
                if (y != 3) {
                    y++;
                    Util.swap(data, x, y, x, y - 1);
                }
                break;
        }
    }

    public static void testPrint(int[][] data) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void testPrint(int[] data) {
        for (int datum : data) {
            System.out.print(datum + " ");
        }
        System.out.println();
    }

}
