/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.LlamadoAtencionDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  5:   */ import com.asinfo.as2.entities.Documento;
/*  6:   */ import com.asinfo.as2.entities.LlamadoAtencion;
/*  7:   */ import com.asinfo.as2.entities.MotivoLlamadoAtencion;
/*  8:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  9:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion;
/* 10:   */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/* 11:   */ import java.util.Date;
/* 12:   */ import java.util.HashMap;
/* 13:   */ import java.util.List;
/* 14:   */ import java.util.Map;
/* 15:   */ import javax.ejb.EJB;
/* 16:   */ import javax.ejb.Stateless;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class ServicioLlamadoAtencionImpl
/* 20:   */   extends AbstractServicioAS2
/* 21:   */   implements ServicioLlamadoAtencion
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 1L;
/* 24:   */   @EJB
/* 25:   */   private LlamadoAtencionDao llamadoAtencionDao;
/* 26:   */   @EJB
/* 27:   */   private ServicioSecuencia servicioSecuencia;
/* 28:   */   
/* 29:   */   public List<LlamadoAtencion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 30:   */   {
/* 31:36 */     return this.llamadoAtencionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int contarPorCriterio(Map<String, String> filters)
/* 35:   */   {
/* 36:41 */     return this.llamadoAtencionDao.contarPorCriterio(filters);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void guardar(LlamadoAtencion llamadoAtencion)
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:47 */       cargarSecuencia(llamadoAtencion);
/* 44:   */     }
/* 45:   */     catch (ExcepcionAS2 e)
/* 46:   */     {
/* 47:49 */       e.printStackTrace();
/* 48:   */     }
/* 49:51 */     this.llamadoAtencionDao.guardar(llamadoAtencion);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void eliminar(LlamadoAtencion llamadoAtencion)
/* 53:   */   {
/* 54:57 */     this.llamadoAtencionDao.eliminar(llamadoAtencion);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public LlamadoAtencion buscarPorId(int idDocumentoDigitalizado)
/* 58:   */   {
/* 59:64 */     return (LlamadoAtencion)this.llamadoAtencionDao.buscarPorId(Integer.valueOf(idDocumentoDigitalizado));
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List<LlamadoAtencion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 63:   */   {
/* 64:69 */     List<LlamadoAtencion> lista = this.llamadoAtencionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 65:70 */     for (LlamadoAtencion llamadoAtencion : lista) {
/* 66:71 */       llamadoAtencion.getMotivoLlamadoAtencion().getNombre();
/* 67:   */     }
/* 68:73 */     return lista;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public LlamadoAtencion cargarDetalles(int idLlamadoAtencion)
/* 72:   */   {
/* 73:77 */     return this.llamadoAtencionDao.cargarDetalles(idLlamadoAtencion);
/* 74:   */   }
/* 75:   */   
/* 76:   */   public List reportellamadosAtencion(int idOrganizacion, int idEmpleado, int idDepartamento, int idMotivoLlamadoAtencion, Date fechaDesde, Date fechaHasta)
/* 77:   */   {
/* 78:81 */     return this.llamadoAtencionDao.reportellamadosAtencion(idOrganizacion, idEmpleado, idDepartamento, idMotivoLlamadoAtencion, fechaDesde, fechaHasta);
/* 79:   */   }
/* 80:   */   
/* 81:   */   private void cargarSecuencia(LlamadoAtencion llamadoAtencion)
/* 82:   */     throws ExcepcionAS2
/* 83:   */   {
/* 84:86 */     if ((llamadoAtencion == null) || (llamadoAtencion.getNumero().isEmpty()))
/* 85:   */     {
/* 86:87 */       String numero = "";
/* 87:88 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(llamadoAtencion.getDocumento().getId(), llamadoAtencion.getFechaDesde());
/* 88:89 */       llamadoAtencion.setNumero(numero);
/* 89:   */     }
/* 90:   */   }
/* 91:   */   
/* 92:   */   public void actualizarAtributoEntidad(LlamadoAtencion llamadoAtencion, HashMap<String, Object> campos)
/* 93:   */   {
/* 94:95 */     this.llamadoAtencionDao.actualizarAtributoEntidad(llamadoAtencion, campos);
/* 95:   */   }
/* 96:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioLlamadoAtencionImpl
 * JD-Core Version:    0.7.0.1
 */