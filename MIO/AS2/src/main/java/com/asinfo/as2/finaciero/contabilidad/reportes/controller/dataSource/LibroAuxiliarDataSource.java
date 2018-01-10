/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  4:   */ import java.util.List;
/*  5:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  6:   */ import net.sf.jasperreports.engine.JRException;
/*  7:   */ import net.sf.jasperreports.engine.JRField;
/*  8:   */ 
/*  9:   */ public class LibroAuxiliarDataSource
/* 10:   */   implements JRDataSource
/* 11:   */ {
/* 12:   */   private List<DetalleAsiento> listaDetalleAsiento;
/* 13:   */   
/* 14:   */   public Object getFieldValue(JRField field)
/* 15:   */     throws JRException
/* 16:   */   {
/* 17:40 */     Object value = null;
/* 18:   */     
/* 19:42 */     String fieldName = field.getName();
/* 20:44 */     if (fieldName.equals("f_numero")) {
/* 21:45 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getNumero();
/* 22:46 */     } else if (fieldName.equals("f_fecha")) {
/* 23:47 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getFecha();
/* 24:48 */     } else if (fieldName.equals("f_concepto")) {
/* 25:49 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getConcepto();
/* 26:50 */     } else if (fieldName.equals("f_descripcion")) {
/* 27:51 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getDescripcion();
/* 28:52 */     } else if (fieldName.equals("f_debe")) {
/* 29:53 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getDebe();
/* 30:54 */     } else if (fieldName.equals("f_haber")) {
/* 31:55 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getHaber();
/* 32:56 */     } else if (fieldName.equals("f_tipo_asiento")) {
/* 33:57 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getTipoAsiento();
/* 34:   */     }
/* 35:60 */     return value;
/* 36:   */   }
/* 37:   */   
/* 38:63 */   private int index = -1;
/* 39:   */   
/* 40:   */   public boolean next()
/* 41:   */     throws JRException
/* 42:   */   {
/* 43:72 */     this.index += 1;
/* 44:73 */     return this.index < this.listaDetalleAsiento.size();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 48:   */   {
/* 49:81 */     return this.listaDetalleAsiento;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 53:   */   {
/* 54:85 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.LibroAuxiliarDataSource
 * JD-Core Version:    0.7.0.1
 */