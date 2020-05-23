/*
 * Copyright (C) 2017 WordPlat Open Source Project
 *
 *      https://wordplat.com/InteractiveKLineView/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wordplat.ikvstockchart.render;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.wordplat.ikvstockchart.drawing.EmptyDataDrawing;
import com.wordplat.ikvstockchart.drawing.HighlightDrawing;
import com.wordplat.ikvstockchart.drawing.IDrawing;
import com.wordplat.ikvstockchart.drawing.TimeLineDrawing;
import com.wordplat.ikvstockchart.drawing.TimeLineGridAxisDrawing;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.StockIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TimeLineRender 分时图</p>
 * <p>Date: 2017/3/9</p>
 *
 * @author afon
 */

public class TimeVolumeLineRender extends AbstractRender {

    private final RectF chartRect = new RectF(); // 分时图显示区域
    private final float[] contentPts = new float[2];
    private final float[] extremumY = new float[2];
    private int minVisibleIndex;
    private int maxVisibleIndex;

    /**
     * 当前缩放下显示的 entry 数量
     */
    private int currentVisibleCount = -1;


    private final List<IDrawing> drawingList = new ArrayList<>();

    private final List<StockIndex> stockIndexList = new ArrayList<>(); // 股票指标列表

    public TimeVolumeLineRender() {
        drawingList.add(new TimeLineGridAxisDrawing());
        drawingList.add(new TimeLineDrawing());
        drawingList.add(new EmptyDataDrawing());
        drawingList.add(new HighlightDrawing());
    }

    public void addDrawing(IDrawing drawing) {
        drawingList.add(drawing);
    }

    public void clearDrawing() {
        drawingList.clear();
    }

    public void addStockIndex(StockIndex stockIndex) {
        stockIndexList.add(stockIndex);
    }

    public void removeStockIndex(StockIndex stockIndex) {
        stockIndexList.remove(stockIndex);
    }

    public void clearStockIndex() {
        stockIndexList.clear();
    }


    public RectF getChartRect() {
        return chartRect;
    }

    private void initDrawingList(RectF rect, List<IDrawing> drawingList) {
        for (IDrawing drawing : drawingList) {
            drawing.onInit(rect, this);
        }
    }


    @Override
    public void setEntrySet(EntrySet entrySet) {
        super.setEntrySet(entrySet);

        postMatrixTouch(chartRect.width(), sizeColor.getTimeLineMaxCount());

        entrySet.computeTimeLineMinMax(0, entrySet.getEntryList().size());
        computeExtremumValue(extremumY, entrySet.getMinY(), entrySet.getDeltaY());
        postMatrixValue(chartRect.width(), chartRect.height(), extremumY[0], extremumY[1]);

        postMatrixOffset(chartRect.left, chartRect.top);
        scroll(0);
    }

    @Override
    public boolean canScroll(float dx) {
        return false;
    }

    @Override
    public boolean canDragging(float dx) {
        return false;
    }

    @Override
    public void onViewRect(RectF viewRect) {
//        chartRect.set(viewRect);
//
//        for (IDrawing drawing : drawingList) {
//            drawing.onInit(chartRect, this);
//        }
        final float candleBottom = viewRect.bottom - sizeColor.getXLabelViewHeight();
        final int remainHeight = (int) (candleBottom - viewRect.top);

        int calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                stockIndex.setEnable(stockIndex.getHeight() > 0
                        && calculateHeight + stockIndex.getHeight() < remainHeight);

                calculateHeight += stockIndex.getHeight();
            }
        }

        chartRect.set(viewRect.left, viewRect.top, viewRect.right, candleBottom - calculateHeight);

        initDrawingList(chartRect, drawingList);

        calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                calculateHeight += stockIndex.getHeight();

                float top = chartRect.bottom + sizeColor.getXLabelViewHeight() + calculateHeight - stockIndex.getHeight();
                float bottom = chartRect.bottom + sizeColor.getXLabelViewHeight() + calculateHeight;

                stockIndex.setRect(
                        viewRect.left + stockIndex.getPaddingLeft(),
                        top + stockIndex.getPaddingTop(),
                        viewRect.right - stockIndex.getPaddingRight(),
                        bottom - stockIndex.getPaddingBottom());

                initDrawingList(stockIndex.getRect(), stockIndex.getDrawingList());
            }
        }
    }

    @Override
    public void zoomIn(float x, float y) {

    }

    @Override
    public void zoomOut(float x, float y) {

    }

    private void renderDrawingList(Canvas canvas, List<IDrawing> drawingList, float minY, float maxY) {
        minVisibleIndex = 0;
        maxVisibleIndex = entrySet.getEntryList().size() - 1;

        if(maxVisibleIndex < 1) return;

        for (int i = minVisibleIndex ; i < maxVisibleIndex + 1 ; i++) {
            for (IDrawing drawing : drawingList) {
                drawing.computePoint(minVisibleIndex, maxVisibleIndex, i);
            }
        }

        for (IDrawing drawing : drawingList) {
            drawing.onComputeOver(canvas, minVisibleIndex, maxVisibleIndex, minY, maxY);
        }

        for (IDrawing drawing : drawingList) {
            drawing.onDrawOver(canvas);
        }
    }

    @Override
    public void render(Canvas canvas) {
        final int count = entrySet.getEntryList().size();
        final int lastIndex = count - 1;



//        final float minY = count > 0 ? entrySet.getEntryList().get(entrySet.getMinYIndex()).getLow() : Float.NaN;
//        final float maxY = count > 0 ? entrySet.getEntryList().get(entrySet.getMaxYIndex()).getHigh() : Float.NaN;
        final float minY = entrySet.getMinY();
        final float maxY = entrySet.getMaxY();

        renderDrawingList(canvas, drawingList, minY, maxY);

        computeVisibleIndex();

        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                float deltaY = stockIndex.getDeltaY();

                if (deltaY > 0) {
                    computeExtremumValue(extremumY,
                            stockIndex.getMinY(),
                            deltaY,
                            stockIndex.getExtremumYScale(),
                            stockIndex.getExtremumYDelta());
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), extremumY[0], extremumY[1]);

                    renderDrawingList(canvas, stockIndex.getDrawingList(), stockIndex.getMinY(), stockIndex.getMaxY());

                } else {
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), Float.NaN, Float.NaN);

                    renderDrawingList(canvas, stockIndex.getDrawingList(), Float.NaN, Float.NaN);
                }
            }
        }
    }

    /**
     * 计算当前显示区域内的 X 轴范围
     */
    private void computeVisibleIndex() {
        contentPts[0] = chartRect.left;
        contentPts[1] = 0;
        invertMapPoints(contentPts);

        currentVisibleCount = 420; //7hours * 60mins

        minVisibleIndex = contentPts[0] <= 0 ? 0 : (int) contentPts[0];
        maxVisibleIndex = minVisibleIndex + currentVisibleCount + 1;
        if (maxVisibleIndex > entrySet.getEntryList().size() - 1) {
            maxVisibleIndex = entrySet.getEntryList().size() - 1;
        }

        // 计算当前显示区域内 entry 在 Y 轴上的最小值和最大值
        entrySet.computeMinMax(minVisibleIndex, maxVisibleIndex, stockIndexList);

        computeExtremumValue(extremumY, entrySet.getMinY(), entrySet.getDeltaY());
//        postMatrixValue(chartRect.width(), chartRect.height(), extremumY[0], extremumY[1]);
    }

}
