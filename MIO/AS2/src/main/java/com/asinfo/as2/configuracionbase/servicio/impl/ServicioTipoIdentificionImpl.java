/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacionRemoto;
/*   5:    */ import com.asinfo.as2.dao.TipoIdentificacionDao;
/*   6:    */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   7:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   8:    */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*   9:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  10:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ServicioTipoIdentificionImpl
/*  21:    */   implements ServicioTipoIdentificacion, ServicioTipoIdentificacionRemoto
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private TipoIdentificacionDao tipoIdentificacionDao;
/*  25:    */   @EJB
/*  26:    */   private ServicioGenerico<TipoIdentificacionComprobanteSRI> servicioTipoIdentificacionComprobanteSRI;
/*  27:    */   @EJB
/*  28:    */   private ServicioGenerico<ComprobanteSRICreditoTributarioSRI> servicioComprobanteSRICreditoTributarioSRI;
/*  29:    */   
/*  30:    */   public void guardar(TipoIdentificacion entidad)
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 44 */       for (TipoIdentificacionComprobanteSRI ticsri : entidad.getListaTipoIdentificacionComprobanteSRI())
/*  35:    */       {
/*  36: 45 */         Map<Integer, CreditoTributarioSRI> mapUI = new HashMap();
/*  37: 46 */         Map<Integer, CreditoTributarioSRI> mapDB = new HashMap();
/*  38: 47 */         if (ticsri.getTipoComprobanteSRI().getListaCreditoTributarioSRI() != null) {
/*  39: 48 */           for (CreditoTributarioSRI ctsri : ticsri.getTipoComprobanteSRI().getListaCreditoTributarioSRI()) {
/*  40: 49 */             mapUI.put(Integer.valueOf(ctsri.getIdCreditoTributarioSRI()), ctsri);
/*  41:    */           }
/*  42:    */         }
/*  43: 53 */         for (ComprobanteSRICreditoTributarioSRI csrict : ticsri.getTipoComprobanteSRI().getListaComprobanteSRICreditoTributarioSRI())
/*  44:    */         {
/*  45: 54 */           if (!mapUI.containsKey(Integer.valueOf(csrict.getCreditoTributarioSRI().getIdCreditoTributarioSRI()))) {
/*  46: 55 */             csrict.setEliminado(true);
/*  47:    */           }
/*  48: 57 */           mapDB.put(Integer.valueOf(csrict.getCreditoTributarioSRI().getIdCreditoTributarioSRI()), csrict.getCreditoTributarioSRI());
/*  49: 58 */           this.servicioComprobanteSRICreditoTributarioSRI.guardar(csrict);
/*  50:    */         }
/*  51: 61 */         for (CreditoTributarioSRI ctsri : mapUI.values()) {
/*  52: 62 */           if (!mapDB.containsKey(Integer.valueOf(ctsri.getIdCreditoTributarioSRI())))
/*  53:    */           {
/*  54: 63 */             ComprobanteSRICreditoTributarioSRI csrict = new ComprobanteSRICreditoTributarioSRI();
/*  55: 64 */             csrict.setIdOrganizacion(entidad.getIdOrganizacion());
/*  56: 65 */             csrict.setIdSucursal(entidad.getIdSucursal());
/*  57: 66 */             csrict.setTipoIdentificacion(entidad);
/*  58: 67 */             csrict.setTipoComprobanteSRI(ticsri.getTipoComprobanteSRI());
/*  59: 68 */             csrict.setCreditoTributarioSRI(ctsri);
/*  60: 69 */             this.servicioComprobanteSRICreditoTributarioSRI.guardar(csrict);
/*  61:    */           }
/*  62:    */         }
/*  63: 72 */         this.servicioTipoIdentificacionComprobanteSRI.guardar(ticsri);
/*  64:    */       }
/*  65: 74 */       this.tipoIdentificacionDao.guardar(entidad);
/*  66:    */     }
/*  67:    */     catch (AS2Exception e)
/*  68:    */     {
/*  69: 76 */       e.printStackTrace();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TipoIdentificacion cargarDetalle(TipoIdentificacion entidad)
/*  74:    */   {
/*  75: 82 */     return this.tipoIdentificacionDao.cargarDetalle(entidad.getId());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void eliminar(TipoIdentificacion entidad)
/*  79:    */   {
/*  80: 92 */     this.tipoIdentificacionDao.eliminar(entidad);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public TipoIdentificacion buscarPorId(Integer id)
/*  84:    */   {
/*  85:103 */     return (TipoIdentificacion)this.tipoIdentificacionDao.buscarPorId(id);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public TipoIdentificacion buscarPorCodigo(String codigo)
/*  89:    */   {
/*  90:113 */     HashMap<String, String> filters = new HashMap();
/*  91:114 */     filters.put("codigo", codigo);
/*  92:115 */     return this.tipoIdentificacionDao.obtenerTipoIdentificacion(filters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public TipoIdentificacion buscarPorCodigo(int idOrganizacion, String codigo)
/*  96:    */   {
/*  97:125 */     return this.tipoIdentificacionDao.buscarPorCodigo(idOrganizacion, codigo);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public TipoIdentificacion buscarPorNombre(String nombre)
/* 101:    */   {
/* 102:135 */     HashMap<String, String> filters = new HashMap();
/* 103:136 */     filters.put("nombre", nombre);
/* 104:137 */     return this.tipoIdentificacionDao.obtenerTipoIdentificacion(filters);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<TipoIdentificacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 108:    */   {
/* 109:142 */     return this.tipoIdentificacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<TipoIdentificacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 113:    */   {
/* 114:153 */     return this.tipoIdentificacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int contarPorCriterio(Map<String, String> filters)
/* 118:    */   {
/* 119:163 */     return this.tipoIdentificacionDao.contarPorCriterio(filters);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public TipoIdentificacion devuelvePrimerTipoIdentificacion()
/* 123:    */   {
/* 124:173 */     return this.tipoIdentificacionDao.devuelvePrimerTipoIdentificacion();
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioTipoIdentificionImpl
 * JD-Core Version:    0.7.0.1
 */