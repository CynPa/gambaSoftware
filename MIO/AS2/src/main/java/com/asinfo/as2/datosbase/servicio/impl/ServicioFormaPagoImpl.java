/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.FormaPagoDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoRemoto;
/*   6:    */ import com.asinfo.as2.entities.FormaPago;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioFormaPagoImpl
/*  17:    */   implements ServicioFormaPago, ServicioFormaPagoRemoto
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private FormaPagoDao formaPagoDao;
/*  21:    */   
/*  22:    */   public void guardar(FormaPago entidad)
/*  23:    */   {
/*  24: 44 */     this.formaPagoDao.guardar(entidad);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(FormaPago entidad)
/*  28:    */   {
/*  29: 54 */     this.formaPagoDao.eliminar(entidad);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<FormaPago> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 64 */     return this.formaPagoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int contarPorCriterio(Map<String, String> filters)
/*  38:    */   {
/*  39: 74 */     return this.formaPagoDao.contarPorCriterio(filters);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public FormaPago buscarPorId(Integer id)
/*  43:    */   {
/*  44: 84 */     return (FormaPago)this.formaPagoDao.buscarPorId(id);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<FormaPago> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 95 */     return this.formaPagoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public FormaPago buscarPorCodigo(String codigo)
/*  54:    */     throws ExcepcionAS2
/*  55:    */   {
/*  56:106 */     Map<String, String> filters = new HashMap();
/*  57:107 */     filters.put("codigo", codigo);
/*  58:    */     try
/*  59:    */     {
/*  60:110 */       List<FormaPago> listaFormaPago = obtenerListaCombo("nombre", true, filters);
/*  61:111 */       return (FormaPago)listaFormaPago.get(0);
/*  62:    */     }
/*  63:    */     catch (Exception e)
/*  64:    */     {
/*  65:114 */       throw new ExcepcionAS2("msg_error_forma_pago_no_encontrado", " " + (String)filters.get("nombre"));
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public FormaPago cargarDetalle(int idFormaPago)
/*  70:    */   {
/*  71:126 */     return this.formaPagoDao.cargarDetalle(idFormaPago);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public FormaPago devuelveFormaPagoPredeterminada()
/*  75:    */   {
/*  76:136 */     return this.formaPagoDao.devuelveFormaPagoPredeterminada();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public FormaPago buscarFormaPago(Map<String, String> filters)
/*  80:    */     throws ExcepcionAS2
/*  81:    */   {
/*  82:146 */     return this.formaPagoDao.buscarFormaPago(filters);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public FormaPago formaPagoPorTipoRetencionYPorcentaje(BigDecimal porcentajeRetencion, boolean indicadorRetencionFuente, boolean indicadorRetencionIVA, int idOrganizacion)
/*  86:    */   {
/*  87:153 */     return this.formaPagoDao.formaPagoPorTipoRetencionYPorcentaje(porcentajeRetencion, indicadorRetencionFuente, indicadorRetencionIVA, idOrganizacion);
/*  88:    */   }
/*  89:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioFormaPagoImpl
 * JD-Core Version:    0.7.0.1
 */