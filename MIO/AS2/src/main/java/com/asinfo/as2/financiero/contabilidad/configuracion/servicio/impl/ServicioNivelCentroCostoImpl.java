/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.NivelCentroCostoDao;
/*   4:    */ import com.asinfo.as2.entities.CentroCosto;
/*   5:    */ import com.asinfo.as2.entities.NivelCentroCosto;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCentroCosto;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioNivelCentroCostoImpl
/*  15:    */   implements ServicioNivelCentroCosto
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private NivelCentroCostoDao nivelCentroCostoDao;
/*  19:    */   @EJB
/*  20:    */   private transient ServicioCentroCosto servicioCentroCosto;
/*  21:    */   
/*  22:    */   public void guardar(NivelCentroCosto nivelCentroCosto)
/*  23:    */   {
/*  24: 46 */     this.nivelCentroCostoDao.guardar(nivelCentroCosto);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(NivelCentroCosto nivelCentroCosto)
/*  28:    */   {
/*  29: 57 */     this.nivelCentroCostoDao.eliminar(nivelCentroCosto);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public NivelCentroCosto buscarPorId(int id)
/*  33:    */   {
/*  34: 67 */     return (NivelCentroCosto)this.nivelCentroCostoDao.buscarPorId(Integer.valueOf(id));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMascara(CentroCosto centroCosto)
/*  38:    */   {
/*  39: 78 */     String mascara = "";
/*  40:    */     NivelCentroCosto nivelCentroCosto;
/*  41:    */     int codigo;
/*  42: 79 */     if (centroCosto.getCentroCostoPadre() != null)
/*  43:    */     {
/*  44: 80 */       CentroCosto centroCostoPadre = this.servicioCentroCosto.cargarDetalle(centroCosto.getCentroCostoPadre().getIdCentroCosto());
/*  45: 81 */       nivelCentroCosto = centroCosto.getNivelCentroCosto();
/*  46: 83 */       if (centroCostoPadre == null)
/*  47:    */       {
/*  48: 84 */         for (int i = 0; i < nivelCentroCosto.getLongitud(); i++) {
/*  49: 85 */           mascara = mascara + "9";
/*  50:    */         }
/*  51: 87 */         mascara = mascara + ".";
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 91 */         mascara = centroCostoPadre.getCodigo();
/*  56:    */         
/*  57: 93 */         codigo = centroCostoPadre.getNivelCentroCosto().getCodigo();
/*  58: 94 */         List<NivelCentroCosto> niveles = obtenerListaCombo("codigo", true, null);
/*  59: 96 */         for (NivelCentroCosto nivelCentroCostoX : niveles) {
/*  60: 97 */           if ((nivelCentroCostoX.getCodigo() > codigo) && 
/*  61: 98 */             (nivelCentroCostoX.getCodigo() <= nivelCentroCosto.getCodigo()))
/*  62:    */           {
/*  63: 99 */             for (int i = 0; i < nivelCentroCostoX.getLongitud(); i++) {
/*  64:100 */               mascara = mascara + "9";
/*  65:    */             }
/*  66:102 */             mascara = mascara + ".";
/*  67:    */           }
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71:109 */     return mascara;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<NivelCentroCosto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  75:    */   {
/*  76:120 */     return this.nivelCentroCostoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<NivelCentroCosto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  80:    */   {
/*  81:131 */     return this.nivelCentroCostoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int contarPorCriterio(Map<String, String> filters)
/*  85:    */   {
/*  86:141 */     return this.nivelCentroCostoDao.contarPorCriterio(filters);
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioNivelCentroCostoImpl
 * JD-Core Version:    0.7.0.1
 */