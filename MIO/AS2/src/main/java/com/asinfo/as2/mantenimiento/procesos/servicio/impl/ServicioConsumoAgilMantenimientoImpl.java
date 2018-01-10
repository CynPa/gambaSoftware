/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.ConsumoAgilMantenimientoDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioConsumoAgilMantenimiento;
/*  11:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.ejb.SessionContext;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ServicioConsumoAgilMantenimientoImpl
/*  22:    */   extends AbstractServicioAS2
/*  23:    */   implements ServicioConsumoAgilMantenimiento
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @EJB
/*  27:    */   private transient ConsumoAgilMantenimientoDao consumoAgilMantenimientoDao;
/*  28:    */   @EJB
/*  29:    */   private transient GenericoDao<DetalleConsumoAgilMantenimiento> detalleConsumoAgilMAntenimientoDao;
/*  30:    */   @EJB
/*  31:    */   private transient ServicioGenerico<ConsumoAgilMantenimiento> servicioConsumoAgilMantenimiento;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioGenerico<DetalleConsumoAgilMantenimiento> servicioDetalleConsumoAgilMantenimiento;
/*  34:    */   
/*  35:    */   public void guardar(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/*  36:    */     throws AS2ExceptionMantenimiento, ExcepcionAS2, AS2Exception
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 40 */       if (this.consumoAgilMantenimientoDao.buscarPorId(Integer.valueOf(consumoAgilMantenimiento.getId())) == null) {
/*  41: 41 */         this.consumoAgilMantenimientoDao.guardar(consumoAgilMantenimiento);
/*  42:    */       } else {
/*  43: 43 */         this.consumoAgilMantenimientoDao.actualizar(consumoAgilMantenimiento);
/*  44:    */       }
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 46 */       this.context.setRollbackOnly();
/*  49: 47 */       LOG.error(e);
/*  50: 48 */       e.printStackTrace();
/*  51: 49 */       throw new ExcepcionAS2(e);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void eliminar(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/*  56:    */     throws AS2ExceptionMantenimiento, ExcepcionAS2
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60: 56 */       consumoAgilMantenimiento = cargarDetalle();
/*  61: 57 */       for (DetalleConsumoAgilMantenimiento de : consumoAgilMantenimiento.getListaDetalleConsumoAgilMantenimiento()) {
/*  62: 58 */         this.servicioDetalleConsumoAgilMantenimiento.eliminar(de);
/*  63:    */       }
/*  64: 60 */       this.consumoAgilMantenimientoDao.eliminar(consumoAgilMantenimiento);
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68: 62 */       this.context.setRollbackOnly();
/*  69: 63 */       e.printStackTrace();
/*  70: 64 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ConsumoAgilMantenimiento buscarPorId(Integer id)
/*  75:    */   {
/*  76: 71 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<ConsumoAgilMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  80:    */   {
/*  81: 77 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ConsumoAgilMantenimiento cargarDetalle(ConsumoAgilMantenimiento consumoAgilMantenimiento)
/*  85:    */   {
/*  86: 82 */     return this.consumoAgilMantenimientoDao.cargarDetalle();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int contarPorCriterio(Map<String, String> filters)
/*  90:    */   {
/*  91: 88 */     return 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ConsumoAgilMantenimiento cargarDetalle()
/*  95:    */   {
/*  96: 93 */     return this.consumoAgilMantenimientoDao.cargarDetalle();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void guardar(ConsumoAgilMantenimiento consumoAgilMantenimiento, DetalleConsumoAgilMantenimiento detalle)
/* 100:    */     throws AS2ExceptionMantenimiento, ExcepcionAS2, AS2Exception
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:100 */       if (consumoAgilMantenimiento.getResponsableSalidaMercaderia() == null) {
/* 105:101 */         throw new AS2ExceptionMantenimiento("com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioOrdenTrabajoMantenimientoImpl.ERROR_SELECCIONE_RESPONSABLE", new String[] { "RESPONSABLE" });
/* 106:    */       }
/* 107:103 */       if (this.detalleConsumoAgilMAntenimientoDao.buscarPorId(DetalleConsumoAgilMantenimiento.class, Integer.valueOf(detalle.getId())) != null) {
/* 108:104 */         this.detalleConsumoAgilMAntenimientoDao.actualizar(detalle);
/* 109:    */       } else {
/* 110:106 */         this.detalleConsumoAgilMAntenimientoDao.guardar(detalle);
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:110 */       this.context.setRollbackOnly();
/* 116:111 */       e.printStackTrace();
/* 117:112 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void eliminar(DetalleConsumoAgilMantenimiento detalle)
/* 122:    */     throws AS2ExceptionMantenimiento, ExcepcionAS2
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:119 */       if (this.detalleConsumoAgilMAntenimientoDao.buscarPorId(DetalleConsumoAgilMantenimiento.class, Integer.valueOf(detalle.getId())) != null)
/* 127:    */       {
/* 128:120 */         detalle.setEliminado(true);
/* 129:121 */         this.detalleConsumoAgilMAntenimientoDao.eliminar(detalle);
/* 130:    */       }
/* 131:    */       else
/* 132:    */       {
/* 133:123 */         detalle.setEliminado(true);
/* 134:    */       }
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:127 */       this.context.setRollbackOnly();
/* 139:128 */       LOG.error(e);
/* 140:129 */       e.printStackTrace();
/* 141:130 */       throw new ExcepcionAS2(e);
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.servicio.impl.ServicioConsumoAgilMantenimientoImpl
 * JD-Core Version:    0.7.0.1
 */