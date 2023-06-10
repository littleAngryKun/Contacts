package com.example.contacts_wb;

import java.util.Comparator;
/**
 *实现了 Comparator 接口，用于比较两个 SortModel 对象的大小，以便进行排序
 */

public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		//如果 o1 对象的 sortLetters 属性等于 "@" 或者 o2 对象的 sortLetters 属性等于 "#"，则认为 o1 对象小于 o2 对象，返回 -1；
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			//如果 o1 对象的 sortLetters 属性等于 "#" 或者 o2 对象的 sortLetters 属性等于 "@"，则认为 o1 对象大于 o2 对象，返回 1；
			return 1;
		} else {
			//否则，比较 o1 对象的 sortLetters 属性和 o2 对象的 sortLetters 属性的大小关系，如果 o1 对象的 sortLetters 属性小于 o2 对象的 sortLetters 属性，则返回一个负整数，否则返回一个正整数。
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
