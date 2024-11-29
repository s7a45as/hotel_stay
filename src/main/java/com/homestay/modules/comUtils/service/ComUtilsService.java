package com.homestay.modules.comUtils.service;

import java.util.List;
import java.util.Map;

public interface ComUtilsService {
    List<Map<String, String>> getCityList();
    List<Map<String, String>> getDistrictList(String cityCode);
} 