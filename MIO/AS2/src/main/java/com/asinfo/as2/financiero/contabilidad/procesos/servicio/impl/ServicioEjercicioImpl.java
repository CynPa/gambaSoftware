/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EjercicioDao;
/*   4:    */ import com.asinfo.as2.dao.PeriodoDao;
/*   5:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   6:    */ import com.asinfo.as2.entities.Ejercicio;
/*   7:    */ import com.asinfo.as2.entities.Periodo;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioEjercicioImpl
/*  17:    */   implements ServicioEjercicio
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private EjercicioDao ejercicioDao;
/*  21:    */   @EJB
/*  22:    */   private PeriodoDao periodoDao;
/*  23:    */   
/*  24:    */   public void guardar(Ejercicio ejercicio)
/*  25:    */   {
/*  26: 47 */     for (Periodo periodo : ejercicio.getPeriodos()) {
/*  27: 48 */       this.periodoDao.guardar(periodo);
/*  28:    */     }
/*  29: 50 */     this.ejercicioDao.guardar(ejercicio);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void eliminar(Ejercicio ejercicio)
/*  33:    */   {
/*  34: 63 */     this.ejercicioDao.eliminar(ejercicio);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Ejercicio buscarPorId(Integer id)
/*  38:    */   {
/*  39: 75 */     return (Ejercicio)this.ejercicioDao.buscarPorId(id);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<DetalleAsiento> cerrarEjercicio(int idEjercicio)
/*  43:    */     throws ExcepcionAS2Financiero
/*  44:    */   {
/*  45: 88 */     return this.ejercicioDao.cerrarEjercicio(idEjercicio);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Ejercicio recuperaEjercicio(int idEjercicio)
/*  49:    */   {
/*  50: 93 */     return this.ejercicioDao.recuperaEjercicio(idEjercicio);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<Ejercicio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55:107 */     return this.ejercicioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Ejercicio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60:121 */     return this.ejercicioDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int contarPorCriterio(Map<String, String> filters)
/*  64:    */   {
/*  65:129 */     return this.ejercicioDao.contarPorCriterio(filters);
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioEjercicioImpl
 * JD-Core Version:    0.7.0.1
 */