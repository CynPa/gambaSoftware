/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  6:   */ import net.sf.jasperreports.engine.JRException;
/*  7:   */ import net.sf.jasperreports.engine.JRField;
/*  8:   */ 
/*  9:   */ public class QueryResultDataSource
/* 10:   */   implements JRDataSource
/* 11:   */ {
/* 12:   */   private String[] fields;
/* 13:   */   private Iterator iterator;
/* 14:   */   private Object currentValue;
/* 15:   */   
/* 16:   */   public QueryResultDataSource(List list, String[] fields)
/* 17:   */   {
/* 18:19 */     this.fields = fields;
/* 19:20 */     this.iterator = list.iterator();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getFieldValue(JRField field)
/* 23:   */     throws JRException
/* 24:   */   {
/* 25:24 */     Object value = null;
/* 26:25 */     int index = getFieldIndex(field.getName());
/* 27:26 */     if (index > -1)
/* 28:   */     {
/* 29:27 */       Object[] values = (Object[])this.currentValue;
/* 30:28 */       value = values[index];
/* 31:   */     }
/* 32:30 */     return value;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean next()
/* 36:   */     throws JRException
/* 37:   */   {
/* 38:34 */     this.currentValue = (this.iterator.hasNext() ? this.iterator.next() : null);
/* 39:35 */     return this.currentValue != null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   private int getFieldIndex(String field)
/* 43:   */   {
/* 44:39 */     int index = -1;
/* 45:40 */     for (int i = 0; i < this.fields.length; i++) {
/* 46:41 */       if (this.fields[i].equals(field))
/* 47:   */       {
/* 48:42 */         index = i;
/* 49:43 */         break;
/* 50:   */       }
/* 51:   */     }
/* 52:46 */     return index;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.QueryResultDataSource
 * JD-Core Version:    0.7.0.1
 */