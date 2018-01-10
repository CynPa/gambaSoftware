/*  1:   */ package com.asinfo.as2.ventas.reportes.datasource;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Bodega;
/*  4:   */ import com.asinfo.as2.entities.DespachoCliente;
/*  5:   */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  6:   */ import com.asinfo.as2.entities.Empresa;
/*  7:   */ import com.asinfo.as2.entities.Producto;
/*  8:   */ import com.asinfo.as2.entities.Unidad;
/*  9:   */ import java.util.List;
/* 10:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 11:   */ import net.sf.jasperreports.engine.JRException;
/* 12:   */ import net.sf.jasperreports.engine.JRField;
/* 13:   */ 
/* 14:   */ @Deprecated
/* 15:   */ public class ReporteDespachoClienteDataSource
/* 16:   */   implements JRDataSource
/* 17:   */ {
/* 18:   */   private DespachoCliente despachoCliente;
/* 19:29 */   private int index = -1;
/* 20:   */   
/* 21:   */   public Object getFieldValue(JRField field)
/* 22:   */     throws JRException
/* 23:   */   {
/* 24:42 */     Object value = null;
/* 25:43 */     String fieldName = field.getName();
/* 26:44 */     if (fieldName.equals("numeroDespacho")) {
/* 27:45 */       value = this.despachoCliente.getNumero();
/* 28:46 */     } else if (fieldName.equals("nombreComercial")) {
/* 29:47 */       value = this.despachoCliente.getEmpresa().getNombreComercial();
/* 30:48 */     } else if (fieldName.equals("fechaDespacho")) {
/* 31:49 */       value = this.despachoCliente.getFecha();
/* 32:50 */     } else if (fieldName.equals("nombreProducto")) {
/* 33:51 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getProducto().getNombreComercial();
/* 34:52 */     } else if (fieldName.equals("cantidadProducto")) {
/* 35:53 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getCantidad();
/* 36:54 */     } else if (fieldName.equals("nombreBodega")) {
/* 37:55 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getBodega().getNombre();
/* 38:56 */     } else if (fieldName.equals("pesoProducto")) {
/* 39:57 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getProducto().getPeso();
/* 40:58 */     } else if (fieldName.equals("volumenProducto")) {
/* 41:59 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getProducto().getVolumen();
/* 42:60 */     } else if (fieldName.equals("codigoUnidad")) {
/* 43:61 */       value = ((DetalleDespachoCliente)this.despachoCliente.getListaDetalleDespachoCliente().get(this.index)).getProducto().getUnidad().getCodigo();
/* 44:   */     }
/* 45:63 */     return value;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean next()
/* 49:   */     throws JRException
/* 50:   */   {
/* 51:73 */     this.index += 1;
/* 52:74 */     return this.index < this.despachoCliente.getListaDetalleDespachoCliente().size();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public DespachoCliente getDespachoCliente()
/* 56:   */   {
/* 57:78 */     if (this.despachoCliente == null) {
/* 58:79 */       this.despachoCliente = new DespachoCliente();
/* 59:   */     }
/* 60:81 */     return this.despachoCliente;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 64:   */   {
/* 65:85 */     this.despachoCliente = despachoCliente;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public int getIndex()
/* 69:   */   {
/* 70:89 */     return this.index;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void setIndex(int index)
/* 74:   */   {
/* 75:93 */     this.index = index;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.datasource.ReporteDespachoClienteDataSource
 * JD-Core Version:    0.7.0.1
 */