package com.example.contacts_wb;

import java.io.Serializable;

/**
 * @author
 * SortModel 对象是用于表示联系人信息的，可以将多个 SortModel 对象组合成一个列表，
 * 然后对该列表进行排序，以便实现联系人列表的显示和搜索等功能。
 */
public class SortModel implements Serializable {

	private String name;
	private String sortLetters;

	public SortModel(String name, String sortLetters, boolean isChecked,
			String iconUrl, int sex) {
		super();
		this.name = name;
		this.sortLetters = sortLetters;
		this.isChecked = isChecked;
		this.iconUrl = iconUrl;
		this.sex = sex;
	}

	public SortModel() {
		super();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	private boolean isChecked;
	private String iconUrl;
	private int sex; // 0 男 1 女

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
