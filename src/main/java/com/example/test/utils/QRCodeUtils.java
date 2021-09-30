package com.example.test.utils;

import com.alibaba.fastjson.JSON;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class QRCodeUtils {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtils.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String imgPath, String destPath,
                                boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
        return file;
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     * @date 2013-12-11 上午10:16:36
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        QRCodeUtils.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        QRCodeUtils.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtils.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeUtils.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtils.decode(new File(path));
    }

    public static void main(String[] args) throws Exception {
//        String text = "huangmingjie";  //这里设置自定义网站url
//        String logoPath = "http://127.0.0.1:8088/workflow/capi/auth/base-shop/qrCodeDrainage";
//        String destPath = "/Users/admin/Downloads";
//        System.out.println(QRCodeUtils.encode(text, logoPath, destPath, true));


        String aaa = "experimentation_subject_id=eyJfcmFpbHMiOnsibWVzc2FnZSI6IklqTXdaak0xTUdZMkxUUTJOalF0TkRWaE15MDVORGt6TFRVNFlqWmtPR0psT1RrNVl5ST0iLCJleHAiOm51bGwsInB1ciI6ImNvb2tpZS5leHBlcmltZW50YXRpb25fc3ViamVjdF9pZCJ9fQ==--d3806b67d576d3c800e589c48431dd7f8c486658; bad_id544ae310-b152-11ea-8c12-810e7227392c=daf50ee1-0eb7-11ec-a842-6727b5a770be; href=http://dohko.developer.yqs.hualala.com/dashboard/4587; nice_id544ae310-b152-11ea-8c12-810e7227392c=d7a3f431-1b51-11ec-acff-0d0de99194d2; SECKEY_CID=d7684371c89004c398272cf11da809990797d189; BMAP_SECKEY=cb9ebb5114207770f9361c444c33319b92ea6d7e948616eadd3fcf2b48e55e7ebd8e2dfac08582834404b575167d597a827ce8af8e2e52b141e311c84ff3a4115138ff948f7e7791f2214756a786f64cc5e5ca190c2a45807725a0d3e16a223aa9724fb25e3e778bfd45dcd4e4d966079b8978b7eef3841a84578289435879e423063768da343e244985162ee885f3f12727fa8d2cf37293a62723745608cbc7f928f5638e33a3c8526d70f4c1f91188c2e4401bb628ce621670d2d627c31589e1ed2e2f698e2e1ececc833909160cbd6de8e9825f11d27788fb2d605a8286b1a3e61702c01ad6606c3070561ddd9124; accessId=544ae310-b152-11ea-8c12-810e7227392c; bad_idbaf5eda0-9c1f-11ea-976c-ab9c4067c7dd=aeb95af1-1f3a-11ec-870e-f73f84de6751; nice_idbaf5eda0-9c1f-11ea-976c-ab9c4067c7dd=aeb95af2-1f3a-11ec-870e-f73f84de6751; qimo_seosource_544ae310-b152-11ea-8c12-810e7227392c=站内; qimo_seokeywords_544ae310-b152-11ea-8c12-810e7227392c=; qimo_xstKeywords_544ae310-b152-11ea-8c12-810e7227392c=; pageViewNum=4; verifyCode=6cd04cd3-928f-40a7-a5c0-03b088288e1a; access_token=c1e2e598-1c3a-41a8-bcf8-7cc2173175bf; expired_time=300";
        try {
            String bbb = new String(aaa.getBytes("UTF-8"), "iso-8859-1");
//            bbb = null;
//            bbb.getBytes();
//            int i = 0;
//            int a = 1 / i;
            log.info(bbb);
        } catch (Exception e) {
            log.error("distBigscreen error ,request={} werwetwert" + e.getLocalizedMessage());
        }


        Map<String, List<String>> map = new HashMap();
        map.put("getChartIdsByWtId", Arrays.asList("WtUpdateEventHandler onEvent", "/apis/data_source/external/data_source_id", "/apis/api/work_table", "/apis/meta/table", "/apis/api/work_table", "/apis/meta/table", "/apis/meta/table"));
        map.put("queryDistBySourceAccId", Arrays.asList("/apis/acc/checkShare"));
        map.put("getDistributeAccIdByChartID", Arrays.asList("/apis/api/data_upload", "/apis/meta/remove_virtual/{id}", "@Bean(\"combineWtRefreshModel\")", "/apis/text_data_source/tcb/upload_data", "/apis/meta/virtual", "/apis/meta/modify_work_virtual", "/apis/workTable/history/remove_data/{wtable_log_id}", "/apis/meta/save_work_virtual", "/apis/workTable/manager/add", "/apis/workTable/tableAuth", "/apis/workTable/tableAuth/{id}", "/apis/workTable/manager/delete", "/apis/workTable/data/modify_origin/{topic_id}", "/apis/workTable/tableAuth", "/apis/workTable/modify/columns", "WtUpdateEventHandler onEvent"));
        map.put("getDbsByMenuId", Arrays.asList("/apis/menu/{menu_id}/dbs"));
        map.put("getDistAccList", Arrays.asList("/apis/all_group/{menu_id}", "/apis/shared_group/{menu_id}", "/apis/acc/delete", "/apis/db/delete/{id}/{shareDeleteFlag}", "/apis/visual/dashboard/{id}", "/apis/acc/delete", "/apis/menu/delete/{menu_id}/{menuIdTop}", "/apis/menu/dist", "/apis/db/edit/{id}", "/apis/menu/move", "/apis/menu/edit"));
        map.put("cancelDistMenu", Arrays.asList("/apis/acc/delete", "/apis/menu/delete/{menu_id}/{menuIdTop}"));
        map.put("distMenu", Arrays.asList("/apis/menu/dist"));
        map.put("getMenuId", Arrays.asList("/apis/dbs/{type}", "/apis/dbs/application/{type}"));
        map.put("getDistMenus", Arrays.asList("/apis/menu/dist"));
        map.put("getAppMenuId", Arrays.asList("/apis/share_menu/{menu_id}/{menu_type}"));
        map.put("getDistByDsId", Arrays.asList("/apis/acc/delete", "/apis/db/delete/{id}/{shareDeleteFlag}", "/apis/visual/dashboard/{id}", "/apis/db/edit/{id}", "/apis/menu/move"));
        map.put("getDistByDestAccIdAndDsId", Arrays.asList("/apis/db/app/copy/check/{dbId}", "/apis/db/copy", "/apis/db/get/{id}/{menu_id}", "/apis/db/jump/get/{id}/{menu_id}", "/apis/data_screen/get/{id}"));
        map.put("getMenuByMenuId", Arrays.asList(""));
        map.put("updateDistMenu", Arrays.asList("/apis/acc/delete", "/apis/db/delete/{id}/{shareDeleteFlag}", "/apis/visual/dashboard/{id}", "/apis/acc/delete", "/apis/menu/delete/{menu_id}/{menuIdTop}", "/apis/db/edit/{id}", "/apis/menu/move", "/apis/menu/edit"));
        map.put("getMenuDistMsg", Arrays.asList("/apis/data_screen/add", "/apis/data_screen/copy", "/apis/menu/get_dist_msg/{is_menu}/{id}"));
        map.put("getDbDistMsg", Arrays.asList("/apis/menu/get_dist_msg/{is_menu}/{id}"));
        map.put("liulei", Arrays.asList("/apis/acc/apis/acc", "/apis/menu/dist", "/apis/menu/{menu_id}/dbs", "/apis/menu/GET{menu_id}/distribute_dbids", "/apis/menu/move", "/apis/menu/edit", "/apis/menu/work_table", "/apis/menu/work_table", "/apis/menu/work_table", "/apis/meta/table", "/apis/menu/distBigscreen", "/apis/workTable/dag/{id}", "/apis/dbs/collect/{type}", "/apis/dbs/private/{type}", "/apis/template/{templateId}", "/apis/dbs/{type}", "/apis/dbs/share/{type}", "/apis/menu/get_dist_msg/{is_menu}/{id}"));

        List<String> responseList = new ArrayList<>();
        map.forEach((k, v) -> {
            responseList.addAll(v);
        });
//        System.out.println("---------------" + JSON.toJSONString(responseList.stream().distinct().collect(Collectors.toList())));


    }

}
