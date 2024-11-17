-- 插入房源图片数据
INSERT INTO `t_house_image` (`house_id`, `url`, `type`, `sort`) VALUES
-- 房源1的图片
(1, 'https://example.com/house1/main.jpg', 1, 0),
(1, 'https://example.com/house1/living.jpg', 0, 1),
(1, 'https://example.com/house1/bedroom.jpg', 0, 2),
(1, 'https://example.com/house1/kitchen.jpg', 0, 3),

-- 房源2的图片
(2, 'https://example.com/house2/main.jpg', 1, 0),
(2, 'https://example.com/house2/living.jpg', 0, 1),
(2, 'https://example.com/house2/bedroom.jpg', 0, 2),
(2, 'https://example.com/house2/view.jpg', 0, 3),

-- 房源3的图片
(3, 'https://example.com/house3/main.jpg', 1, 0),
(3, 'https://example.com/house3/pool.jpg', 0, 1),
(3, 'https://example.com/house3/garden.jpg', 0, 2),
(3, 'https://example.com/house3/bedroom.jpg', 0, 3); 