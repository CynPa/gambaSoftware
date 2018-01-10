/*   1:    */ package com.asinfo.as2.financiero.SRI.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.sri.CreditoTributarioSRIDao;
/*   4:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   5:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   6:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   7:    */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCreditoTributario;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioCreditoTributarioImpl
/*  17:    */   implements ServicioCreditoTributario
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private CreditoTributarioSRIDao creditoTributarioSRIDao;
/*  21:    */   
/*  22:    */   public void guardar(CreditoTributarioSRI creditoTributarioSRI)
/*  23:    */   {
/*  24: 48 */     this.creditoTributarioSRIDao.guardar(creditoTributarioSRI);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(CreditoTributarioSRI creditoTributarioSRI)
/*  28:    */   {
/*  29: 60 */     this.creditoTributarioSRIDao.eliminar(creditoTributarioSRI);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CreditoTributarioSRI buscarPorId(int idCreditoTributarioSRI)
/*  33:    */   {
/*  34: 71 */     return (CreditoTributarioSRI)this.creditoTributarioSRIDao.buscarPorId(Integer.valueOf(idCreditoTributarioSRI));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<CreditoTributarioSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 83 */     return this.creditoTributarioSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<CreditoTributarioSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 95 */     return this.creditoTributarioSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int contarPorCriterio(Map<String, String> filters)
/*  48:    */   {
/*  49:106 */     return this.creditoTributarioSRIDao.contarPorCriterio(filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CreditoTributarioSRI cargarDetalle(int idCreditoTributarioSRI)
/*  53:    */   {
/*  54:117 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public CreditoTributarioSRI buscarPorCodigo(String codigo)
/*  58:    */   {
/*  59:122 */     return this.creditoTributarioSRIDao.buscarPorCodigo(codigo);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<CreditoTributarioSRI> autocompletarCreditoTributario(String consulta, boolean indicadorActivos)
/*  63:    */   {
/*  64:127 */     List<CreditoTributarioSRI> lista = new ArrayList();
/*  65:128 */     HashMap<String, String> filters = new HashMap();
/*  66:129 */     filters.put("codigo", consulta.trim());
/*  67:130 */     if (indicadorActivos) {
/*  68:131 */       filters.put("activo", "true");
/*  69:    */     }
/*  70:133 */     lista = this.creditoTributarioSRIDao.obtenerListaCombo("codigo", true, filters);
/*  71:    */     
/*  72:135 */     return lista;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<CreditoTributarioSRI> buscarPorTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI, TipoIdentificacion tipoIdentificacion)
/*  76:    */   {
/*  77:140 */     return this.creditoTributarioSRIDao.buscarPorTipoComprobanteSRI(tipoComprobanteSRI, tipoIdentificacion);
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.impl.ServicioCreditoTributarioImpl
 * JD-Core Version:    0.7.0.1
 */