/*   1:    */ package com.asinfo.as2.financiero.pagos.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.EstadoChequeDao;
/*   4:    */ import com.asinfo.as2.entities.EstadoCheque;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioEstadoCheque;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioEstadoChequeImpl
/*  14:    */   implements ServicioEstadoCheque
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private EstadoChequeDao estadoChequeDao;
/*  18:    */   
/*  19:    */   public void guardar(EstadoCheque estadoCheque)
/*  20:    */     throws ExcepcionAS2
/*  21:    */   {
/*  22: 30 */     if (((estadoCheque.isEstadoInicial()) || (estadoCheque.isEstadoFinal())) && 
/*  23: 31 */       (this.estadoChequeDao.existeEstadoInicialFinal(estadoCheque))) {
/*  24: 32 */       throw new ExcepcionAS2("msg_error_existe_estado_inicial_final");
/*  25:    */     }
/*  26: 35 */     this.estadoChequeDao.guardar(estadoCheque);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void eliminar(EstadoCheque estadoCheque)
/*  30:    */   {
/*  31: 45 */     this.estadoChequeDao.eliminar(estadoCheque);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EstadoCheque buscarPorId(int idEstadoCheque)
/*  35:    */   {
/*  36: 56 */     return (EstadoCheque)this.estadoChequeDao.buscarPorId(Integer.valueOf(idEstadoCheque));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<EstadoCheque> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 66 */     return this.estadoChequeDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<EstadoCheque> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  45:    */   {
/*  46: 76 */     return this.estadoChequeDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int contarPorCriterio(Map<String, String> filters)
/*  50:    */   {
/*  51: 86 */     return this.estadoChequeDao.contarPorCriterio(filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public EstadoCheque cargarDetalle(int idEstadoCheque)
/*  55:    */   {
/*  56: 96 */     return (EstadoCheque)this.estadoChequeDao.cargarDetalle(idEstadoCheque);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public EstadoCheque getEstadoFinal(int idOrganizacion)
/*  60:    */   {
/*  61:101 */     return this.estadoChequeDao.getEstadoFinal(idOrganizacion);
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.configuracion.servicio.impl.ServicioEstadoChequeImpl
 * JD-Core Version:    0.7.0.1
 */