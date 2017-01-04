package com.jd.page;

public class Pager<T> {

	/**
	 * 一页数据默认20条
	 */
	private int pageSize = 20;
	/**
	 * 当前页码
	 */
	private int pageNo;

	/**
	 * 一共有多少条数据
	 */
	private long total;

	/**
	 * 一共有多少页
	 */
	private int totalPage;

	/**
	 * 分页的url
	 */
	private String pageUrl;

	/**
	 * 获取第一条记录位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.getPageNo() - 1) * this.getPageSize();
	}

	/**
	 * 获取最后记录位置
	 * 
	 * @return
	 */
	public int getLastResult() {
		return this.getPageNo() * this.getPageSize();
	}

	/**
	 * 计算一共多少页
	 */
	public void setTotalPage() {
		this.totalPage = (int) ((this.total % this.pageSize > 0) ? (this.total / this.pageSize + 1)
				: this.total / this.pageSize);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Pager(int pageNo, int pageSize, long totalCount2) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
		this.setTotal(totalCount2);
		this.init();
	}

	/**
	 * 初始化计算分页
	 */
	private void init() {
		this.setTotalPage();// 设置一共页数
	}
}
