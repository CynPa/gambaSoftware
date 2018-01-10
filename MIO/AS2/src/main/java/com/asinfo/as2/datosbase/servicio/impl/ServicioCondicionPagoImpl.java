/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CondicionPagoDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPagoRemote;
/*   6:    */ import com.asinfo.as2.entities.CondicionPago;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioCondicionPagoImpl
/*  15:    */   implements ServicioCondicionPago, ServicioCondicionPagoRemote
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private CondicionPagoDao condicionPagoDao;
/*  19:    */   
/*  20:    */   public void guardar(CondicionPago condicionPago)
/*  21:    */   {
/*  22: 41 */     this.condicionPagoDao.guardar(condicionPago);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void eliminar(CondicionPago condicionPago)
/*  26:    */   {
/*  27: 51 */     this.condicionPagoDao.eliminar(condicionPago);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CondicionPago buscarPorId(Integer id)
/*  31:    */   {
/*  32: 61 */     return (CondicionPago)this.condicionPagoDao.buscarPorId(id);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<CondicionPago> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 72 */     return this.condicionPagoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<CondicionPago> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 82 */     return this.condicionPagoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int contarPorCriterio(Map<String, String> filters)
/*  46:    */   {
/*  47: 92 */     return this.condicionPagoDao.contarPorCriterio(filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public CondicionPago buscarPorCodigo(String codigo)
/*  51:    */   {
/*  52: 97 */     return this.condicionPagoDao.buscarPorCodigo(codigo);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public CondicionPago buscarCondicionPagoPorDiasPlazo(int diasPlazo, int idOrganizacion)
/*  56:    */     throws ExcepcionAS2
/*  57:    */   {
/*  58:108 */     return this.condicionPagoDao.buscarCondicionPagoPorDiasPlazo(diasPlazo, idOrganizacion);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public CondicionPago devuelveCondicionPagoPredeterminada()
/*  62:    */     throws ExcepcionAS2
/*  63:    */   {
/*  64:118 */     return this.condicionPagoDao.devuelveCondicionPagoPredeterminada();
/*  65:    */   }
/*  66:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioCondicionPagoImpl
 * JD-Core Version:    0.7.0.1
 */