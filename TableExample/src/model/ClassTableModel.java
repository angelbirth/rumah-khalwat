package model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ClassTableModel<T> extends AbstractTableModel {
    private Class<T> clazz;
    private List<T> data;
    private String[] headers;
    private String[] getters;
    private String[] setters;

    public ClassTableModel(Class<T> c) {
	clazz = c;
	data = new ArrayList<>();
	Field[] fields = c.getDeclaredFields();
	headers = new String[fields.length];
	setters = new String[fields.length];
	getters = new String[fields.length];
	for (int i = 0; i < fields.length; i++) {
	    headers[i] = fields[i].getName();
	    getters[i] = "get" + Character.toUpperCase(headers[i].charAt(0))
		    + headers[i].substring(1);
	    setters[i] = "set" + Character.toUpperCase(headers[i].charAt(0))
		    + headers[i].substring(1);
	}
    }

    public ClassTableModel(Class<T> c, T[] data) {
	this(c, Arrays.asList(data));
    }

    public ClassTableModel(Class<T> c, List<T> data) {
	this(c);
	setData(data);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	Class<?> type = clazz.getDeclaredFields()[columnIndex].getType();
	if (type == int.class || type == short.class || type == long.class
		|| type == float.class || type == double.class)
	    return Number.class;

	return type;
    }

    @Override
    public String getColumnName(int column) {
	return headers[column];
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public int getColumnCount() {
	return headers.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	T bean = data.get(rowIndex);
	String getter = getters[columnIndex];
	Class<?> type;
	try {
	    type = clazz.getMethod(getter, (Class[]) null).getReturnType();
	    Class<?> parameterType;
	    if (type.isEnum()) {
		parameterType = String.class;
	    } else {
		parameterType = type;
	    }
	    Method setter = clazz
		    .getMethod(setters[columnIndex], parameterType);
	    setter.invoke(bean, aValue);
	} catch (NoSuchMethodException | SecurityException
		| IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {

	}

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	try {
	    return clazz.getMethod(getters[columnIndex], new Class[] {})
		    .invoke(data.get(rowIndex), new Object[] {});
	} catch (IllegalArgumentException | NoSuchMethodException e) {
	    return null;
	} catch (IllegalAccessException | SecurityException e) {
	    throw new RuntimeException(e.getMessage(), e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public synchronized void setData(List<T> newData) {
	data = newData;
	fireTableDataChanged();
    }

    public synchronized void setData(T[] newData) {
	setData(Arrays.asList(newData));
    }

    public synchronized void addData(T newData) {
	data.add(newData);
	fireTableDataChanged();
    }
    // private static String properCase(String s){
    // String[] words=s.split("[A-Z]")
    // }

}
