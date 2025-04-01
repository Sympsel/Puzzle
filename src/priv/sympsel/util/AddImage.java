package priv.sympsel.util;

import priv.sympsel.Config;
import priv.sympsel.resource.ImagePath;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.*;
import java.util.Scanner;

import static priv.sympsel.resource.ImagePath.imageGroupPath;

public class AddImage {
    public static void imageProcessing(String image) throws IOException {
//        // 输入图片路径和输出目录路径
//        String imagePath = "D:/桌面/棒棒糖.jpg";

//        // 图片改变尺寸后的路径
//        String tempOutput = "D:/桌面";
        // 最终序列图的输出文件夹目录
        String outputDirectoryFinal = imageGroupPath + "/p" +
                (NonConfigurableVariables.MAX_PICTURE_COUNT + 1);

        // 原图片的新尺寸
        int newWidth = 4 * Config.WIDTH;
        int newHeight = 4 * Config.HEIGHT;

        // 拆分网格的X和Y
        int X = 4;
        int Y = 4;

        // /path/abc.txt -> abc
        String nameWithoutExtension = getNameWithoutExtension(image);
        // /path/abc.txt -> abc.txt
        String imageName = getName(image);

        // 复制图片到临时文件夹并重命名
        String copiedImagePathTo = ImagePath.tempDir;
        String copiedImageNewName = copiedImagePathTo + "/" + "whole" + Config.type;
        copySourcePictureRename(image, copiedImageNewName);

        // 改变原图尺寸
        reSizeImage(copiedImageNewName, newWidth, newHeight);

        // 切割图片
        ImageSplitter.splitImage(copiedImagePathTo + "/whole" + Config.type,
                outputDirectoryFinal, X, Y);

        // 批量重命名文件
//        BatchRenameFiles.renameFiles(outputDirectoryFinal, Config.type);

        moveFiles(copiedImagePathTo + "/whole" + Config.type,
                imageGroupPath + "/" + "p" + Util.getImageGroupNumber(imageGroupPath)
                        + "/whole" + Config.type);

//         删除临时图片
        deleteDirectoryContents(copiedImagePathTo);
        saveToFile(imageGroupPath);
    }

    public static void moveFiles(String sourcePath, String destinationPath) {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        try {
            // 移动文件
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件移动成功！");
        } catch (IOException e) {
            System.out.println("文件移动失败：" + e.getMessage());
        }
    }
    public static void reSizeImage(String srcImage, int targetWidth, int targetHeight) throws IOException {
        File srcImageFile = new File(srcImage);
        if (!srcImageFile.exists()) {
            System.out.println("源图片不存在");
            return;
        }
        if (srcImageFile.isDirectory()) {
            System.out.println("源图片是一个目录，请指定一个文件！");
            return;
        }
        // todo 改变原图片大小并覆盖原图片
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

    public static class BatchRenameFiles {
        public static void renameFiles(String directoryPath, String type) {
            // 创建File对象，表示目录
            File directory = new File(directoryPath);
            // 检查目录是否存在且是一个目录
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("指定的路径不是一个有效的目录！");
                return;
            }
            // 获取目录下的所有文件
            File[] files = directory.listFiles();

            if (files == null || files.length == 0) {
                System.out.println("目录为空，没有文件需要修改！");
                return;
            }
            // 遍历文件数组，批量修改文件名
            for(int i = 1; i < files.length; i++) {
                // 构造新的文件名
                String newFileName = i + type;
                // 创建新的File对象，表示新文件名
                File newFile = new File(directoryPath + File.separator + newFileName);

                // 重命名文件
                if (files[i].renameTo(newFile)) {
                    System.out.printf("%s %s %s %s\n", "文件 ", files[i].getName(), " 已成功重命名为 ", newFileName);
                } else {
                    System.out.printf("%s %s\n", "无法重命名文件 ", files[i].getName());
                }
                i++;
            }
        }

        public static void reNamedFiles(String name, String newName) {
            File file = new File(name);
            if (file.renameTo(new File(newName))) {
                System.out.println("文件 " + name + " 已成功重命名为 " + newName);
            } else {
                System.out.println("无法重命名文件 " + name);
            }
        }
    }

    public static class ImageSplitter {

        public static void splitImage(String imagePath, String outputDirectory, int X, int Y) {
            System.out.println("开始切割图片");
            File imageFile = new File(imagePath);
            if(!imageFile.exists()) {
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

                        System.out.println("Saved block: " + blockFileName);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error processing image: " + imagePath);
                e.printStackTrace();
            }
        }
    }

