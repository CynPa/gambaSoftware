/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CentroCostoDao;
/*   4:    */ import com.asinfo.as2.dao.NivelCentroCostoDao;
/*   5:    */ import com.asinfo.as2.entities.CentroCosto;
/*   6:    */ import com.asinfo.as2.entities.NivelCentroCosto;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioCentroCostoImpl
/*  18:    */   implements ServicioCentroCosto
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private CentroCostoDao centroCostoDao;
/*  22:    */   @EJB
/*  23:    */   private NivelCentroCostoDao nivelCentroCostoDao;
/*  24:    */   
/*  25:    */   public void guardar(CentroCosto centroCosto)
/*  26:    */     throws ExcepcionAS2Financiero
/*  27:    */   {
/*  28: 51 */     if ((centroCosto.getNivelCentroCosto().getCodigo() != 1) && (centroCosto.getCentroCostoPadre() == null)) {
/*  29: 52 */       throw new ExcepcionAS2Financiero("msg_error_centro_costo_padre");
/*  30:    */     }
/*  31: 54 */     this.centroCostoDao.guardar(centroCosto);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void eliminar(CentroCosto centroCosto)
/*  35:    */     throws ExcepcionAS2Financiero
/*  36:    */   {
/*  37: 64 */     this.centroCostoDao.eliminar(centroCosto);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public CentroCosto buscarPorId(int id)
/*  41:    */   {
/*  42: 74 */     return (CentroCosto)this.centroCostoDao.buscarPorId(Integer.valueOf(id));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public CentroCosto buscarPorCodigo(String codigoCentroCosto)
/*  46:    */     throws ExcepcionAS2
/*  47:    */   {
/*  48: 84 */     return this.centroCostoDao.buscarPorCodigo(codigoCentroCosto);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<CentroCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 95 */     return this.centroCostoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<CentroCosto> ObtenerListaComboPorBodega(int idBodega)
/*  57:    */   {
/*  58:105 */     return this.centroCostoDao.ObtenerListaComboPorBodega(idBodega);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<CentroCosto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */     throws ExcepcionAS2Financiero
/*  63:    */   {
/*  64:117 */     return this.centroCostoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int contarPorCriterio(Map<String, String> filters)
/*  68:    */   {
/*  69:127 */     return this.centroCostoDao.contarPorCriterio(filters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<CentroCosto> buscarPorNivel(int idNivel)
/*  73:    */   {
/*  74:137 */     NivelCentroCosto nivelCentroCosto = (NivelCentroCosto)this.nivelCentroCostoDao.buscarPorId(Integer.valueOf(idNivel));
/*  75:138 */     if (nivelCentroCosto == null) {
/*  76:139 */       return new ArrayList();
/*  77:    */     }
/*  78:141 */     return this.centroCostoDao.buscarPorNivel(nivelCentroCosto.getCodigo() - 1);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public CentroCosto cargarDetalle(int idCentroCosto)
/*  82:    */   {
/*  83:151 */     return this.centroCostoDao.cargarDetalle(idCentroCosto);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<CentroCosto> buscarPorCodigoNivelCentroCosto(int codigo)
/*  87:    */     throws ExcepcionAS2
/*  88:    */   {
/*  89:161 */     return this.centroCostoDao.buscarPorCodigoNivelCentroCosto(codigo);
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCentroCostoImpl
 * JD-Core Version:    0.7.0.1
 */