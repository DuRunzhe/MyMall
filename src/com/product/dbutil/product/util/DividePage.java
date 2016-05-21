package com.product.dbutil.product.util;

public class DividePage {
	private int pageSize;// 表示显示条数
	private int recordCount;// 总条数
	private int currentPage;// 表示当前页

	public DividePage(int pageSize, int recordCount, int currentPage) {
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.currentPage=currentPage;//没有这句，currentPage一直是默认值0，
		setCurrentPage(currentPage);
	}

	public DividePage(int pageSize, int recordCount) {
		this(pageSize, recordCount, 1);
	}

	// 获取总页数
	public int getPageCount() {
		int size = recordCount / pageSize;
		int mod = recordCount % pageSize;
		if (mod != 0) {
			size++;
		}
		return recordCount == 0 ? 1 : size;
	}

	public int getFromIndex() {
		return (currentPage - 1) * pageSize;
	}

	public int getToIndex() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		int validPage = currentPage <= 0 ? 1 : currentPage;
		validPage = validPage > this.currentPage ? getPageCount() : validPage;
		// this.currentPage=currentPage;//上页下页可用，其他不行
		this.currentPage = validPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

}
