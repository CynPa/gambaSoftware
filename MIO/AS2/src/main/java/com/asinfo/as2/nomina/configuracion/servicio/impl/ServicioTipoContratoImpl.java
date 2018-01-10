/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.TipoContratoDao;
/*   4:    */ import com.asinfo.as2.entities.TipoContrato;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioTipoContratoImpl
/*  13:    */   implements ServicioTipoContrato
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private transient TipoContratoDao tipoContratoDao;
/*  17:    */   
/*  18:    */   public void guardar(TipoContrato tipoContrato)
/*  19:    */   {
/*  20: 42 */     this.tipoContratoDao.guardar(tipoContrato);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(TipoContrato entidad)
/*  24:    */   {
/*  25: 52 */     this.tipoContratoDao.eliminar(entidad);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TipoContrato buscarPorId(int idTipoContrato)
/*  29:    */   {
/*  30: 62 */     return (TipoContrato)this.tipoContratoDao.buscarPorId(Integer.valueOf(idTipoContrato));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<TipoContrato> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 74 */     return this.tipoContratoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<TipoContrato> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 84 */     return this.tipoContratoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 94 */     return this.tipoContratoDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public TipoContrato cargarDetalle(int idEntidad)
/*  49:    */   {
/*  50:104 */     return this.tipoContratoDao.cargarDetalle(idEntidad);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public TipoContrato buscarPorNombre(String nombre)
/*  54:    */   {
/*  55:114 */     return this.tipoContratoDao.buscarPorNombre(nombre);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioTipoContratoImpl
 * JD-Core Version:    0.7.0.1
 */