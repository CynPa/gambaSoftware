/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   4:    */ import com.asinfo.as2.dao.PagoRolEmpleadoDao;
/*   5:    */ import com.asinfo.as2.dao.PagoRolEmpleadoRubroDao;
/*   6:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   7:    */ import com.asinfo.as2.entities.PagoRol;
/*   8:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   9:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleadoRubro;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioPagoRolEmpleadoRubroImpl
/*  19:    */   implements ServicioPagoRolEmpleadoRubro
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*  23:    */   @EJB
/*  24:    */   PagoRolEmpleadoDao pagoRolEmpleadoDao;
/*  25:    */   @EJB
/*  26:    */   PagoRolDao pagoRolDao;
/*  27:    */   
/*  28:    */   public void guardar(List<PagoRolEmpleadoRubro> listaPagoRolEmpleadoRubro, PagoRol pagoRol)
/*  29:    */   {
/*  30: 52 */     Map<Integer, PagoRolEmpleado> mPRE = new HashMap();
/*  31: 53 */     for (PagoRolEmpleadoRubro prer : listaPagoRolEmpleadoRubro)
/*  32:    */     {
/*  33: 54 */       if (!mPRE.containsKey(Integer.valueOf(prer.getPagoRolEmpleado().getId()))) {
/*  34: 55 */         mPRE.put(Integer.valueOf(prer.getPagoRolEmpleado().getId()), prer.getPagoRolEmpleado());
/*  35:    */       }
/*  36: 57 */       this.pagoRolEmpleadoRubroDao.guardar(prer);
/*  37:    */     }
/*  38: 59 */     this.pagoRolEmpleadoRubroDao.flush();
/*  39: 61 */     for (PagoRolEmpleado pre : mPRE.values()) {
/*  40: 62 */       this.pagoRolEmpleadoDao.actualizarValorAPagar(pre);
/*  41:    */     }
/*  42: 65 */     if (null != pagoRol)
/*  43:    */     {
/*  44: 66 */       pagoRol.setIndicadorReprocesar(Boolean.valueOf(true));
/*  45: 67 */       this.pagoRolDao.actualizar(pagoRol);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<PagoRolEmpleadoRubro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 80 */     return this.pagoRolEmpleadoRubroDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int contarPorCriterio(Map<String, String> filters)
/*  55:    */   {
/*  56: 90 */     return this.pagoRolEmpleadoRubroDao.contarPorCriterio(filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<PagoRolEmpleadoRubro> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  60:    */   {
/*  61: 98 */     return this.pagoRolEmpleadoRubroDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubroFiniquito(HistoricoEmpleado historicoEmpleado)
/*  65:    */   {
/*  66:104 */     return this.pagoRolEmpleadoRubroDao.getListaPagoRolEmpleadoRubroFiniquito(historicoEmpleado);
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoRolEmpleadoRubroImpl
 * JD-Core Version:    0.7.0.1
 */