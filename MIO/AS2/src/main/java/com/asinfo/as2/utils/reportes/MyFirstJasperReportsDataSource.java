/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  4:   */ import net.sf.jasperreports.engine.JRException;
/*  5:   */ import net.sf.jasperreports.engine.JRField;
/*  6:   */ 
/*  7:   */ public class MyFirstJasperReportsDataSource
/*  8:   */   implements JRDataSource
/*  9:   */ {
/* 10:25 */   private Object[][] data = { { "Chicago", new Integer(39), "Mary Karsen", "202 College Av." }, { "Chicago", new Integer(35), "George Karsen", "412 College Av." }, { "Chicago", new Integer(11), "Julia White", "412 Upland Pl." }, { "Dallas", new Integer(47), "Janet Fuller", "445 Upland Pl." }, { "Dallas", new Integer(43), "Susanne Smith", "2 Upland Pl." }, { "Dallas", new Integer(40), "Susanne Miller", "440 - 20th Ave." }, { "Dallas", new Integer(36), "John Steel", "276 Upland Pl." }, { "Dallas", new Integer(37), "Michael Clancy", "19 Seventh Av." }, { "Dallas", new Integer(19), "Susanne Heiniger", "86 - 20th Ave." }, { "Dallas", new Integer(10), "Anne Fuller", "135 Upland Pl." }, { "Dallas", new Integer(4), "Sylvia Ringer", "365 College Av." }, { "Dallas", new Integer(0), "Laura Steel", "429 Seventh Av." }, { "Lyon", new Integer(38), "Andrew Heiniger", "347 College Av." }, { "Lyon", new Integer(28), "Susanne White", "74 - 20th Ave." }, { "Lyon", new Integer(17), "Laura Ott", "443 Seventh Av." }, { "Lyon", new Integer(2), "Anne Miller", "20 Upland Pl." }, { "New York", new Integer(46), "Andrew May", "172 Seventh Av." }, { "New York", new Integer(44), "Sylvia Ott", "361 College Av." }, { "New York", new Integer(41), "Bill King", "546 College Av." }, { "Oslo", new Integer(45), "Janet May", "396 Seventh Av." }, { "Oslo", new Integer(42), "Robert Ott", "503 Seventh Av." }, { "Paris", new Integer(25), "Sylvia Steel", "269 College Av." }, { "Paris", new Integer(18), "Sylvia Fuller", "158 - 20th Ave." }, { "Paris", new Integer(5), "Laura Miller", "294 Seventh Av." }, { "San Francisco", new Integer(48), "Robert White", "549 Seventh Av." }, { "San Francisco", new Integer(7), "James Peterson", "231 Upland Pl." } };
/* 11:55 */   private int index = -1;
/* 12:   */   
/* 13:   */   public boolean next()
/* 14:   */     throws JRException
/* 15:   */   {
/* 16:62 */     this.index += 1;
/* 17:63 */     return this.index < this.data.length;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Object getFieldValue(JRField field)
/* 21:   */     throws JRException
/* 22:   */   {
/* 23:67 */     Object value = null;
/* 24:   */     
/* 25:69 */     String fieldName = field.getName();
/* 26:71 */     if (fieldName.equals("City")) {
/* 27:72 */       value = this.data[this.index][0];
/* 28:73 */     } else if (fieldName.equals("Id")) {
/* 29:74 */       value = this.data[this.index][1];
/* 30:75 */     } else if (fieldName.equals("Name")) {
/* 31:76 */       value = this.data[this.index][2];
/* 32:77 */     } else if (fieldName.equals("Street")) {
/* 33:78 */       value = this.data[this.index][3];
/* 34:   */     }
/* 35:81 */     return value;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.MyFirstJasperReportsDataSource
 * JD-Core Version:    0.7.0.1
 */