-- 创建城市表
CREATE TABLE t_cities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL COMMENT '城市编码',
    name VARCHAR(50) NOT NULL COMMENT '城市名称',
    level TINYINT NOT NULL DEFAULT 1 COMMENT '行政级别：1-省份，2-直辖市',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code),
    UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- 创建地区表
CREATE TABLE t_districts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL COMMENT '地区编码',
    name VARCHAR(50) NOT NULL COMMENT '地区名称',
    city_code VARCHAR(50) NOT NULL COMMENT '所属城市编码',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code),
    KEY idx_city_code (city_code),
    CONSTRAINT fk_city_code FOREIGN KEY (city_code) REFERENCES t_cities(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区表';

-- 插入城市数据
INSERT INTO t_cities (code, name, level) VALUES
-- 直辖市
('BJ', '北京市', 2),
('SH', '上海市', 2),
('TJ', '天津市', 2),
('CQ', '重庆市', 2),
-- 省份
('GD', '广东省', 1),
('JS', '江苏省', 1),
('ZJ', '浙江省', 1),
('SC', '四川省', 1),
('FJ', '福建省', 1),
('HN', '湖南省', 1),
('HB', '湖北省', 1),
('SD', '山东省', 1),
('AH', '安徽省', 1),
('HE', '河北省', 1),
('SX', '山西省', 1),
('JX', '江西省', 1),
('GX', '广西壮族自治区', 1),
('YN', '云南省', 1),
('GZ', '贵州省', 1),
('NM', '内蒙古自治区', 1),
('LN', '辽宁省', 1),
('JL', '吉林省', 1),
('HL', '黑龙江省', 1),
('SN', '陕西省', 1),
('GS', '甘肃省', 1),
('QH', '青海省', 1),
('XJ', '新疆维吾尔自治区', 1),
('XZ', '西藏自治区', 1),
('NX', '宁夏回族自治区', 1),
('HI', '海南省', 1),
('TW', '台湾省', 1),
('HK', '香港特别行政区', 2),
('MO', '澳门特别行政区', 2);

-- 插入地区数据
-- 北京市的区县
INSERT INTO t_districts (code, name, city_code) VALUES
('BJ-CP', '昌平区', 'BJ'),
('BJ-HD', '海淀区', 'BJ'),
('BJ-CY', '朝阳区', 'BJ'),
('BJ-DC', '东城区', 'BJ'),
('BJ-XC', '西城区', 'BJ'),
('BJ-FT', '丰台区', 'BJ'),
('BJ-SY', '顺义区', 'BJ');

-- 上海市的区县
INSERT INTO t_districts (code, name, city_code) VALUES
('SH-PD', '浦东新区', 'SH'),
('SH-XH', '徐汇区', 'SH'),
('SH-JA', '静安区', 'SH'),
('SH-HP', '黄浦区', 'SH'),
('SH-BS', '宝山区', 'SH'),
('SH-MH', '闵行区', 'SH'),
('SH-PT', '普陀区', 'SH');

-- 天津市的区县
INSERT INTO t_districts (code, name, city_code) VALUES
('TJ-HD', '河东区', 'TJ'),
('TJ-HX', '河西区', 'TJ'),
('TJ-NK', '南开区', 'TJ'),
('TJ-HB', '河北区', 'TJ'),
('TJ-HQ', '红桥区', 'TJ');

-- 重庆市的区县
INSERT INTO t_districts (code, name, city_code) VALUES
('CQ-YZ', '渝中区', 'CQ'),
('CQ-JB', '江北区', 'CQ'),
('CQ-NA', '南岸区', 'CQ'),
('CQ-SP', '沙坪坝区', 'CQ'),
('CQ-BB', '北碚区', 'CQ');

-- 广东省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('GD-GZ', '广州市', 'GD'),
('GD-SZ', '深圳市', 'GD'),
('GD-DG', '东莞市', 'GD'),
('GD-FS', '佛山市', 'GD'),
('GD-ZH', '珠海市', 'GD'),
('GD-HZ', '惠州市', 'GD'),
('GD-ZS', '中山市', 'GD');

-- 江苏省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('JS-NJ', '南京市', 'JS'),
('JS-SZ', '苏州市', 'JS'),
('JS-WX', '无锡市', 'JS'),
('JS-XZ', '徐州市', 'JS'),
('JS-NT', '南通市', 'JS'),
('JS-YZ', '扬州市', 'JS'),
('JS-ZJ', '镇江市', 'JS');

-- 浙江省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('ZJ-HZ', '杭州市', 'ZJ'),
('ZJ-NB', '宁波市', 'ZJ'),
('ZJ-WZ', '温州市', 'ZJ'),
('ZJ-JX', '嘉兴市', 'ZJ'),
('ZJ-SX', '绍兴市', 'ZJ');

-- 四川省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('SC-CD', '成都市', 'SC'),
('SC-MY', '绵阳市', 'SC'),
('SC-DY', '德阳市', 'SC'),
('SC-MS', '眉山市', 'SC'),
('SC-LS', '乐山市', 'SC'),
('SC-YB', '宜宾市', 'SC'),
('SC-ZG', '自贡市', 'SC');

-- 福建省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('FJ-FZ', '福州市', 'FJ'),
('FJ-XM', '厦门市', 'FJ'),
('FJ-QZ', '泉州市', 'FJ'),
('FJ-ZZ', '漳州市', 'FJ'),
('FJ-PT', '莆田市', 'FJ');

-- 湖南省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('HN-CS', '长沙市', 'HN'),
('HN-ZZ', '株洲市', 'HN'),
('HN-YY', '岳阳市', 'HN'),
('HN-CZ', '常德市', 'HN'),
('HN-YZ', '永州市', 'HN');

-- 辽宁省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('LN-SY', '沈阳市', 'LN'),
('LN-DL', '大连市', 'LN'),
('LN-AS', '鞍山市', 'LN'),
('LN-FS', '抚顺市', 'LN'),
('LN-BX', '本溪市', 'LN');

-- 吉林省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('JL-CC', '长春市', 'JL'),
('JL-JL', '吉林市', 'JL'),
('JL-SP', '四平市', 'JL'),
('JL-BC', '白城市', 'JL'),
('JL-YJ', '延吉市', 'JL');

-- 黑龙江省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('HL-HEB', '哈尔滨市', 'HL'),
('HL-QQH', '齐齐哈尔市', 'HL'),
('HL-JMS', '佳木斯市', 'HL'),
('HL-MDJ', '牡丹江市', 'HL'),
('HL-DQ', '大庆市', 'HL');

-- 陕西省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('SN-XA', '西安市', 'SN'),
('SN-BJ', '宝鸡市', 'SN'),
('SN-XY', '咸阳市', 'SN'),
('SN-WN', '渭南市', 'SN'),
('SN-YL', '榆林市', 'SN');

-- 甘肃省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('GS-LZ', '兰州市', 'GS'),
('GS-JYG', '嘉峪关市', 'GS'),
('GS-BY', '白银市', 'GS'),
('GS-TS', '天水市', 'GS'),
('GS-WW', '武威市', 'GS');

-- 青海省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('QH-XN', '西宁市', 'QH'),
('QH-HX', '海西州', 'QH'),
('QH-HB', '海北州', 'QH'),
('QH-HN', '海南州', 'QH'),
('QH-GL', '果洛州', 'QH');

-- 新疆维吾尔自治区的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('XJ-WLQ', '乌鲁木齐市', 'XJ'),
('XJ-KLY', '克拉玛依市', 'XJ'),
('XJ-TL', '吐鲁番市', 'XJ'),
('XJ-HM', '哈密市', 'XJ'),
('XJ-KS', '喀什市', 'XJ');

-- 西藏自治区的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('XZ-LS', '拉萨市', 'XZ'),
('XZ-RKZ', '日喀则市', 'XZ'),
('XZ-CDD', '昌都市', 'XZ'),
('XZ-LZ', '林芝市', 'XZ'),
('XZ-NQ', '那曲市', 'XZ');

-- 宁夏回族自治区的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('NX-YC', '银川市', 'NX'),
('NX-SZ', '石嘴山市', 'NX'),
('NX-WZ', '吴忠市', 'NX'),
('NX-GY', '固原市', 'NX'),
('NX-ZW', '中卫市', 'NX');

-- 海南省的地级市
INSERT INTO t_districts (code, name, city_code) VALUES
('HI-HK', '海口市', 'HI'),
('HI-SY', '三亚市', 'HI'),
('HI-SZ', '三沙市', 'HI'),
('HI-DA', '儋州市', 'HI'),
('HI-WC', '文昌市', 'HI');

-- 台湾省的主要城市
INSERT INTO t_districts (code, name, city_code) VALUES
('TW-TB', '台北市', 'TW'),
('TW-KH', '高雄市', 'TW'),
('TW-TC', '台中市', 'TW'),
('TW-TN', '台南市', 'TW'),
('TW-TY', '桃园市', 'TW');

-- 香港特别行政区的主要区域
INSERT INTO t_districts (code, name, city_code) VALUES
('HK-CW', '中西区', 'HK'),
('HK-WC', '湾仔区', 'HK'),
('HK-KC', '九龙城区', 'HK'),
('HK-ST', '沙田区', 'HK'),
('HK-YL', '元朗区', 'HK');

-- 澳门特别行政区的主要区域
INSERT INTO t_districts (code, name, city_code) VALUES
('MO-MP', '澳门半岛', 'MO'),
('MO-TK', '氹仔', 'MO'),
('MO-CL', '路环', 'MO');
  