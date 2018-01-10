/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.SerieInventarioProductoDao;
/*   4:    */ import com.asinfo.as2.dao.SerieProductoDao;
/*   5:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   6:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   7:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   9:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  10:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  13:    */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*  14:    */ import com.asinfo.as2.entities.SerieProducto;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieInventarioProducto;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.Iterator;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.ejb.Lock;
/*  26:    */ import javax.ejb.LockType;
/*  27:    */ import javax.ejb.Singleton;
/*  28:    */ 
/*  29:    */ @Singleton
/*  30:    */ public class ServicioSerieInventarioProductoImpl
/*  31:    */   implements ServicioSerieInventarioProducto
/*  32:    */ {
/*  33:    */   @EJB
/*  34:    */   private transient SerieInventarioProductoDao serieInventarioProductoDao;
/*  35:    */   @EJB
/*  36:    */   private transient SerieProductoDao serieProductoDao;
/*  37:    */   
/*  38:    */   @Lock(LockType.WRITE)
/*  39:    */   public void guardar(SerieInventarioProducto serie)
/*  40:    */   {
/*  41: 57 */     if (!serie.getInventarioProducto().getProducto().getIndicadorSerie().booleanValue()) {
/*  42: 58 */       for (SerieInventarioProducto serieProducto : serie.getInventarioProducto().getListaSerieProducto()) {
/*  43: 59 */         serieProducto.setEliminado(true);
/*  44:    */       }
/*  45:    */     }
/*  46: 64 */     serie.getSerieProducto().setProducto(serie.getInventarioProducto().getProducto());
/*  47: 65 */     serie.getSerieProducto().setBodega(serie.getInventarioProducto().getBodega());
/*  48: 67 */     if (!serie.isEliminado()) {
/*  49: 68 */       serie.getSerieProducto().setActivo(serie.getInventarioProducto().getOperacion() == 1);
/*  50:    */     }
/*  51: 72 */     this.serieProductoDao.guardar(serie.getSerieProducto());
/*  52: 73 */     this.serieInventarioProductoDao.guardar(serie);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public InventarioProducto cargarDetalle(InventarioProducto inventarioProducto)
/*  56:    */   {
/*  57: 78 */     return this.serieInventarioProductoDao.cargarDetalle(inventarioProducto);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<SerieInventarioProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  61:    */   {
/*  62: 83 */     return this.serieInventarioProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void validar(MovimientoInventario movimiento)
/*  66:    */     throws AS2Exception
/*  67:    */   {
/*  68: 89 */     List<InventarioProducto> listaInvPro = new ArrayList();
/*  69: 90 */     for (DetalleMovimientoInventario detalle : movimiento.getDetalleMovimientosInventario()) {
/*  70: 91 */       if (!detalle.isEliminado()) {
/*  71: 92 */         listaInvPro.add(detalle.getInventarioProducto());
/*  72:    */       }
/*  73:    */     }
/*  74: 96 */     validar(listaInvPro);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void validar(RecepcionProveedor recepcion)
/*  78:    */     throws AS2Exception
/*  79:    */   {
/*  80:101 */     List<InventarioProducto> listaInvPro = new ArrayList();
/*  81:102 */     for (DetalleRecepcionProveedor detalle : recepcion.getListaDetalleRecepcionProveedor()) {
/*  82:103 */       if ((!detalle.isEliminado()) && (detalle.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO))) {
/*  83:104 */         listaInvPro.add(detalle.getInventarioProducto());
/*  84:    */       }
/*  85:    */     }
/*  86:108 */     validar(listaInvPro);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void validar(DespachoCliente despacho)
/*  90:    */     throws AS2Exception
/*  91:    */   {
/*  92:114 */     List<InventarioProducto> listaInvPro = new ArrayList();
/*  93:115 */     for (DetalleDespachoCliente detalle : despacho.getListaDetalleDespachoCliente()) {
/*  94:116 */       if (!detalle.isEliminado()) {
/*  95:117 */         listaInvPro.add(detalle.getInventarioProducto());
/*  96:    */       }
/*  97:    */     }
/*  98:121 */     validar(listaInvPro);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void validar(List<InventarioProducto> listaInvPro)
/* 102:    */     throws AS2Exception
/* 103:    */   {
/* 104:127 */     Map<String, String> hmSeries = new HashMap();
/* 105:129 */     for (Iterator localIterator1 = listaInvPro.iterator(); localIterator1.hasNext();)
/* 106:    */     {
/* 107:129 */       inventarioProducto = (InventarioProducto)localIterator1.next();
/* 108:131 */       for (SerieInventarioProducto serieInvPr : inventarioProducto.getListaSerieProducto()) {
/* 109:132 */         if (!serieInvPr.isEliminado())
/* 110:    */         {
/* 111:133 */           producto = inventarioProducto.getProducto();
/* 112:    */           
/* 113:135 */           String clave = producto.getId() + "~" + serieInvPr.getSerieProducto().getCodigo();
/* 114:137 */           if (hmSeries.containsKey(clave)) {
/* 115:140 */             throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioSerieProductoImpl.MENSAJE_INFO_SERIE_DUPLICADA", new String[] { serieInvPr.getSerieProducto().getCodigo(), producto.getCodigo(), producto.getNombre() });
/* 116:    */           }
/* 117:142 */           hmSeries.put(clave, clave);
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:    */     InventarioProducto inventarioProducto;
/* 122:    */     Producto producto;
/* 123:150 */     for (InventarioProducto inventarioProducto : listaInvPro) {
/* 124:152 */       if ((inventarioProducto != null) && (inventarioProducto.getProducto().getIndicadorSerie().booleanValue()))
/* 125:    */       {
/* 126:154 */         Producto producto = inventarioProducto.getProducto();
/* 127:156 */         if (inventarioProducto.getCantidad().intValue() != inventarioProducto.getCantidad().doubleValue()) {
/* 128:158 */           throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioSerieProductoImpl.MENSAJE_INFO_CANTIDAD_LINEA_SERIE_DECIMALES", new String[] { producto.getCodigo(), producto.getNombre(), inventarioProducto.getCantidad().toString() });
/* 129:    */         }
/* 130:162 */         int totalSeries = 0;
/* 131:163 */         for (SerieInventarioProducto serieProducto : inventarioProducto.getListaSerieProducto()) {
/* 132:164 */           if (!serieProducto.isEliminado()) {
/* 133:165 */             totalSeries++;
/* 134:    */           }
/* 135:    */         }
/* 136:169 */         if (totalSeries != inventarioProducto.getCantidad().doubleValue()) {
/* 137:171 */           throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioSerieProductoImpl.MENSAJE_INFO_CANTIDAD_PRODUCTO_SERIE_ERRONEO", new String[] { producto.getCodigo(), producto.getNombre(), inventarioProducto.getCantidad().toString(), String.valueOf(totalSeries) });
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioSerieInventarioProductoImpl
 * JD-Core Version:    0.7.0.1
 */