/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.entities.Chofer;
/*   6:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioChofer;
/*   9:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  10:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  11:    */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.SessionContext;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.ejb.TransactionAttribute;
/*  20:    */ import javax.ejb.TransactionAttributeType;
/*  21:    */ import javax.ejb.TransactionManagement;
/*  22:    */ import javax.ejb.TransactionManagementType;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  27:    */ public class ServicioChoferImpl
/*  28:    */   extends AbstractServicioAS2
/*  29:    */   implements ServicioChofer
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -8181844210742442787L;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  34:    */   @EJB
/*  35:    */   private transient GenericoDao<Chofer> choferDao;
/*  36:    */   
/*  37:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  38:    */   public void guardar(Chofer chofer)
/*  39:    */     throws ExcepcionAS2Identification, AS2Exception
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 57 */       validar(chofer);
/*  44: 58 */       this.choferDao.guardar(chofer);
/*  45:    */     }
/*  46:    */     catch (ExcepcionAS2Identification e)
/*  47:    */     {
/*  48: 60 */       this.context.setRollbackOnly();
/*  49: 61 */       e.printStackTrace();
/*  50: 62 */       throw e;
/*  51:    */     }
/*  52:    */     catch (AS2Exception e)
/*  53:    */     {
/*  54: 64 */       this.context.setRollbackOnly();
/*  55: 65 */       e.printStackTrace();
/*  56: 66 */       throw e;
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 68 */       this.context.setRollbackOnly();
/*  61: 69 */       LOG.error(e);
/*  62: 70 */       e.printStackTrace();
/*  63: 71 */       throw new AS2Exception(e.getMessage());
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void validar(Chofer chofer)
/*  68:    */     throws AS2Exception, ExcepcionAS2Identification
/*  69:    */   {
/*  70: 77 */     Map<String, String> filtros = new HashMap();
/*  71: 78 */     filtros.put("idOrganizacion", chofer.getIdOrganizacion() + "");
/*  72: 79 */     filtros.put("codigo", "=C");
/*  73: 80 */     TipoIdentificacion tipoIdentificacion = null;
/*  74: 81 */     List<TipoIdentificacion> listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, filtros);
/*  75: 82 */     if (listaTipoIdentificacion.size() > 0) {
/*  76: 83 */       tipoIdentificacion = (TipoIdentificacion)listaTipoIdentificacion.get(0);
/*  77:    */     } else {
/*  78: 85 */       throw new AS2Exception("msg_error_no_existe_tipo_identificacion_codigo", new String[] { "C" });
/*  79:    */     }
/*  80: 87 */     ValidarIdentificacion.validarIdentificacion(tipoIdentificacion.isIndicadorValidarIdentificacion(), chofer.getLicencia());
/*  81:    */     
/*  82:    */ 
/*  83: 90 */     Map<String, String> filtrosLicencia = new HashMap();
/*  84: 91 */     filtros.put("idOrganizacion", chofer.getIdOrganizacion() + "");
/*  85: 92 */     filtrosLicencia.put("licencia", chofer.getLicencia());
/*  86: 93 */     filtrosLicencia.put("idChofer", "!=" + chofer.getId());
/*  87: 94 */     List<Chofer> listaChoferesLicencia = obtenerListaCombo("licencia", true, filtrosLicencia);
/*  88: 95 */     if (listaChoferesLicencia.size() > 0) {
/*  89: 96 */       throw new AS2Exception("msg_error_licencia_repetida", new String[] { chofer.getLicencia() });
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void eliminar(Chofer chofer)
/*  94:    */   {
/*  95:102 */     this.choferDao.eliminar(chofer);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Chofer cargarDetalle(int idChofer)
/*  99:    */   {
/* 100:107 */     List<String> listaCampos = new ArrayList();
/* 101:108 */     listaCampos.add("transportista");
/* 102:109 */     return (Chofer)this.choferDao.cargarDetalle(Chofer.class, idChofer, listaCampos);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Chofer buscarPorId(Integer idChofer)
/* 106:    */   {
/* 107:114 */     return (Chofer)this.choferDao.buscarPorId(Chofer.class, idChofer);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Chofer> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 111:    */   {
/* 112:119 */     List<String> listaCampos = new ArrayList();
/* 113:120 */     listaCampos.add("transportista");
/* 114:121 */     return this.choferDao.obtenerListaPorPagina(Chofer.class, startIndex, pageSize, sortField, sortOrder, filters, listaCampos);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int contarPorCriterio(Map<String, String> filters)
/* 118:    */   {
/* 119:126 */     return this.choferDao.contarPorCriterio(Chofer.class, filters);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public List<Chofer> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 123:    */   {
/* 124:131 */     return this.choferDao.obtenerListaCombo(Chofer.class, sortField, sortOrder, filters);
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioChoferImpl
 * JD-Core Version:    0.7.0.1
 */