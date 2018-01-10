/*   1:    */ package com.asinfo.as2.inventario.reportes.controller.dataSource;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteStockValoradoResumido;
/*   4:    */ import java.util.List;
/*   5:    */ import net.sf.jasperreports.engine.JRDataSource;
/*   6:    */ import net.sf.jasperreports.engine.JRException;
/*   7:    */ import net.sf.jasperreports.engine.JRField;
/*   8:    */ 
/*   9:    */ public class ReporteStockProductoDataSourcePersonalizado
/*  10:    */   implements JRDataSource
/*  11:    */ {
/*  12:    */   private List<ReporteStockValoradoResumido> listaReporteStockValoradoResumido;
/*  13:    */   
/*  14:    */   public Object getFieldValue(JRField field)
/*  15:    */     throws JRException
/*  16:    */   {
/*  17: 38 */     Object value = null;
/*  18:    */     
/*  19: 40 */     String fieldName = field.getName();
/*  20: 42 */     if (fieldName.equals("f_codigoProducto")) {
/*  21: 43 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCodigoProducto();
/*  22: 44 */     } else if (fieldName.equals("f_nombreProducto")) {
/*  23: 45 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getNombreProducto();
/*  24: 46 */     } else if (fieldName.equals("f_codigoSubcategoria")) {
/*  25: 47 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCodigoSubcategoriaProducto();
/*  26: 48 */     } else if (fieldName.equals("f_nombreSubcategoria")) {
/*  27: 49 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getNombreSubcategoriaProducto();
/*  28: 50 */     } else if (fieldName.equals("f_codigoCategoria")) {
/*  29: 51 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCodigoCategoriaProducto();
/*  30: 52 */     } else if (fieldName.equals("f_nombreCategoria")) {
/*  31: 53 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getNombreCategoriaProducto();
/*  32: 54 */     } else if (fieldName.equals("f_recepcion")) {
/*  33: 55 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getRecepcion();
/*  34: 56 */     } else if (fieldName.equals("f_ajusteIngreso")) {
/*  35: 57 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getAjusteIngreso();
/*  36: 58 */     } else if (fieldName.equals("f_transferenciaIngreso")) {
/*  37: 59 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getTransferenciaIngreso();
/*  38: 60 */     } else if (fieldName.equals("f_devolucionCliente")) {
/*  39: 61 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getDevolucionCliente();
/*  40: 62 */     } else if (fieldName.equals("f_ajusteEgreso")) {
/*  41: 63 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getAjusteEgreso();
/*  42: 64 */     } else if (fieldName.equals("f_transferenciaEgreso")) {
/*  43: 65 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getTransferenciaEgreso();
/*  44: 66 */     } else if (fieldName.equals("f_consumo")) {
/*  45: 67 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getConsumo();
/*  46: 68 */     } else if (fieldName.equals("f_despacho")) {
/*  47: 69 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getDespacho();
/*  48: 70 */     } else if (fieldName.equals("f_devolucionProveedor")) {
/*  49: 71 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getDevolucionProvedor();
/*  50: 72 */     } else if (fieldName.equals("f_saldoInicial")) {
/*  51: 73 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getSaldoInicial();
/*  52: 74 */     } else if (fieldName.equals("f_saldoFinal")) {
/*  53: 75 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getSaldoFinal();
/*  54: 76 */     } else if (fieldName.equals("f_nombreBodega")) {
/*  55: 77 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getNombreBodega();
/*  56: 78 */     } else if (fieldName.equals("f_unidad")) {
/*  57: 79 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCodigoUnidad();
/*  58: 80 */     } else if (fieldName.equals("f_costoPromedioAjusteIngreso")) {
/*  59: 81 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioAjusteInventario();
/*  60: 82 */     } else if (fieldName.equals("f_costoPromedioRecepcion")) {
/*  61: 83 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioRecepcion();
/*  62: 84 */     } else if (fieldName.equals("f_costoPromedioTransferenciaIngreso")) {
/*  63: 85 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioTransferenciaIngreso();
/*  64: 86 */     } else if (fieldName.equals("f_costoPromedioDevolucion")) {
/*  65: 87 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioDevolucion();
/*  66: 88 */     } else if (fieldName.equals("f_costoPromedioAjusteEgreso")) {
/*  67: 89 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioAjusteEgreso();
/*  68: 90 */     } else if (fieldName.equals("f_costoPromedioTransferenciaEgreso")) {
/*  69: 91 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioTransferenciaEgreso();
/*  70: 92 */     } else if (fieldName.equals("f_costoPromedioConsumo")) {
/*  71: 93 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioConsumo();
/*  72: 94 */     } else if (fieldName.equals("f_costoPromedioDespachos")) {
/*  73: 95 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioDespachos();
/*  74: 96 */     } else if (fieldName.equals("f_costoPromedioDevolucionProveedor")) {
/*  75: 97 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getCostoPromedioDevolucionProveedor();
/*  76: 98 */     } else if (fieldName.equals("f_precio")) {
/*  77: 99 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getPrecioDespacho();
/*  78:100 */     } else if (fieldName.equals("f_saldoInicialEnPlata")) {
/*  79:101 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getSaldoInicialEnPlata();
/*  80:102 */     } else if (fieldName.equals("f_saldoDevolucionCliente")) {
/*  81:103 */       value = ((ReporteStockValoradoResumido)this.listaReporteStockValoradoResumido.get(this.index)).getSaldoDevolucionCliente();
/*  82:    */     }
/*  83:106 */     return value;
/*  84:    */   }
/*  85:    */   
/*  86:109 */   private int index = -1;
/*  87:    */   
/*  88:    */   public boolean next()
/*  89:    */     throws JRException
/*  90:    */   {
/*  91:118 */     this.index += 1;
/*  92:119 */     return this.index < this.listaReporteStockValoradoResumido.size();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<ReporteStockValoradoResumido> getListaReporteStockValoradoResumido()
/*  96:    */   {
/*  97:132 */     return this.listaReporteStockValoradoResumido;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setListaReporteStockValoradoResumido(List<ReporteStockValoradoResumido> listaReporteStockValoradoResumido)
/* 101:    */   {
/* 102:142 */     this.listaReporteStockValoradoResumido = listaReporteStockValoradoResumido;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.dataSource.ReporteStockProductoDataSourcePersonalizado
 * JD-Core Version:    0.7.0.1
 */