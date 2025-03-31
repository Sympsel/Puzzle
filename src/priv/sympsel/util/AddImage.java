package priv.sympsel.util;

import priv.sympsel.Config;
import priv.sympsel.resource.ImagePath;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.*;
import java.util.Scanner;

import static priv.sympsel.resource.ImagePath.imageGroupPath;

public class AddImage {
    public static void imageProcessing(String imagePath) throws IOException {
//        // 输入图片路径和输出目录路径
//        String imagePath = "D:/桌面/棒棒糖.jpg";

        String copiedImagePath = "image/tempimage";
        copySourcePicture(imagePath,copiedImagePath);

        // 图片改变尺寸后的路径
        String outputDirectory1 = "D:/桌面";
        // 最终序列图的输出文件夹目录
        String outputDirectoryFinal = imageGroupPath +
                NonConfigurableVariables.MAX_PICTURE_COUNT;

        // 原图片的新尺寸
        int newWidth = 4 * Config.WIDTH;
        int newHeight = 4 * Config.HEIGHT;

        // 拆分网格的X和Y
        int X = 4;
        int Y = 4;

        // 重命名的图片文件类型
        String type = ".jpg";

        // 改变原图尺寸
        String reSizedImage = ImageUtils.resizeImage(
                imagePath, outputDirectory1, newWidth, newHeight);

        // 切割图片
        ImageSplitter.splitImage(outputDirectory1, outputDirectoryFinal, X, Y);

        // 批量重命名文件
        BatchRenameFiles.renameFiles(outputDirectoryFinal, type);

        // 重命名原图


        // 删除临时图片
        deleteDirectoryContents(copiedImagePath);
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
            int i = 0;
            // 遍历文件数组，批量修改文件名
            for (File file : files) {
                // 构造新的文件名
                String newFileName = i + type;
                // 创建新的File对象，表示新文件名
                File newFile = new File(directoryPath + File.separator + newFileName);

                // 重命名文件
                if (file.renameTo(newFile)) {
                    System.out.printf("%s %s %s %s\n", "文件 ", file.getName(), " 已成功重命名为 ", newFileName);
                } else {
                    System.out.printf("%s %s\n", "无法重命名文件 ", file.getName());
                }
                i++;
            }
        }

        public static void renamePicture(String path, String newPathNameNoExtend, String type) {
            File file = new File(path);
            File newFile = new File(newPathNameNoExtend + type);

            if (file.renameTo(newFile)) {
                System.out.println("文件 " + path + " 已成功重命名为 " + newPathNameNoExtend + type);
            } else {
                System.out.println("无法重命名文件 " + path);
            }
        }
    }

    public static class ImageSplitter {

        public static void splitImage(String imagePath, String outputDirectory, int X, int Y) {
            try {
                // 读取原始图像
                File imageFile = new File(imagePath);
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
                        String blockFileName = outputDirectory + File.separator + "block_" + row + "_" + col + ".png";
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
            ImageIO.write(resizedImage, "jpg", outputFile);
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

    public static void appendPictureALL() throws IOException {
        System.out.println("请输入图片路径");
        // todo 把读取图片改为读取配置文件
        Scanner sc = new Scanner(System.in);
        String imagePath = sc.nextLine();
        // 在指定位置创建文件夹
        String newFileName = "/p" + NonConfigurableVariables.MAX_PICTURE_COUNT;
        Path imageGroupPath = Paths.get(ImagePath.imageGroupPath + newFileName);
        try {
            Files.createDirectories(imageGroupPath);
            System.out.println("图片添加成功！目前" +
                    NonConfigurableVariables.MAX_PICTURE_COUNT + "张图片");
        } catch (FileAlreadyExistsException e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，图片组已存在");
        } catch (Exception e) {
            System.out.println("AddImage.imageGroupPath: 添加失败，未知错误");
        }

        // 处理文件夹中的图像
        imageProcessing(imagePath);
    }

    public static void copySourcePicture(String path,String objDest) throws IOException {

        File sourceImage = new File(path);
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
}