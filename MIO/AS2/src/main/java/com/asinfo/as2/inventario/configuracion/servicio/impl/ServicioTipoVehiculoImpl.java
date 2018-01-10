/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.RutaDao;
/*   4:    */ import com.asinfo.as2.dao.TipoVehiculoDao;
/*   5:    */ import com.asinfo.as2.entities.Ciudad;
/*   6:    */ import com.asinfo.as2.entities.Ruta;
/*   7:    */ import com.asinfo.as2.entities.TipoVehiculo;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoVehiculo;
/*  11:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  12:    */ import java.util.HashSet;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Set;
/*  16:    */ import javax.annotation.Resource;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.SessionContext;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.ejb.TransactionAttribute;
/*  21:    */ import javax.ejb.TransactionAttributeType;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ServicioTipoVehiculoImpl
/*  25:    */   implements ServicioTipoVehiculo
/*  26:    */ {
/*  27:    */   @EJB
/*  28:    */   private TipoVehiculoDao tipoVehiculoDao;
/*  29:    */   @EJB
/*  30:    */   private RutaDao rutaDao;
/*  31:    */   @Resource
/*  32:    */   private SessionContext context;
/*  33:    */   
/*  34:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  35:    */   public void guardar(TipoVehiculo tipoVehiculo)
/*  36:    */     throws ExcepcionAS2Inventario, AS2Exception
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 53 */       validar(tipoVehiculo);
/*  41: 54 */       for (Ruta ruta : tipoVehiculo.getListaRuta()) {
/*  42: 55 */         this.rutaDao.guardar(ruta);
/*  43:    */       }
/*  44: 57 */       this.tipoVehiculoDao.guardar(tipoVehiculo);
/*  45:    */     }
/*  46:    */     catch (AS2Exception e)
/*  47:    */     {
/*  48: 59 */       this.context.setRollbackOnly();
/*  49: 60 */       throw e;
/*  50:    */     }
/*  51:    */     catch (Exception e)
/*  52:    */     {
/*  53: 62 */       this.context.setRollbackOnly();
/*  54: 63 */       throw new ExcepcionAS2Inventario(e);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void validar(TipoVehiculo tipoVehiculo)
/*  59:    */     throws AS2Exception
/*  60:    */   {
/*  61: 70 */     for (Ruta ruta : tipoVehiculo.getListaRuta()) {
/*  62: 71 */       if ((!ruta.isEliminado()) && (
/*  63: 72 */         (ruta.getCiudadOrigen() == null) || (ruta.getCiudadDestino() == null) || (ruta.getCiudadOrigen().getId() == 0) || 
/*  64: 73 */         (ruta.getCiudadDestino().getId() == 0))) {
/*  65: 74 */         throw new AS2Exception("msg_error_campo_obligatorio", new String[] { "Ciudad" });
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void validarWarning(TipoVehiculo tipoVehiculo)
/*  71:    */     throws AS2Exception
/*  72:    */   {
/*  73: 82 */     Set<String> listaSet = new HashSet();
/*  74: 83 */     for (Ruta ruta : tipoVehiculo.getListaRuta()) {
/*  75: 84 */       if (!ruta.isEliminado())
/*  76:    */       {
/*  77: 85 */         String key = ruta.getCiudadOrigen().getId() + "~" + ruta.getCiudadDestino().getId() + "~" + ruta.getFormaPagoFlete();
/*  78: 86 */         if (listaSet.contains(key)) {
/*  79: 88 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioTipoVehiculoImpl.MENSAJE_WARNING_CIUDADES_REPETIDAS", new String[] {ruta.getCiudadOrigen().getNombre() + " - " + ruta.getCiudadDestino().getNombre() });
/*  80:    */         }
/*  81: 90 */         listaSet.add(key);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void eliminar(TipoVehiculo tipoVehiculo)
/*  87:    */   {
/*  88:103 */     this.tipoVehiculoDao.eliminar(tipoVehiculo);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public TipoVehiculo buscarPorId(Integer id)
/*  92:    */   {
/*  93:114 */     return (TipoVehiculo)this.tipoVehiculoDao.buscarPorId(id);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TipoVehiculo cargarDetalle(int idTipoVehiculo)
/*  97:    */     throws ExcepcionAS2
/*  98:    */   {
/*  99:119 */     return this.tipoVehiculoDao.cargarDetalle(idTipoVehiculo);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<TipoVehiculo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 103:    */   {
/* 104:130 */     return this.tipoVehiculoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<TipoVehiculo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 108:    */   {
/* 109:140 */     return this.tipoVehiculoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int contarPorCriterio(Map<String, String> filters)
/* 113:    */   {
/* 114:150 */     return this.tipoVehiculoDao.contarPorCriterio(filters);
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioTipoVehiculoImpl
 * JD-Core Version:    0.7.0.1
 */