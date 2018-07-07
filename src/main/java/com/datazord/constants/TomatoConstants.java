package com.datazord.constants;

public class TomatoConstants {

	public static final Long ERROR_CODE_SUCCESS = 200L;
	public static final Long ERROR_CODE_FAILED = 250L;

	public static final Integer SIZE_PRODUCT_OPTION = 1;
	public static final Integer COLOR_PRODUCT_OPTION = 13;
	
	public static final String TOMATO_COMPANY_ID = "1";

//	public static String xmlFilePath = "E:\\my private Work\\TomatoWorkSpace\\Items.xml";
	public static String xmlFilePath = "C:\\Users\\Mohamed\\Desktop\\Items.xml";

	public static String MissedMappingExceptionMessage = "Please Check Mapping to continue";

	public static String DESTINATION_CATEGORY_JSON_PATH = "Product.model";
	public static String DESTINATION_COLOR_JSON_PATH = "Product.product_custom_option.color_id";
	public static String DESTINATION_SIZE_JSON_PATH = "Product.product_custom_option.child.size_id";
}
