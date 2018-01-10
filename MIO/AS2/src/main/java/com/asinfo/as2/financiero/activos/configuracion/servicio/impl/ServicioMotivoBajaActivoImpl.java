/*   1:    */ package com.asinfo.as2.financiero.activos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.MotivoBajaActivoDao;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   7:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   8:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioMotivoBajaActivoImpl
/*  16:    */   implements ServicioMotivoBajaActivo
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private MotivoBajaActivoDao motivoBajaActivoDao;
/*  20:    */   
/*  21:    */   public void guardar(MotivoBajaActivo motivoBajaActivo)
/*  22:    */   {
/*  23: 44 */     this.motivoBajaActivoDao.guardar(motivoBajaActivo);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void eliminar(MotivoBajaActivo motivoBajaActivo)
/*  27:    */   {
/*  28: 57 */     this.motivoBajaActivoDao.eliminar(motivoBajaActivo);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public MotivoBajaActivo buscarPorId(int idMotivoBajaActivo)
/*  32:    */   {
/*  33: 69 */     MotivoBajaActivo motivoBajaActivo = (MotivoBajaActivo)this.motivoBajaActivoDao.buscarPorId(Integer.valueOf(idMotivoBajaActivo));
/*  34: 70 */     if (motivoBajaActivo.getCuentaContableMotivoBajaActivo() != null)
/*  35:    */     {
/*  36: 71 */       motivoBajaActivo.getCuentaContableMotivoBajaActivo().getId();
/*  37: 72 */       motivoBajaActivo.getDocumento().getTipoAsiento().getId();
/*  38:    */     }
/*  39: 74 */     motivoBajaActivo.getDocumento().getId();
/*  40: 75 */     return motivoBajaActivo;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<MotivoBajaActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 87 */     return this.motivoBajaActivoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<MotivoBajaActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 99 */     return this.motivoBajaActivoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int contarPorCriterio(Map<String, String> filters)
/*  54:    */   {
/*  55:110 */     return this.motivoBajaActivoDao.contarPorCriterio(filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public MotivoBajaActivo cargarDetalle(int idMotivoBajaActivo)
/*  59:    */   {
/*  60:122 */     return null;
/*  61:    */   }
/*  62:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.impl.ServicioMotivoBajaActivoImpl
 * JD-Core Version:    0.7.0.1
 */