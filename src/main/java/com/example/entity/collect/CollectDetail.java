package com.example.entity.collect;

import com.example.entity.Collect;
import com.example.entity.Goods;
import lombok.Data;

/**
 * 有宇
 * 2024/4/1 12:32
 */
@Data
public class CollectDetail extends Collect {
    private String businessName;
    private String goodsName;
    private String goodsImg;
    private String goodUnit;
    private Double goodsPrice;

}
