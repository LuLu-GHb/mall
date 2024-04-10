package com.example.entity;

import lombok.Data;

@Data
public class RelateDTO {
    /** 用户id */
    private Integer useId;
    /** 商品id */
    private Integer goodsId;
    /** 指数 */
    private Integer index;

    public RelateDTO(Integer useId, Integer goodsId, Integer index) {
        this.useId = useId;
        this.goodsId = goodsId;
        this.index = index;
    }
}
