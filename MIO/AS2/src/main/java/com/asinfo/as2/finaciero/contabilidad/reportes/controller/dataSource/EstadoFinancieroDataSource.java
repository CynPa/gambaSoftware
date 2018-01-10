/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleEstadoFinanciero;
/*  4:   */ import java.util.List;
/*  5:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  6:   */ import net.sf.jasperreports.engine.JRException;
/*  7:   */ import net.sf.jasperreports.engine.JRField;
/*  8:   */ 
/*  9:   */ public class EstadoFinancieroDataSource
/* 10:   */   implements JRDataSource
/* 11:   */ {
/* 12:   */   private List<DetalleEstadoFinanciero> listaDetalleEstadoFinanciero;
/* 13:   */   
/* 14:   */   public Object getFieldValue(JRField field)
/* 15:   */     throws JRException
/* 16:   */   {
/* 17:40 */     Object value = null;
/* 18:   */     
/* 19:42 */     String fieldName = field.getName();
/* 20:44 */     if (fieldName.equals("f_saldo")) {
/* 21:45 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getSaldo();
/* 22:46 */     } else if (fieldName.equals("f_nivel")) {
/* 23:47 */       value = Integer.valueOf(((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getNivel());
/* 24:48 */     } else if (fieldName.equals("f_nombre_cuenta_contable")) {
/* 25:50 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getNombreCuentaContable();
/* 26:51 */     } else if (fieldName.equals("f_codigo_cuenta")) {
/* 27:52 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getCodigoCuenta();
/* 28:53 */     } else if (fieldName.equals("f_debe")) {
/* 29:54 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getDebe();
/* 30:55 */     } else if (fieldName.equals("f_haber")) {
/* 31:56 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getHaber();
/* 32:57 */     } else if (fieldName.equals("f_saldo_deudor")) {
/* 33:58 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getSaldoDeudor();
/* 34:59 */     } else if (fieldName.equals("f_saldo_acreedor")) {
/* 35:60 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getSaldoAcreedor();
/* 36:61 */     } else if (fieldName.equals("f_saldo_inicial")) {
/* 37:62 */       value = ((DetalleEstadoFinanciero)this.listaDetalleEstadoFinanciero.get(this.index)).getSaldoInicial();
/* 38:   */     }
/* 39:65 */     return value;
/* 40:   */   }
/* 41:   */   
/* 42:68 */   private int index = -1;
/* 43:   */   
/* 44:   */   public boolean next()
/* 45:   */     throws JRException
/* 46:   */   {
/* 47:77 */     this.index += 1;
/* 48:78 */     return this.index < this.listaDetalleEstadoFinanciero.size();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public List<DetalleEstadoFinanciero> getListaDetalleEstadoFinanciero()
/* 52:   */   {
/* 53:86 */     return this.listaDetalleEstadoFinanciero;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setListaDetalleEstadoFinanciero(List<DetalleEstadoFinanciero> listaDetalleEstadoFinanciero)
/* 57:   */   {
/* 58:91 */     this.listaDetalleEstadoFinanciero = listaDetalleEstadoFinanciero;
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.EstadoFinancieroDataSource
 * JD-Core Version:    0.7.0.1
 */