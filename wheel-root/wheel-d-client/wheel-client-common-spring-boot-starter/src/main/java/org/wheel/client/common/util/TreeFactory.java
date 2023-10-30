package org.wheel.client.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.wheel.client.common.constant.CommConstant;
import org.wheel.client.common.enums.CommonExceptionEnum;
import org.wheel.client.common.exception.CommonException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 15:35
 */
@Slf4j
public class TreeFactory<T> {

    private List<T> formNodeList;

    @Getter
    private List<T> toNodeList;

    private Class formClass;

    private String root;

    private String codeField;

    private String nameField;

    private String parentCodeField;

    private String childrenField;

    private String setChildrenField;

    public TreeFactory(List<T> formNodeList, Class formClass, String root, String codeField, String nameField, String parentCodeField, String childrenField, String setChildrenField) {
        this.formNodeList = formNodeList;
        this.formClass = formClass;
        this.root = root;
        this.codeField = codeField;
        this.nameField = nameField;
        this.parentCodeField = parentCodeField;
        this.childrenField = childrenField;
        this.setChildrenField = setChildrenField;
        check();
    }

    public static <T> TreeFactory<T> getInstance(List<T> formNodeList, Class formClass, String root, SFunction<T, ?> funcCodeField, SFunction<T, ?> funcNameField, SFunction<T, ?> funcParentCodeField, SFunction<T, ?> funcChildrenField) {
        String codeField = BeanOperateUtil.getMethodName(funcCodeField);
        String nameField = BeanOperateUtil.getMethodName(funcNameField);
        String parentCodeField = BeanOperateUtil.getMethodName(funcParentCodeField);
        String childrenField = BeanOperateUtil.getMethodName(funcChildrenField);
        String setChildrenField = StrUtil.concat(true,"set",StrUtil.removePrefix(childrenField,"get"));
        return new TreeFactory(formNodeList, formClass, root, codeField, nameField, parentCodeField, childrenField,setChildrenField);
    }


    public static <T> TreeFactory<T> getInstance(List<T> formNodeList, Class formClass, String root, String codeField, String nameField, String parentCodeField, String childrenField,String setChildrenField) {
        return new TreeFactory(formNodeList, formClass, root, codeField, nameField, parentCodeField, childrenField,setChildrenField);
    }


    public void check() {
        if (CollUtil.isEmpty(this.formNodeList)) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "集合为空");
        }
        if (this.formClass == null) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "对象类型为空");
        }
        if (StrUtil.isBlank(this.root)) {
            this.root = StrUtil.isNotBlank(this.root) ? this.root : CommConstant.COMMON_TREE_ROOT;
        }
        if (codeField == null) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "Code Func 为空");
        }
        if (nameField == null) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "Name Func 为空");
        }
        if (parentCodeField == null) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "ParentCode Func 为空");
        }
        if (childrenField == null) {
            throw new CommonException(CommonExceptionEnum.COMMON_PARAM_ERROR, "Children Func 为空");
        }
    }

    public void initTree() {
        this.toNodeList = getChildren(this.root);
    }

    public List<String> getSubNodeList(List<String> codes) {
        if (CollUtil.isEmpty(this.toNodeList)) {
            initTree();
        }
        return handleSubNodeList(codes, this.getToNodeList());
    }

    private List<String> handleSubNodeList(List<String> codes, List<T> treeNodeList) {
        List<String> toNodes = new ArrayList<>();
        for (T node : treeNodeList) {
            String toCode = Convert.toStr(ReflectUtil.invoke(node, this.codeField));
            List<T> children = getChildren(Convert.toStr(ReflectUtil.invoke(node, this.childrenField)));
            if (CollUtil.contains(codes, toCode)) {
                toNodes.add(toCode);
                if (codes.size() == 1) {
                    break;
                }
            }
            if (CollUtil.isNotEmpty(children)) {
                List<String> childrenCodes = handleSubNodeList(codes, children);
                toNodes.addAll(childrenCodes);
            }
        }
        return toNodes;
    }

    public List<String> getTreeNodeList() {
        return getTreeNodeList(null, true);
    }

    public List<String> getTreeNodeList(List<String> codes, Boolean isContain) {
        if (CollUtil.isEmpty(this.toNodeList)) {
            initTree();
        }
        return handleTreeNodeList(codes, this.getToNodeList(), isContain);
    }

    private List<String> handleTreeNodeList(List<String> codes, List<T> treeNodeList, Boolean isContain) {
        Boolean toIsContain = BeanUtil.toBean(isContain, Boolean.class);
        List<String> toNodes = new ArrayList<>();
        for (T node : treeNodeList) {
            String toCode = Convert.toStr(ReflectUtil.invoke(node, this.codeField));
            if (isContain) {
                toNodes.add(toCode);
            } else if (CollUtil.contains(codes, toCode)) {
                toIsContain = true;
                toNodes.add(toCode);
            }
            List<T> children = ReflectUtil.invoke(node, this.childrenField);
            if (CollUtil.isNotEmpty(children)) {
                List<String> childrenCodes = handleTreeNodeList(codes, children, toIsContain);
                toNodes.addAll(childrenCodes);
            }
        }
        return toNodes;
    }


    public void search(String keyword) {
        if (CollUtil.isEmpty(this.toNodeList)) {
            initTree();
        }
        delNode(keyword, this.getToNodeList());
    }

    private void delNode(String keyword, List<T> treeNodeList) {
        Iterator<T> treeList = treeNodeList.iterator();
        while (treeList.hasNext()) {
            T nextNode = treeList.next();
            List<T> childrenList = ReflectUtil.invoke(nextNode, this.childrenField);
            if (CollUtil.isNotEmpty(childrenList)) {
                delNode(keyword, childrenList);
                if (CollUtil.isEmpty(childrenList)) {
                    treeNodeList.remove(nextNode);
                }
            } else {
                String name = ReflectUtil.invoke(nextNode, this.nameField);
                if (!StrUtil.contains(name, keyword)) {
                    treeNodeList.remove(nextNode);
                }
            }
        }
        ;
    }

    public List<T> getChildren(String root) {
        List<T> list = new ArrayList<>();
        for (T node : this.formNodeList) {
            String parentCode = Convert.toStr(ReflectUtil.invoke(node, this.parentCodeField));
            String code = Convert.toStr(ReflectUtil.invoke(node, this.codeField));
            if (StrUtil.equals(root, parentCode)) {
                T bean = BeanUtil.toBean(node, (Class<T>) formClass);
                List<T> children = getChildren(code);
                if (CollUtil.isNotEmpty(children)) {
                    ReflectUtil.invoke(bean, this.setChildrenField, children);
                }
                list.add(bean);
            }
        }
        return list;
    }
}
