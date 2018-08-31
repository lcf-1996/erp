package com.lcf.erp.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/*
	封装easyui的Tree组件需要的数据
*/
@Data
public class Tree {
	private String id; //树节点的编号
	private String text; //树节点的文本
	private boolean checked; //是否选中
	private List<Tree> children = new ArrayList<Tree>();
}
