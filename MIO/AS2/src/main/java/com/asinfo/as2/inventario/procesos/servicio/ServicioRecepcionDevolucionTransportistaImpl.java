/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.RecepcionDevolucionTransportistaDao;
/*   5:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   6:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class ServicioRecepcionDevolucionTransportistaImpl
/*  20:    */   implements ServicioRecepcionDevolucionTransportista
/*  21:    */ {
/*  22:    */   @EJB
/*  23:    */   private RecepcionDevolucionTransportistaDao recepcionDevolucionTransportistaDao;
/*  24:    */   @EJB
/*  25:    */   GenericoDao<PreDevolucionCliente> preDevolucionClienteDao;
/*  26:    */   @EJB
/*  27:    */   private ServicioGenerico<PreDevolucionCliente> servicioPreDevolucionCliente;
/*  28:    */   
/*  29:    */   public void guardar(RecepcionDevolucionTransportista recepcionTransportista)
/*  30:    */     throws AS2Exception
/*  31:    */   {
/*  32: 47 */     validar(recepcionTransportista);
/*  33: 48 */     this.recepcionDevolucionTransportistaDao.guardar(recepcionTransportista);
/*  34: 49 */     for (PreDevolucionCliente preDevolucionCliente : recepcionTransportista.getListaPreDevolucionCliente())
/*  35:    */     {
/*  36: 50 */       if (preDevolucionCliente.isIndicadorProcesar()) {
/*  37: 51 */         preDevolucionCliente.setRecepcionDevolucionTransportista(recepcionTransportista);
/*  38:    */       } else {
/*  39: 53 */         preDevolucionCliente.setRecepcionDevolucionTransportista(null);
/*  40:    */       }
/*  41: 55 */       if (preDevolucionCliente.isIndicadorProcesar()) {
/*  42:    */         try
/*  43:    */         {
/*  44: 57 */           this.servicioPreDevolucionCliente.guardarValidar(preDevolucionCliente, preDevolucionCliente.getListaDetallePreDevolucionCliente());
/*  45:    */         }
/*  46:    */         catch (AS2Exception e)
/*  47:    */         {
/*  48: 59 */           e.printStackTrace();
/*  49:    */         }
/*  50:    */       } else {
/*  51: 62 */         this.preDevolucionClienteDao.guardar(preDevolucionCliente);
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void validar(RecepcionDevolucionTransportista recepcionDevolucionTransportista)
/*  57:    */     throws AS2Exception
/*  58:    */   {
/*  59: 69 */     for (PreDevolucionCliente pdc : recepcionDevolucionTransportista.getListaPreDevolucionCliente()) {
/*  60: 70 */       if (pdc.getRecepcionDevolucionTransportista() != null) {
/*  61: 72 */         for (DetallePreDevolucionCliente dpdc : pdc.getListaDetallePreDevolucionCliente())
/*  62:    */         {
/*  63: 73 */           if (dpdc.isIndicadorProcesar())
/*  64:    */           {
/*  65: 74 */             if (dpdc.getCantidadRecibida().compareTo(dpdc.getCantidadProcesar()) > 0) {
/*  66: 76 */               throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioNotaCreditoClienteImpl.MENSAJE_ERROR_CANTIDAD_DEVUELTA_EXCEDIDA", new String[] { "" + dpdc.getCantidadProcesar(), "" + dpdc.getCantidadRecibida(), dpdc.getProducto().getNombre() });
/*  67:    */             }
/*  68: 78 */             if (dpdc.getCantidadRecibida().compareTo(BigDecimal.ZERO) < 0) {
/*  69: 80 */               throw new AS2Exception("msg_error_campo_negativo", new String[] {"" + dpdc.getProducto().getNombre() + ": " + dpdc.getCantidadRecibida() });
/*  70:    */             }
/*  71:    */           }
/*  72: 83 */           if (null == dpdc.getBodega()) {
/*  73: 84 */             throw new AS2Exception("msg_info_seleccionar_bodega", new String[] { "" });
/*  74:    */           }
/*  75: 86 */           if ((dpdc.getProducto().isIndicadorLote()) && (null == dpdc.getLote())) {
/*  76: 87 */             throw new AS2Exception("msg_error_lote_requerido", new String[] { "" });
/*  77:    */           }
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void eliminar(RecepcionDevolucionTransportista recepcion)
/*  84:    */   {
/*  85: 97 */     this.recepcionDevolucionTransportistaDao.eliminar(recepcion);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public RecepcionDevolucionTransportista buscarPorId(int idRecepcionDevolucionTransportista)
/*  89:    */   {
/*  90:103 */     return (RecepcionDevolucionTransportista)this.recepcionDevolucionTransportistaDao.buscarPorId(Integer.valueOf(idRecepcionDevolucionTransportista));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List<RecepcionDevolucionTransportista> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  94:    */   {
/*  95:109 */     return this.recepcionDevolucionTransportistaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List<RecepcionDevolucionTransportista> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  99:    */   {
/* 100:114 */     return this.recepcionDevolucionTransportistaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int contarPorCriterio(Map<String, String> filters)
/* 104:    */   {
/* 105:119 */     return this.recepcionDevolucionTransportistaDao.contarPorCriterio(filters);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public RecepcionDevolucionTransportista cargarDetalle(int idRecepcionDevolucionTransportista)
/* 109:    */   {
/* 110:124 */     return this.recepcionDevolucionTransportistaDao.cargarDetalle(idRecepcionDevolucionTransportista);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<Object[]> getReporteRecepcionDevolucionTransportista(Date fecha, int idTransportista, boolean soloDiferencias)
/* 114:    */   {
/* 115:129 */     return this.recepcionDevolucionTransportistaDao.getReporteRecepcionDevolucionTransportista(fecha, idTransportista, soloDiferencias);
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioRecepcionDevolucionTransportistaImpl
 * JD-Core Version:    0.7.0.1
 */