    public static class ImageUtils {

        // 方法：调整图像大小并保存到指定路径
        public static String resizeImage(
                String imagePath, String outputPath, int targetWidth, int targetHeight
        ) throws IOException {
            File imageFile = new File(imagePath);
            if (!imageFile.exists() || !imageFile.canRead()) {
                throw new IOException("Image file does not exist or is unreadable: " + imagePath);
            }
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                throw new IOException("Failed to read image from file: " + imagePath);
            }
            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
            File outputFile = new File(outputPath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            ImageIO.write(resizedImage, Config.format, outputFile);
            System.out.println("Resized image saved to: " + outputPath);
            return outputPath;
        }

        // 方法：调整图像大小
        private static BufferedImage resizeImage(
                BufferedImage originalImage, int targetWidth, int targetHeight
        ) {
            // 创建一个新的空白图像，目标大小
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedImage.createGraphics();

            // 设置高质量图像缩放
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

            // 清理资源
            graphics2D.dispose();
            return resizedImage;
        }

        // 方法：批量处理文件夹中的图像
        public static void resizeImagesInDirectory(String inputDirectory, String outputDirectory, int targetWidth, int targetHeight) {
            File inputDir = new File(inputDirectory);
            File outputDir = new File(outputDirectory);

            // 如果输出目录不存在，则创建
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // 遍历输入目录中的所有文件
            File[] files = inputDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png"))) {
                        try {
                            // 读取原始图像
                            BufferedImage originalImage = ImageIO.read(file);

                            if (originalImage == null) {
                                System.err.println("Failed to read image: " + file.getName());
                                continue;
                            }

                            // 调整图像大小
                            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);

                            // 保存调整后的图像
                            String outputFilePath = outputDirectory + File.separator + file.getName();
                            ImageIO.write(resizedImage, "jpg", new File(outputFilePath));
                            System.out.println("Resized: " + file.getName());
                        } catch (IOException e) {
                            System.err.println("Error processing file: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static boolean appendPictureALL() throws IOException {
        // 在指定位置创建文件夹
        int newNubOfPicture = NonConfigurableVariables.MAX_PICTURE_COUNT + 1;
        String newFileName = "/p" + newNubOfPicture;
        Path imageGroupPath = Paths.get(ImagePath.imageGroupPath + "/" +  newFileName);
        try {
            Files.createDirectories(imageGroupPath);

            System.out.print("请输入图片路径: ");
            System.out.println("(不要包含引号。)");


            // todo 把读取图片改为读取配置文件
            Scanner sc = new Scanner(System.in);
            String imagePath = sc.nextLine();
            imagePath = Util.reFormatPath(imagePath);

            System.out.println("若未添加成功（已文件目录：image/picture/p<...>为准）删除格式与p1~p6的值不同的文件");


            // 处理文件夹中的图像
//        imageProcessing(imagePath);
            imageProcessing(imagePath);
            System.out.println("图片添加成功！目前" +
                    newNubOfPicture + "张图片");
            return true;
        } catch (FileAlreadyExistsException e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，图片组已存在");
        } catch (Exception e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，未知错误");
        }
        return false;
    }

    public static void copySourcePictureRename(String srcImagePath, String objDest) throws IOException {

        File sourceImage = new File(srcImagePath);
        File destinationImage = new File(objDest);

        try {
            boolean isCopied = sourceImage.renameTo(destinationImage);
            if (isCopied) {
                System.out.println("图片拷贝成功！");

            } else {
                System.out.println("图片拷贝失败，可能是文件正在被使用或路径错误。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteDirectoryContents(String dir) throws IOException {
        Path dirPath = Paths.get(dir);
        if (Files.exists(dirPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
                for (Path path : stream) {
                    if (Files.isDirectory(path)) {
                        deleteDirectoryContents(dir); // 递归删除子文件夹
                    } else {
                        Files.delete(path); // 删除文件
                    }
                }
            }
        }
    }

    public static String getNameWithoutExtension(String filePath){

        Path path = Paths.get(filePath);

        // 获取文件名不包含扩展名
        String nameWithoutExtension = path.getFileName().toString();
        int lastIndexOfDot = nameWithoutExtension.lastIndexOf(".");
        if (lastIndexOfDot != -1) {
            nameWithoutExtension = nameWithoutExtension.substring(0, lastIndexOfDot);
        }
        return nameWithoutExtension;
    }

    public static String getName(String filePath) {
        Path path = Paths.get(filePath);
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