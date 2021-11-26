package com.bh.acl.common;

import com.bh.entity.Permission;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/22 17:22
 * @Description: TODO
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    public static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());
        BitSet bitSet = new BitSet();
        for (Permission it : treeNodes) {
            if(StringUtils.equals(treeNode.getId(),it.getPid())){
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}