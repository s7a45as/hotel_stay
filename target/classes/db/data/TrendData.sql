-- 插入测试数据
INSERT INTO `t_trend_data` (`type`, `date`, `count`) VALUES
                                                         ('USER', DATE_SUB(CURDATE(), INTERVAL 5 MONTH), 456),
                                                         ('USER', DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 789),
                                                         ('USER', DATE_SUB(CURDATE(), INTERVAL 3 MONTH), 654),
                                                         ('USER', DATE_SUB(CURDATE(), INTERVAL 2 MONTH), 876),
                                                         ('USER', DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 543),
                                                         ('USER', CURDATE(), 987);




-- 插入订单趋势测试数据
INSERT INTO `t_trend_data` (`type`, `date`, `count`) VALUES
                                                         ('ORDER', DATE_SUB(CURDATE(), INTERVAL 5 MONTH), 45),
                                                         ('ORDER', DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 78),
                                                         ('ORDER', DATE_SUB(CURDATE(), INTERVAL 3 MONTH), 56),
                                                         ('ORDER', DATE_SUB(CURDATE(), INTERVAL 2 MONTH), 89),
                                                         ('ORDER', DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 65),
                                                         ('ORDER', CURDATE(), 92);