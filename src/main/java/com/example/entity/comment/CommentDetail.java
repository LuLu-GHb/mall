package com.example.entity.comment;

import com.example.entity.Collect;
import com.example.entity.Comment;
import lombok.Data;

/**
 * 有宇
 * 2024/4/1 12:32
 */
@Data
public class CommentDetail extends Comment {

    private String businessName;
    private String goodsName;
    private String userName;
    private String userAvatar;
}
