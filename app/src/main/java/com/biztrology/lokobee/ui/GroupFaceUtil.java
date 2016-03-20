package com.biztrology.lokobee.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.TypedValue;

/**
 * 头像模式 方的 最多5个
 */
public class GroupFaceUtil {

    private static final String TAG = GroupFaceUtil.class.getSimpleName();
    /**
     * 图片之间的距离
     */
    private static int PADDING = 1;

    /**
     * 现在需要的效果
     */
    public static final int FACE_TYPE_NOW=2;

    /**
     * 和微信一样的
     */
    public static final int FACE_TYPE_WX = 3;



    /**
     * 创建头像
     *
     * @param context
     * @param bitmapArray
     * @return
     */
    public static Bitmap createGroupFace(int type, Context context, Bitmap[] bitmapArray) {
        if (type == FACE_TYPE_WX) {
            //类似于微信一样的效果
            return createTogetherBitmap(bitmapArray, context);
        }
        //现有需求的效果
        return createTogetherBit(bitmapArray, context);
    }

    /**
     * 压缩头像
     *
     * @param paramFloat
     * @param bitmap
     * @return
     */
    private static Bitmap scaleBitmap(float paramFloat, Bitmap bitmap) {
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(paramFloat, paramFloat);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), localMatrix, true);
    }

    /**
     *  dip 转换为px
     */
    private static int dip2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * 拼接群头像
     *
     * @param paramList 群id
     * @param context
     * @return 头像本地路径
     */
    @SuppressWarnings("unused")
    private static Bitmap createTogetherBit(Bitmap[] paramList, final Context context) {
        if (paramList.length < 1 && paramList.length > 5) {
            return null;
        } else if (paramList.length == 1) {
            return oneSpriteImageView(paramList, context);
        } else if (paramList.length == 2) {
            return twoSpriteImageView(paramList, context);
        } else if (paramList.length == 3) {
            return threeSpriteImageView(paramList, context);
        } else if (paramList.length == 4) {
            return fourSpriteImageView(paramList, context);
        } else if (paramList.length == 5) {
            return fiveSpriteImageView(paramList, context);
        } else {
            return null;
        }

    }

    /**
     * 一个图片
     *
     * @param paramList
     * @param context
     * @return
     */
    private static Bitmap oneSpriteImageView(Bitmap[] paramList, Context context) {
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight, Config.ARGB_8888);

        //create new Canvas
        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.WHITE);
        localCanvas.setBitmap(canvasBitmap);




        int colum = 1;
        float scale = 1.0F / colum;
        // 根据列数缩小bitmap
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);

        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);

            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }

        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();

        // 第一个头像与画布顶部的距离
        int firstTop = (tempHeight - scaledHeight) / 2;
        // 第一个头像与画布左部的距离
        int firstLeft = (tempWidth - scaledWidth) / 2;

        //压缩生成图片
        Bitmap bit = scaleBitmap(scale, paramList[0]);
        localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
        bit.recycle();


        PADDING = 2;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }

    /***
     * 两个图片
     *
     * @param paramList
     * @param context
     * @return
     */
    private static Bitmap twoSpriteImageView(Bitmap[] paramList, Context context) {
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight, Config.ARGB_8888);


        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.WHITE);
        localCanvas.setBitmap(canvasBitmap);


        int colum = 2;
        float scale = 1.0F / colum;
        // 根据列数缩小
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);

        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);

            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }


        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();


        //两个图片错位(左右错位)
        float cuowei = scaledWidth * 20 / 100;
        //顶部错位
        int topcuowei = scaledHeight * 20 / 100;


        // 第一个头像与画布顶部的距离
        int firstTop = (tempHeight - (scaledHeight + topcuowei)) / 2;
        // 第一个头像与画布左部的距离
        float firstLeft = (tempWidth - (scaledWidth * 2 - cuowei)) / 2;


        //
        int secondTop = firstTop + topcuowei;
        float secondLeft = firstLeft + scaledWidth - cuowei;


        // 按照最终压缩比例压缩
        Bitmap bit = scaleBitmap(scale, paramList[0]);
        localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
        bit.recycle();


        Bitmap bit1 = scaleBitmap(scale, paramList[1]);
        localCanvas.drawBitmap(bit1, secondLeft, secondTop, null);
        bit1.recycle();


        PADDING = 2;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }

    /**
     * 三个图片
     *
     * @param paramList
     * @param context
     * @return
     */
    private static Bitmap threeSpriteImageView(Bitmap[] paramList, Context context) {
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight, Config.ARGB_8888);


        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.WHITE);
        localCanvas.setBitmap(canvasBitmap);


        int colum = 2;
        float scale = 1.0F / colum;
        // 根据列数缩小
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);

        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);

            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }


        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();


        //两个图片错位(左右错位)
        float cuowei = scaledWidth * 55 / 100;
        //顶部错位
        int topcuowei = scaledHeight * 50 / 100;
        //中间间隙
        int zhongjian = scaledWidth * 10 / 100;


        // 第一个头像与画布顶部的距离
        int firstTop = (tempHeight - (scaledHeight + topcuowei)) / 2;
        // 第一个头像与画布左部的距离
        float firstLeft = (tempWidth - (scaledWidth * 2 + zhongjian)) / 2;


        //
        // int secondTop = firstTop;
        float secondLeft = firstLeft + scaledWidth + zhongjian;


        int threeTop = firstTop + topcuowei;
        float threeLeft = secondLeft - cuowei;


        // 按照最终压缩比例压缩
        Bitmap bit = scaleBitmap(scale, paramList[0]);
        localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
        bit.recycle();

        Bitmap bit1 = scaleBitmap(scale, paramList[1]);
        localCanvas.drawBitmap(bit1, secondLeft, firstTop, null);
        bit1.recycle();


        Bitmap bit2 = scaleBitmap(scale, paramList[2]);
        localCanvas.drawBitmap(bit2, threeLeft, threeTop, null);
        bit2.recycle();


        PADDING = 2;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }

    /**
     * four SpriteImageView
     *
     * @param paramList
     * @param context
     * @return
     */
    private static Bitmap fourSpriteImageView(Bitmap[] paramList, Context context) {
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight, Config.ARGB_8888);


        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.WHITE);
        localCanvas.setBitmap(canvasBitmap);


        int colum = 2;
        float scale = 1.0F / colum;
        // 根据列数缩小
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);

        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);

            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }

        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();
        //两个图片错位(左右错位)
        float cuowei = scaledWidth * 10 / 100;
        //顶部错位
        int topcuowei = scaledHeight * 50 / 100;


        // 第一个头像与画布顶部的距离
        int firstTop = (tempHeight - (scaledHeight + topcuowei)) / 2;
        float firstLeft = (tempWidth - (scaledWidth * 2 - cuowei)) / 2;

        //
        //int secondTop = firstTop;
        float secondLeft = firstLeft + scaledWidth + cuowei;

        //
        int threeTop = firstTop + topcuowei;
        //float threeLeft = firstLeft;

        //
        int fourTop = firstTop + topcuowei;
        //  float fourLeft = secondLeft;

        // 按照最终压缩比例压缩
        Bitmap bit = scaleBitmap(scale, paramList[0]);
        localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
        bit.recycle();

        Bitmap bit1 = scaleBitmap(scale, paramList[1]);
        localCanvas.drawBitmap(bit1, secondLeft, firstTop, null);
        bit1.recycle();

        Bitmap bit2 = scaleBitmap(scale, paramList[2]);
        localCanvas.drawBitmap(bit2, firstLeft, threeTop, null);
        bit2.recycle();

        Bitmap bit3 = scaleBitmap(scale, paramList[3]);
        localCanvas.drawBitmap(bit3, secondLeft, fourTop, null);
        bit3.recycle();


        PADDING = 2;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }

    /**
     * fiveSpriteImageView
     *
     * @param paramList
     * @param context
     * @return
     */
    private static Bitmap fiveSpriteImageView(Bitmap[] paramList, Context context) {
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight, Config.ARGB_8888);

        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.GRAY);
        localCanvas.setBitmap(canvasBitmap);


        int colum = 3;
        float scale = 1.0F / colum;
        // 根据列数缩小
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);

        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);

            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }

        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();


        //两个图片错位(左右错位)
        // float cuowei = scaledWidth;
        //顶部错位
        int topcuowei = scaledHeight * 50 / 100;
        // 上下错位(两个娃娃)
        int shangxia = scaledHeight * 10 / 100;

        // 第一个头像与画布顶部的距离
        int firstTop = (tempHeight - (scaledHeight * 2 + shangxia)) / 2;
        //第一个头像与画布左边的距离
        float firstLeft = (tempWidth - (scaledWidth * 2 + scaledWidth)) / 2;

        //第二个预订部的距离
        // int secondTop = firstTop;
        //第二个左边的距离
        float secondLeft = firstLeft + scaledWidth + scaledWidth;


        //第三个顶部的距离
        int threeTop = firstTop + scaledHeight + shangxia;
        //第三个左边的距离
        //  float threeLeft = firstLeft;

        //第四个顶部的距离
        // int fourTop = threeTop;
        //第四个左边的距离
        //  float fourLeft = secondLeft;


        int fiveTop = firstTop + topcuowei;
        float fiveLeft = firstLeft + scaledWidth;


        // 按照最终压缩比例压缩
        //第一个图片
        Bitmap bit = scaleBitmap(scale, paramList[0]);
        localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
        bit.recycle();

        Bitmap bit1 = scaleBitmap(scale, paramList[1]);
        localCanvas.drawBitmap(bit1, secondLeft, firstTop, null);
        bit1.recycle();

        Bitmap bit2 = scaleBitmap(scale, paramList[2]);
        localCanvas.drawBitmap(bit2, firstLeft, threeTop, null);
        bit2.recycle();

        Bitmap bit3 = scaleBitmap(scale, paramList[3]);
        localCanvas.drawBitmap(bit3, secondLeft, threeTop, null);
        bit3.recycle();

        Bitmap bit4 = scaleBitmap(scale, paramList[4]);
        localCanvas.drawBitmap(bit4, fiveLeft, fiveTop, null);
        bit4.recycle();

        // 重置padding
        PADDING = 1;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }

    /**
     * 拼接群头像(不知道集体有多少,和微信的一样)
     * 最多有9张图片
     *
     * @param paramList
     * @param context
     * @return 头像本地路径
     */
    @SuppressWarnings("unused")
    private static Bitmap createTogetherBitmap(Bitmap[] paramList, final Context context) {
        if (paramList.length < 1 && paramList.length > 9) {
            return null;
        }
        // 先取一个获取宽和高
        Bitmap tempBitmap = paramList[0];
        if (tempBitmap == null) {
            return null;
        }
        // 画布的宽
        int tempWidth = tempBitmap.getWidth();
        // 画布的高
        int tempHeight = tempBitmap.getHeight();
        // 创建一个空格的bitmap
        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight,
                Bitmap.Config.ARGB_8888);
        // 头像的数量
        int bitmapCount = paramList.length;

        Canvas localCanvas = new Canvas();
        localCanvas.drawColor(Color.WHITE);
        localCanvas.setBitmap(canvasBitmap);


        int colum = 0;
        if (bitmapCount > 0 && bitmapCount < 5) {
            colum = 2;
        } else if (bitmapCount > 4 && bitmapCount < 10) {
            colum = 3;
        }
        float scale = 1.0F / colum;

        // 根据列数缩小
        Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);
        if (PADDING > 0) {
            PADDING = dip2px(context, PADDING);
            // 如果有内边距 再次缩小
            float paddingScale = (float) (tempWidth - (colum + 1) * PADDING) / colum / scaledBitmap.getWidth();
            scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
            scale = scale * paddingScale;
        }
        // 第一行的 头像个数
        int topRowCount = bitmapCount % colum;
        // 满行的行数
        int rowCount = bitmapCount / colum;
        if (topRowCount > 0) {
            // 如果第一行头像个数大于零 行数加1
            rowCount++;
        } else if (topRowCount == 0) {
            // 6 或者 9 第一行头像个数和列数一致
            topRowCount = colum;
        }
        // 缩小后头像的宽
        int scaledWidth = scaledBitmap.getWidth();
        // 缩小后头像的高
        int scaledHeight = scaledBitmap.getHeight();
        // 第一个头像与画布顶部的距离
        int firstTop = ((tempHeight - (rowCount * scaledHeight + (rowCount + 1) * PADDING)) / 2) + PADDING;
        // 第一个头像与画布左部的距离
        int firstLeft = ((tempWidth - (topRowCount * scaledWidth + (topRowCount + 1) * PADDING)) / 2) + PADDING;
        for (int i = 0; i < paramList.length; i++) {
            if (i == 9) {// 达到上限 停止
                break;
            }
            // 按照最终压缩比例压缩
            Bitmap bit = scaleBitmap(scale, paramList[i]);
            localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
            firstLeft += (scaledWidth + PADDING);
            if (i == topRowCount - 1 | tempWidth - firstLeft < scaledWidth) {
                firstTop += (scaledHeight + PADDING);
                firstLeft = PADDING;
            }
            bit.recycle();
        }
        // 重置padding
        PADDING = 2;
        localCanvas.save(Canvas.ALL_SAVE_FLAG);
        localCanvas.restore();
        return canvasBitmap;
    }



}
