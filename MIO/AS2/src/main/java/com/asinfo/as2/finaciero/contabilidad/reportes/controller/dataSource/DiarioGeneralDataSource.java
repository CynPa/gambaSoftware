/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleAsiento;
/*  4:   */ import java.util.List;
/*  5:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  6:   */ import net.sf.jasperreports.engine.JRException;
/*  7:   */ import net.sf.jasperreports.engine.JRField;
/*  8:   */ 
/*  9:   */ public class DiarioGeneralDataSource
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
/* 20:44 */     if (fieldName.equals("numero")) {
/* 21:45 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getNumero();
/* 22:46 */     } else if (fieldName.equals("fecha")) {
/* 23:47 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getFecha();
/* 24:48 */     } else if (fieldName.equals("concepto")) {
/* 25:49 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getConcepto();
/* 26:50 */     } else if (fieldName.equals("descripcion")) {
/* 27:51 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getDescripcion();
/* 28:52 */     } else if (fieldName.equals("debe")) {
/* 29:53 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getDebe();
/* 30:54 */     } else if (fieldName.equals("haber")) {
/* 31:55 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getHaber();
/* 32:56 */     } else if (fieldName.equals("nombreCuentaContable")) {
/* 33:57 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getNombreCuentaContable();
/* 34:58 */     } else if (fieldName.equals("codigoCuenta")) {
/* 35:59 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getCodigoCuenta();
/* 36:60 */     } else if (fieldName.equals("tipoAsiento")) {
/* 37:61 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getTipoAsiento();
/* 38:62 */     } else if (fieldName.equals("usuarioCreacion")) {
/* 39:63 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getUsuarioCreacion();
/* 40:64 */     } else if (fieldName.equals("fechaCreacion")) {
/* 41:65 */       value = ((DetalleAsiento)this.listaDetalleAsiento.get(this.index)).getFechaCreacion();
/* 42:   */     }
/* 43:69 */     return value;
/* 44:   */   }
/* 45:   */   
/* 46:72 */   private int index = -1;
/* 47:   */   
/* 48:   */   public boolean next()
/* 49:   */     throws JRException
/* 50:   */   {
/* 51:81 */     this.index += 1;
/* 52:82 */     return this.index < this.listaDetalleAsiento.size();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 56:   */   {
/* 57:90 */     return this.listaDetalleAsiento;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 61:   */   {
/* 62:94 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.DiarioGeneralDataSource
 * JD-Core Version:    0.7.0.1
 */