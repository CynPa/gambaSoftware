/*   1:    */ package com.asinfo.as2.financiero.presupuesto.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.presupuesto.PartidaPresupuestariaDao;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*   6:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   7:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioPartidaPresupuestariaImpl
/*  18:    */   implements ServicioPartidaPresupuestaria
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private PartidaPresupuestariaDao partidaPresupuestariaDao;
/*  22:    */   @EJB
/*  23:    */   private ServicioCuentaContable servicioCuentaContable;
/*  24:    */   
/*  25:    */   public void guardar(PartidaPresupuestaria partidaPresupuestaria)
/*  26:    */     throws ExcepcionAS2Financiero
/*  27:    */   {
/*  28: 52 */     List<CuentaContable> lista = partidaPresupuestaria.getListaCuentaContable();
/*  29: 54 */     if (partidaPresupuestaria.getNivelPartidaPresupuestaria().getCodigo() == 1) {
/*  30: 55 */       partidaPresupuestaria.setPartidaPresupuestariaPadre(null);
/*  31:    */     }
/*  32: 57 */     this.partidaPresupuestariaDao.guardar(partidaPresupuestaria);
/*  33: 59 */     for (CuentaContable cuentaContable : lista)
/*  34:    */     {
/*  35: 60 */       if (cuentaContable.isEliminado())
/*  36:    */       {
/*  37: 61 */         cuentaContable.setPartidaPresupuestaria(null);
/*  38: 62 */         cuentaContable.setEliminado(false);
/*  39:    */       }
/*  40: 64 */       this.servicioCuentaContable.guardar(cuentaContable);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void eliminar(PartidaPresupuestaria partidaPresupuestaria)
/*  45:    */   {
/*  46: 76 */     this.partidaPresupuestariaDao.eliminar(partidaPresupuestaria);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public PartidaPresupuestaria buscarPorId(int idPartidaPresupuestaria)
/*  50:    */   {
/*  51: 87 */     return (PartidaPresupuestaria)this.partidaPresupuestariaDao.buscarPorId(Integer.valueOf(idPartidaPresupuestaria));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<PartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56:100 */     return this.partidaPresupuestariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<PartidaPresupuestaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  60:    */   {
/*  61:111 */     return this.partidaPresupuestariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int contarPorCriterio(Map<String, String> filters)
/*  65:    */   {
/*  66:121 */     return this.partidaPresupuestariaDao.contarPorCriterio(filters);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public PartidaPresupuestaria cargarDetalle(int idPartidaPresupuestaria)
/*  70:    */   {
/*  71:131 */     return this.partidaPresupuestariaDao.cargarDetalle(idPartidaPresupuestaria);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<PartidaPresupuestaria> buscarPorGrupoNivelPartidaPresupuestaria(GrupoCuenta grupoPartidaPresupuestaria, int codigo, int idOrganizacion)
/*  75:    */   {
/*  76:141 */     return this.partidaPresupuestariaDao.buscarPorGrupoNivelPartidaPresupuestaria(grupoPartidaPresupuestaria, codigo, idOrganizacion);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<Object[]> getReportePartidaPresupuestaria(int idPartidaPresupuestaria, int idOrganizacion)
/*  80:    */   {
/*  81:146 */     return this.partidaPresupuestariaDao.getReportePartidaPresupuestaria(idPartidaPresupuestaria, idOrganizacion);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<PartidaPresupuestaria> obtenerPartidaPresupuestariaPorUsuario(int idOrganizacion, int idUsuario)
/*  85:    */   {
/*  86:151 */     return this.partidaPresupuestariaDao.getPartidaPresupuestariaPorUsuario(idOrganizacion, idUsuario);
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.configuracion.servicio.impl.ServicioPartidaPresupuestariaImpl
 * JD-Core Version:    0.7.0.1
 */