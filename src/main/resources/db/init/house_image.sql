-- 修改 t_house_image 表，添加或修改字段名
ALTER TABLE t_house_image
ADD COLUMN image_type INT COMMENT '图片类型(0:普通图片 1:封面图)' AFTER url;

ALTER TABLE t_house_image
    ADD COLUMN deleted TINYINT DEFAULT 0 COMMENT '是否删除';