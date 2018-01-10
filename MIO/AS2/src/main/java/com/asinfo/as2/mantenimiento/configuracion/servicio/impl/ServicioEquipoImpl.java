/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.EquipoDao;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.DocumentoEquipo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ImagenEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  13:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  14:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.SessionContext;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ServicioEquipoImpl
/*  25:    */   extends AbstractServicioAS2
/*  26:    */   implements ServicioEquipo
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private transient EquipoDao equipoDao;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioGenerico<DocumentoEquipo> servicioDocumentoEquipo;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioGenerico<ImagenEquipo> servicioImagenEquipo;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  37:    */   @EJB
/*  38:    */   private transient ServicioGenerico<PlanMantenimientoEquipo> servicioPlanMantenimientoEquipo;
/*  39:    */   
/*  40:    */   public List<Equipo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 51 */     return this.equipoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void guardar(Equipo equipo)
/*  46:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 60 */       this.equipoDao.guardar(equipo);
/*  51: 62 */       for (DocumentoEquipo de : equipo.getListaDocumentoEquipo()) {
/*  52: 63 */         this.servicioDocumentoEquipo.guardar(de);
/*  53:    */       }
/*  54: 66 */       for (ImagenEquipo ie : equipo.getListaImagenEquipo()) {
/*  55: 67 */         this.servicioImagenEquipo.guardar(ie);
/*  56:    */       }
/*  57: 69 */       for (DetalleComponenteEquipo dce : equipo.getListaComponenteEquipo()) {
/*  58: 70 */         this.servicioDetalleComponenteEquipo.guardar(dce);
/*  59:    */       }
/*  60: 72 */       for (PlanMantenimientoEquipo plan : equipo.getListaPlanMantenimientoEquipo()) {
/*  61: 73 */         this.servicioPlanMantenimientoEquipo.guardar(plan);
/*  62:    */       }
/*  63:    */     }
/*  64:    */     catch (Exception e)
/*  65:    */     {
/*  66: 76 */       this.context.setRollbackOnly();
/*  67: 77 */       LOG.error(e);
/*  68: 78 */       e.printStackTrace();
/*  69: 79 */       throw new ExcepcionAS2(e);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void eliminar(Equipo equipo)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 86 */       equipo = cargarDetalle(equipo);
/*  79: 87 */       for (DocumentoEquipo de : equipo.getListaDocumentoEquipo()) {
/*  80: 88 */         this.servicioDocumentoEquipo.eliminar(de);
/*  81:    */       }
/*  82: 90 */       for (ImagenEquipo ie : equipo.getListaImagenEquipo()) {
/*  83: 91 */         this.servicioImagenEquipo.eliminar(ie);
/*  84:    */       }
/*  85: 93 */       for (DetalleComponenteEquipo dce : equipo.getListaComponenteEquipo()) {
/*  86: 94 */         this.servicioDetalleComponenteEquipo.eliminar(dce);
/*  87:    */       }
/*  88: 96 */       for (PlanMantenimientoEquipo plan : equipo.getListaPlanMantenimientoEquipo()) {
/*  89: 97 */         this.servicioPlanMantenimientoEquipo.eliminar(plan);
/*  90:    */       }
/*  91: 99 */       this.equipoDao.eliminar(equipo);
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:101 */       this.context.setRollbackOnly();
/*  96:102 */       LOG.error(e);
/*  97:103 */       e.printStackTrace();
/*  98:104 */       throw new ExcepcionAS2(e);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Equipo buscarPorId(Integer id)
/* 103:    */     throws ExcepcionAS2
/* 104:    */   {
/* 105:110 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Equipo cargarDetalle(Equipo equipo)
/* 109:    */   {
/* 110:116 */     return this.equipoDao.cargarDetalle(equipo);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int contarPorCriterio(Map<String, String> filters)
/* 114:    */   {
/* 115:121 */     return this.equipoDao.contarPorCriterio(filters);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public List<Equipo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 119:    */   {
/* 120:126 */     return this.equipoDao.obtenerListaCombo(sortField, sortOrder, filtros);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void actualizarAtributoEntidad(Equipo equipo, HashMap<String, Object> campos)
/* 124:    */   {
/* 125:131 */     this.equipoDao.actualizarAtributoEntidad(equipo, campos);
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.impl.ServicioEquipoImpl
 * JD-Core Version:    0.7.0.1
 */