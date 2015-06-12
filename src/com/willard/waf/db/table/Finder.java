package com.willard.waf.db.table;

import java.lang.reflect.Field;
import java.util.List;

import android.database.Cursor;

import com.willard.waf.db.sqlite.ColumnDbType;
import com.willard.waf.db.sqlite.FinderLazyLoader;
import com.willard.waf.db.utils.LogUtils;
import com.willard.waf.exception.DbException;

/**
 * Author: wyouflf
 * Date: 13-9-10
 * Time: 下午7:43
 */
public class Finder extends Column {

    private final String valueColumnName;
    private final String targetColumnName;

    /* package */ Finder(Class<?> entityType, Field field) {
        super(entityType, field);

        com.willard.waf.db.annotation.Finder finder =
                field.getAnnotation(com.willard.waf.db.annotation.Finder.class);
        this.valueColumnName = finder.valueColumn();
        this.targetColumnName = finder.targetColumn();
    }

    public Class<?> getTargetEntityType() {
        return ColumnUtils.getFinderTargetEntityType(this);
    }

    public String getTargetColumnName() {
        return targetColumnName;
    }

    @Override
    public void setValue2Entity(Object entity, Cursor cursor, int index) {
        Object value = null;
        Class<?> columnType = columnField.getType();
        Object finderValue = TableUtils.getColumnOrId(entity.getClass(), this.valueColumnName).getColumnValue(entity);//得到当前记录entity的ID值 如当前parent实体的id
        if (columnType.equals(FinderLazyLoader.class)) {
            value = new FinderLazyLoader(this, finderValue);//懒加载情况
        } else if (columnType.equals(List.class)) {
            try {
                value = new FinderLazyLoader(this, finderValue).getAllFromDb();//非懒加载情况,根据当前parent实体的id去child中查找符合targetColumnName=id的child的列表
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
        } else {
            try {
                value = new FinderLazyLoader(this, finderValue).getFirstFromDb();//非懒加载情况
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
        }

        if (setMethod != null) {//如果表自定义了set的方法，就利用反射使用自定义的方法
            try {
                setMethod.invoke(entity, value);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                this.columnField.set(entity, value);//如果是懒加载情况则返回一个FinderLazyLoader或ForeignLazyLoader代理类，如果不是懒加载则返回List<Object>或Object
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
    }

    @Override
    public Object getColumnValue(Object entity) {
        return null;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
