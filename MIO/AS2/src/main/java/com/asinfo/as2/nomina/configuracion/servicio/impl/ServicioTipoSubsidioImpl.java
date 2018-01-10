/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoSubsidioDao;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import com.asinfo.as2.entities.TipoSubsidio;
/*   6:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   7:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoSubsidio;
/*   8:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   9:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.SessionContext;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioTipoSubsidioImpl
/*  18:    */   extends AbstractServicioAS2
/*  19:    */   implements ServicioTipoSubsidio
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @EJB
/*  23:    */   private transient TipoSubsidioDao tipoSubsidioDao;
/*  24:    */   
/*  25:    */   public void guardar(TipoSubsidio tipoSubsidio)
/*  26:    */     throws ExcepcionAS2Nomina, AS2Exception
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 52 */       validar(tipoSubsidio);
/*  31: 53 */       this.tipoSubsidioDao.guardar(tipoSubsidio);
/*  32:    */     }
/*  33:    */     catch (AS2Exception e)
/*  34:    */     {
/*  35: 55 */       this.context.setRollbackOnly();
/*  36: 56 */       throw e;
/*  37:    */     }
/*  38:    */     catch (Exception e)
/*  39:    */     {
/*  40: 58 */       this.context.setRollbackOnly();
/*  41: 59 */       throw new ExcepcionAS2Nomina(e);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void validar(TipoSubsidio tipoSubsidio)
/*  46:    */     throws AS2Exception
/*  47:    */   {
/*  48: 64 */     if (!tipoSubsidio.getRubro().getFormula().equals("k")) {
/*  49: 65 */       throw new AS2Exception("msg_info_necesita_rubro_subsidio", new String[] { "" });
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void eliminar(TipoSubsidio tipoSubsidio)
/*  54:    */   {
/*  55: 76 */     this.tipoSubsidioDao.eliminar(tipoSubsidio);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public TipoSubsidio buscarPorId(int idTipoSubsidio)
/*  59:    */   {
/*  60: 86 */     return (TipoSubsidio)this.tipoSubsidioDao.buscarPorId(Integer.valueOf(idTipoSubsidio));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<TipoSubsidio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  64:    */   {
/*  65: 98 */     return this.tipoSubsidioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<TipoSubsidio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  69:    */   {
/*  70:108 */     return this.tipoSubsidioDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TipoSubsidio cargarDetalle(int idTipoSubsidio)
/*  74:    */   {
/*  75:118 */     return this.tipoSubsidioDao.cargarDetalle(idTipoSubsidio);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int contarPorCriterio(Map<String, String> filters)
/*  79:    */   {
/*  80:128 */     return this.tipoSubsidioDao.contarPorCriterio(filters);
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioTipoSubsidioImpl
 * JD-Core Version:    0.7.0.1
 */