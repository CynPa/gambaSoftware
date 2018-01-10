/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.RubroDao;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*   6:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioRubroImpl
/*  14:    */   implements ServicioRubro
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private RubroDao rubroDao;
/*  18:    */   
/*  19:    */   public void guardar(Rubro rubro)
/*  20:    */     throws ExcepcionAS2Nomina
/*  21:    */   {
/*  22: 46 */     if ((rubro.getRubroPadre() != null) && 
/*  23: 47 */       (rubro.getRubroPadre().getId() == rubro.getId())) {
/*  24: 48 */       throw new ExcepcionAS2Nomina("msg_error_rubro_padre");
/*  25:    */     }
/*  26: 51 */     this.rubroDao.guardar(rubro);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void eliminar(Rubro rubro)
/*  30:    */   {
/*  31: 64 */     this.rubroDao.eliminar(rubro);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Rubro buscarPorId(int idRubro)
/*  35:    */   {
/*  36: 77 */     return (Rubro)this.rubroDao.buscarPorId(Integer.valueOf(idRubro));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<Rubro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 88 */     return this.rubroDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<Rubro> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  45:    */   {
/*  46:100 */     return this.rubroDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int contarPorCriterio(Map<String, String> filters)
/*  50:    */   {
/*  51:112 */     return this.rubroDao.contarPorCriterio(filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Rubro cargarDetalle(int idRubro)
/*  55:    */   {
/*  56:124 */     return this.rubroDao.cargarDetalle(idRubro);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<Rubro> getLisRubro()
/*  60:    */   {
/*  61:136 */     return this.rubroDao.getLisRubro();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<Rubro> getListaRubros(int idCRubro)
/*  65:    */   {
/*  66:141 */     return this.rubroDao.getListaRubros(idCRubro);
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioRubroImpl
 * JD-Core Version:    0.7.0.1
 */