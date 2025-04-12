package priv.sympsel.util;

import priv.sympsel.Config;
import priv.sympsel.resource.Path;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.*;
import java.util.Scanner;

import static priv.sympsel.resource.Path.imageGroupPath;

public class AddImage {
    public static void imageProcessing(String image) throws IOException {
        // 最终序列图的输出文件夹目录
        String outputDirectoryFinal = String.format("%s%s%d", imageGroupPath, "/p", NonConfigurableVariables.MAX_PICTURE_COUNT + 1);

        // 原图片的新尺寸
        int newWidth = 4 * Config.WIDTH;
        int newHeight = 4 * Config.HEIGHT;

        // 拆分网格的X和Y
        int X = 4;
        int Y = 4;

        // 复制图片到临时文件夹并重命名
        String copiedImagePathTo = Path.tempDir;
        String copiedImageNewName = String.format("%s%s%s%s", copiedImagePathTo, "/", "whole", Config.type);
        fileCopies(image, copiedImagePathTo);

        // 改变原图尺寸
        resizeImage(copiedImageNewName, newWidth, newHeight);

        // 切割图片
        ImageSplitter.splitImage(String.format("%s%s%s", copiedImagePathTo, "/whole", Config.type),
                outputDirectoryFinal, X, Y);

        moveFiles(String.format("%s%s%S", copiedImagePathTo, "/whole", Config.type),
                String.format("%s%s%s%s%s", imageGroupPath, "/p", Util.getImageGroupNumber(imageGroupPath)
                        , "/whole", Config.type));

        // 删除临时图片及空图片组
        deleteDirectoryContents(copiedImagePathTo);
    }

    public static void moveFiles(String sourcePath, String destinationPath) {
        java.nio.file.Path source = Paths.get(sourcePath);
        java.nio.file.Path destination = Paths.get(destinationPath);

        try {
            // 移动文件
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.printf("%s%s\n%n", "Error moving files: ", e.getMessage());
        }
    }

    public static void resizeImage(String srcImage, int targetWidth, int targetHeight) throws IOException {
        File srcImageFile = new File(srcImage);
        if (!srcImageFile.exists()) {
            System.out.println("源图片不存在");
            return;
        }
        if (srcImageFile.isDirectory()) {
            System.out.println("源图片是一个目录，请指定一个文件！");
            return;
        }
        // 读取图片
        BufferedImage originalImage = ImageIO.read(srcImageFile);

        // 创建一个新的BufferedImage对象，具有指定的宽度和高度
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        // 绘制缩小后的图片
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        // 保存调整大小后的图片，覆盖原文件
        ImageIO.write(resizedImage, Config.format, srcImageFile);
    }

    public static class ImageSplitter {

        public static void splitImage(String imagePath, String outputDirectory, int X, int Y) {
            System.out.println("切割图片");
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("图片不存在");
            }
            try {
                // 读取原始图像

                BufferedImage originalImage = ImageIO.read(imageFile);

                // 获取原始图像的宽度和高度
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();

                // 每个小块的宽度和高度
                int blockWidth = width / X;
                int blockHeight = height / Y;

                // 确保输出目录存在
                File outputDir = new File(outputDirectory);
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }

                // 遍历X×Y网格，切割并保存每个小块
                for (int row = 0; row < Y; row++) {
                    for (int col = 0; col < X; col++) {
                        // 计算每个小块的坐标
                        int x = col * blockWidth;
                        int y = row * blockHeight;
                        // 创建一个小块的BufferedImage
                        BufferedImage block = originalImage.getSubimage(x, y, blockWidth, blockHeight);

                        // 保存小块到文件
                        String blockFileName = outputDirectory + File.separator + (row * Y + col + 1) + Config.type;
                        File blockFile = new File(blockFileName);
                        ImageIO.write(block, "png", blockFile);

                        System.out.printf("%s%s\n", "Saved block: ", blockFileName);
                    }
                }

            } catch (IOException e) {
                System.err.printf("%s%s\n", "Error processing image: ", imagePath);
                e.printStackTrace();
            }
        }
    }

    public static boolean appendPicture(String imagePath) throws IOException {
        // 在指定位置创建文件夹
        int newNubOfPicture = NonConfigurableVariables.MAX_PICTURE_COUNT + 1;
        String newFileName = String.format("%s%d", "/p", newNubOfPicture);
        java.nio.file.Path imageGroupPath = Paths.get(String.format("%s%s", Path.imageGroupPath, newFileName));
        try {
            Files.createDirectories(imageGroupPath);
            System.out.print("请输入图片路径: ");

            imagePath = Util.reFormatPath(imagePath);

            // 处理文件夹中的图像
            imageProcessing(imagePath);
            System.out.printf("%s%d%s%n", "图片添加成功！目前", newNubOfPicture, "张图片\n");
            return true;
        } catch (FileAlreadyExistsException e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，图片组已存在");
        } catch (Exception e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，未知错误");
            deleteEmptyDir(Path.imageGroupPath);
        }
        return false;
    }

    public static void fileCopies(final String sourceFile, String targetDir) throws IOException {
        File file = new File(sourceFile);
        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(String.format("%s%s%s", targetDir, "/whole", Config.type));
        if (!file.exists()) {
            System.out.println("fileCopies: 文件不存在");
            return;
        }
        int len = (int) file.length();
        byte[] b;
        if (len < 1024 * 4) b = new byte[len];
        else b = new byte[1024 * 4];
        while ((len = fis.read(b)) != -1)
            fos.write(b, 0, len);
        System.out.println("文件拷贝完成");
        fos.close();
        fis.close();
    }


    public static void deleteDirectoryContents(String dir) throws IOException {
        java.nio.file.Path dirPath = Paths.get(dir);
        if (Files.exists(dirPath)) {
            try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(dirPath)) {
                for (java.nio.file.Path path : stream) {
                    if (Files.isDirectory(path)) deleteDirectoryContents(dir); // 递归删除子文件夹
                    else Files.delete(path); // 删除文件
                }
            }
        }
    }

    public static void deleteEmptyDir(String dir) throws IOException {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files == null) return;
        for (File file : files) file.delete();
    }

    public static String getNameWithoutExtension(String filePath) {

        java.nio.file.Path path = Paths.get(filePath);

        // 获取文件名不包含扩展名
        String nameWithoutExtension = path.getFileName().toString();
        int lastIndexOfDot = nameWithoutExtension.lastIndexOf(".");
        if (lastIndexOfDot != -1) {
            nameWithoutExtension = nameWithoutExtension.substring(0, lastIndexOfDot);
        }
        return nameWithoutExtension;
    }

    public static String getName(String filePath) {
        java.nio.file.Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    public static void saveToFile(String FILE_PATH) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(FILE_PATH))) {
            // 写入数据到文件
            bos.write("这是一些需要保存的数据".getBytes());
            // 强制将缓冲区中的数据写入文件
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}