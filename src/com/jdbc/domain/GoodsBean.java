package com.jdbc.domain;

public class GoodsBean {
	private int goods_id;// 商品ID
	private String goods_image;// 商品图像
	private String goods_name;// 商品名字
	private double new_price;
	private double old_price;
	private double praise_scale;
	private int scales_volume;
	private String create_time;
	private String goods_promotion;
	private String goods_location;

	public GoodsBean() {
		super();
	}

	private String s_sub_category;
	private String sub_category;
	private String category;

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public double getNew_price() {
		return new_price;
	}

	public void setNew_price(double new_price) {
		this.new_price = new_price;
	}

	public double getOld_price() {
		return old_price;
	}

	public void setOld_price(double old_price) {
		this.old_price = old_price;
	}

	public double getPraise_scale() {
		return praise_scale;
	}

	public void setPraise_scale(double praise_scale) {
		this.praise_scale = praise_scale;
	}

	public int getScales_volume() {
		return scales_volume;
	}

	public void setScales_volume(int scales_volume) {
		this.scales_volume = scales_volume;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getGoods_promotion() {
		return goods_promotion;
	}

	public void setGoods_promotion(String goods_promotion) {
		this.goods_promotion = goods_promotion;
	}

	public String getGoods_location() {
		return goods_location;
	}

	public void setGoods_location(String goods_location) {
		this.goods_location = goods_location;
	}

	public String getS_sub_category() {
		return s_sub_category;
	}

	public void setS_sub_category(String s_sub_category) {
		this.s_sub_category = s_sub_category;
	}

	public String getSub_category() {
		return sub_category;
	}

	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public GoodsBean(int goods_id, String goods_image, String goods_name,
			double new_price, double old_price, double praise_scale,
			int scales_volume, String create_time, String goods_promotion,
			String goods_location, String s_sub_category, String sub_category,
			String category) {
		super();
		this.goods_id = goods_id;
		this.goods_image = goods_image;
		this.goods_name = goods_name;
		this.new_price = new_price;
		this.old_price = old_price;
		this.praise_scale = praise_scale;
		this.scales_volume = scales_volume;
		this.create_time = create_time;
		this.goods_promotion = goods_promotion;
		this.goods_location = goods_location;
		this.s_sub_category = s_sub_category;
		this.sub_category = sub_category;
		this.category = category;
	}

	public GoodsBean(int goods_id, String goods_image, String goods_name,
			double new_price, double old_price, double praise_scale,
			int scales_volume, String create_time, String goods_promotion,
			String goods_location) {
		super();
		this.goods_id = goods_id;
		this.goods_image = goods_image;
		this.goods_name = goods_name;
		this.new_price = new_price;
		this.old_price = old_price;
		this.praise_scale = praise_scale;
		this.scales_volume = scales_volume;
		this.create_time = create_time;
		this.goods_promotion = goods_promotion;
		this.goods_location = goods_location;
	}

	@Override
	public String toString() {
		return "GoodsBean [goods_id=" + goods_id + ", goods_image="
				+ goods_image + ", goods_name=" + goods_name + ", new_price="
				+ new_price + ", old_price=" + old_price + ", praise_scale="
				+ praise_scale + ", scales_volume=" + scales_volume
				+ ", create_time=" + create_time + ", goods_promotion="
				+ goods_promotion + ", goods_location=" + goods_location
				+ ", s_sub_category=" + s_sub_category + ", sub_category="
				+ sub_category + ", category=" + category + "]";
	}

}
