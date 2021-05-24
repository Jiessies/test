package com.example.test.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 * @author zsp
 * 
 * @param <T>
 */
public class RespPage<T> implements Serializable {

	private static final long serialVersionUID = -8321031481661100575L;

	/**
	 *
	 */
	private List<T> rows;
	/**
	 *
	 */
	private int total;

	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 *
	 */
	public RespPage() {

	}

	/**
	 *
	 *
	 * @param rows
	 * @param total
	 */
	public RespPage(List<T> rows, int total) {
		this.rows = rows;
		this.total = total;
	}

	/**
	 *
	 * @param <T>
	 * @return
	 */
	public final static <T> RespPage<T> createEmpty(){
		return new RespPage<>(new ArrayList<T>(), 0);
	}

}