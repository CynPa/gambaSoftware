/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.UnidadConversionDao;
/*   4:    */ import com.asinfo.as2.dao.UnidadDao;
/*   5:    */ import com.asinfo.as2.entities.Unidad;
/*   6:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   7:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadRemoto;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioUnidadImpl
/*  19:    */   implements ServicioUnidad, ServicioUnidadRemoto
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private UnidadDao unidadDao;
/*  23:    */   @EJB
/*  24:    */   private UnidadConversionDao unidadConversionDao;
/*  25:    */   @EJB
/*  26:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  27:    */   
/*  28:    */   public void guardar(Unidad unidad)
/*  29:    */   {
/*  30: 50 */     this.unidadDao.guardar(unidad);
/*  31: 52 */     for (UnidadConversion unidadConversion : unidad.getListaUnidadConversion())
/*  32:    */     {
/*  33: 53 */       this.servicioUnidadConversion.actualizaUnidadConversion(unidadConversion);
/*  34: 54 */       this.unidadConversionDao.guardar(unidadConversion);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void eliminar(Unidad unidad)
/*  39:    */   {
/*  40: 65 */     this.unidadDao.eliminar(unidad);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Unidad buscarPorId(int id)
/*  44:    */   {
/*  45: 75 */     return (Unidad)this.unidadDao.buscarPorId(Integer.valueOf(id));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<Unidad> listaUnidadCombo(String consulta, TipoUnidadMedida tipoUnidadMedida, int idUnidad)
/*  49:    */   {
/*  50: 86 */     return this.unidadDao.listaUnidadCombo(consulta, tipoUnidadMedida, idUnidad);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Unidad cargarDetalle(int idUnidad)
/*  54:    */   {
/*  55: 96 */     return this.unidadDao.cargarDetalle(idUnidad);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Unidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60:106 */     return this.unidadDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<Unidad> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  64:    */   {
/*  65:116 */     return this.unidadDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int contarPorCriterio(Map<String, String> filters)
/*  69:    */   {
/*  70:126 */     return this.unidadDao.contarPorCriterio(filters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Unidad> obtenerListaUnidadPorUnidadOrigen(int idUnidadOrigen)
/*  74:    */   {
/*  75:131 */     return this.unidadDao.obtenerListaUnidadPorUnidadOrigen(idUnidadOrigen);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Unidad buscarUnidad(Map<String, String> filters)
/*  79:    */     throws ExcepcionAS2
/*  80:    */   {
/*  81:141 */     return this.unidadDao.buscarUnidad(filters);
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioUnidadImpl
 * JD-Core Version:    0.7.0.1
 */