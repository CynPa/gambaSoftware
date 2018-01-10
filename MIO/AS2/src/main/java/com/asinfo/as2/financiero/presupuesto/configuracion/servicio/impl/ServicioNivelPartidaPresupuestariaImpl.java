/*   1:    */ package com.asinfo.as2.financiero.presupuesto.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.presupuesto.NivelPartidaPresupuestariaDao;
/*   4:    */ import com.asinfo.as2.dao.presupuesto.PartidaPresupuestariaDao;
/*   5:    */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*   6:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   7:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioNivelPartidaPresupuestaria;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioNivelPartidaPresupuestariaImpl
/*  15:    */   implements ServicioNivelPartidaPresupuestaria
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private NivelPartidaPresupuestariaDao nivelPartidaPresupuestariaDao;
/*  19:    */   @EJB
/*  20:    */   private PartidaPresupuestariaDao partidaPresupuestariaDao;
/*  21:    */   
/*  22:    */   public void guardar(NivelPartidaPresupuestaria nivelPartidaPresupuestaria)
/*  23:    */   {
/*  24: 44 */     this.nivelPartidaPresupuestariaDao.guardar(nivelPartidaPresupuestaria);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(NivelPartidaPresupuestaria nivelPartidaPresupuestaria)
/*  28:    */   {
/*  29: 53 */     this.nivelPartidaPresupuestariaDao.eliminar(nivelPartidaPresupuestaria);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public NivelPartidaPresupuestaria buscarPorId(int idPartidaPresupuestaria)
/*  33:    */   {
/*  34: 62 */     return (NivelPartidaPresupuestaria)this.nivelPartidaPresupuestariaDao.buscarPorId(Integer.valueOf(idPartidaPresupuestaria));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<NivelPartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 72 */     return this.nivelPartidaPresupuestariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<NivelPartidaPresupuestaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 80 */     return this.nivelPartidaPresupuestariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int contarPorCriterio(Map<String, String> filters)
/*  48:    */   {
/*  49: 88 */     return this.nivelPartidaPresupuestariaDao.contarPorCriterio(filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public NivelPartidaPresupuestaria cargarDetalle(int idPartidaPresupuestaria)
/*  53:    */   {
/*  54: 96 */     return this.nivelPartidaPresupuestariaDao.cargarDetalle(idPartidaPresupuestaria);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getMascara(int idPartidaPresupuestariaPadre, int idNivel)
/*  58:    */   {
/*  59:106 */     int codigo = 0;
/*  60:107 */     PartidaPresupuestaria partidaPresupuestariaPadre = (PartidaPresupuestaria)this.partidaPresupuestariaDao.buscarPorId(Integer.valueOf(idPartidaPresupuestariaPadre));
/*  61:108 */     if (partidaPresupuestariaPadre == null)
/*  62:    */     {
/*  63:109 */       partidaPresupuestariaPadre = new PartidaPresupuestaria();
/*  64:110 */       partidaPresupuestariaPadre.setCodigo("");
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68:112 */       codigo = partidaPresupuestariaPadre.getNivelPartidaPresupuestaria().getCodigo();
/*  69:    */     }
/*  70:114 */     NivelPartidaPresupuestaria nivelPartidaPresupuestaria = buscarPorId(idNivel);
/*  71:    */     
/*  72:116 */     List<NivelPartidaPresupuestaria> niveles = obtenerListaCombo("codigo", true, null);
/*  73:    */     
/*  74:118 */     String mascara = partidaPresupuestariaPadre.getCodigo();
/*  75:120 */     for (NivelPartidaPresupuestaria nivelPartidaPresupuestariaX : niveles) {
/*  76:121 */       if ((nivelPartidaPresupuestariaX.getCodigo() > codigo) && 
/*  77:122 */         (nivelPartidaPresupuestariaX.getCodigo() <= nivelPartidaPresupuestaria.getCodigo()))
/*  78:    */       {
/*  79:123 */         for (int i = 0; i < nivelPartidaPresupuestariaX.getLongitud(); i++) {
/*  80:124 */           mascara = mascara + "9";
/*  81:    */         }
/*  82:126 */         mascara = mascara + ".";
/*  83:    */       }
/*  84:    */     }
/*  85:130 */     return mascara;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.configuracion.servicio.impl.ServicioNivelPartidaPresupuestariaImpl
 * JD-Core Version:    0.7.0.1
 */