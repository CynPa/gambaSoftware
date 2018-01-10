/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoPrestamoDao;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*   6:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   7:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo;
/*   8:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   9:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.SessionContext;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioTipoPrestamoImpl
/*  18:    */   extends AbstractServicioAS2
/*  19:    */   implements ServicioTipoPrestamo
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @EJB
/*  23:    */   private TipoPrestamoDao tipoPrestamoDao;
/*  24:    */   
/*  25:    */   public void guardar(TipoPrestamo tipoPrestamo)
/*  26:    */     throws ExcepcionAS2Nomina, AS2Exception
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 54 */       validar(tipoPrestamo);
/*  31: 55 */       if (!tipoPrestamo.isIndicadorContabilizar()) {
/*  32: 56 */         tipoPrestamo.setCuentaContable(null);
/*  33:    */       }
/*  34: 58 */       this.tipoPrestamoDao.guardar(tipoPrestamo);
/*  35:    */     }
/*  36:    */     catch (AS2Exception e)
/*  37:    */     {
/*  38: 61 */       this.context.setRollbackOnly();
/*  39: 62 */       throw e;
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 64 */       this.context.setRollbackOnly();
/*  44: 65 */       throw new ExcepcionAS2Nomina(e);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void validar(TipoPrestamo tipoPrestamo)
/*  49:    */     throws AS2Exception
/*  50:    */   {
/*  51: 70 */     if (!tipoPrestamo.getRubro().getFormula().equals("i")) {
/*  52: 71 */       throw new AS2Exception("msg_info_necesita_rubro_contrato", new String[] { "" });
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void eliminar(TipoPrestamo tipoPrestamo)
/*  57:    */   {
/*  58: 84 */     this.tipoPrestamoDao.eliminar(tipoPrestamo);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public TipoPrestamo buscarPorId(int idTipoPrestamo)
/*  62:    */   {
/*  63: 97 */     return (TipoPrestamo)this.tipoPrestamoDao.buscarPorId(Integer.valueOf(idTipoPrestamo));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<TipoPrestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  67:    */   {
/*  68:108 */     return this.tipoPrestamoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<TipoPrestamo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73:119 */     return this.tipoPrestamoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int contarPorCriterio(Map<String, String> filters)
/*  77:    */   {
/*  78:130 */     return this.tipoPrestamoDao.contarPorCriterio(filters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public TipoPrestamo cargarDetalle(int idTipoPrestamo)
/*  82:    */   {
/*  83:141 */     return this.tipoPrestamoDao.cargarDetalle(idTipoPrestamo);
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioTipoPrestamoImpl
 * JD-Core Version:    0.7.0.1
 */