/*  1:   */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.EmisionBonoDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.SecuenciaAS2Service;
/*  5:   */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  6:   */ import com.asinfo.as2.entities.Empresa;
/*  7:   */ import com.asinfo.as2.entities.Especialidad;
/*  8:   */ import com.asinfo.as2.entities.FacturaCliente;
/*  9:   */ import com.asinfo.as2.entities.PersonaResponsable;
/* 10:   */ import com.asinfo.as2.entities.PuntoDeVenta;
/* 11:   */ import com.asinfo.as2.entities.Sucursal;
/* 12:   */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/* 13:   */ import com.asinfo.as2.enumeraciones.Estado;
/* 14:   */ import com.asinfo.as2.excepciones.AS2Exception;
/* 15:   */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/* 16:   */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/* 17:   */ import com.asinfo.as2.ventas.procesos.servicio.ServicioEmisionBono;
/* 18:   */ import java.util.Date;
/* 19:   */ import java.util.List;
/* 20:   */ import java.util.Map;
/* 21:   */ import javax.ejb.EJB;
/* 22:   */ import javax.ejb.Stateless;
/* 23:   */ import javax.ejb.TransactionManagement;
/* 24:   */ import javax.ejb.TransactionManagementType;
/* 25:   */ 
/* 26:   */ @Stateless
/* 27:   */ @TransactionManagement(TransactionManagementType.CONTAINER)
/* 28:   */ public class ServicioEmisionBonoImpl
/* 29:   */   extends AbstractServicioAS2
/* 30:   */   implements ServicioEmisionBono
/* 31:   */ {
/* 32:   */   private static final long serialVersionUID = 1L;
/* 33:   */   @EJB
/* 34:   */   private EmisionBonoDao bonoDao;
/* 35:   */   @EJB
/* 36:   */   private SecuenciaAS2Service servicioSecuenciaAS2;
/* 37:   */   @EJB
/* 38:   */   private ServicioPeriodo servicioPeriodo;
/* 39:   */   
/* 40:   */   public int contarPorCriterio(Map<String, String> filters)
/* 41:   */   {
/* 42:53 */     return this.bonoDao.contarPorCriterio(filters);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<DetalleFacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 46:   */   {
/* 47:59 */     return this.bonoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void asignarNumeroBono(FacturaCliente facturaCliente)
/* 51:   */   {
/* 52:64 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/* 53:65 */       if ((!dfc.isEliminado()) && (dfc.isIndicadorBono()))
/* 54:   */       {
/* 55:67 */         String numeroSecuenciaBono = facturaCliente.getNumero().substring(0, 8) + this.servicioSecuenciaAS2.getSecuencia(facturaCliente.getIdOrganizacion(), "secuenciaBono");
/* 56:68 */         dfc.setReferencia(numeroSecuenciaBono);
/* 57:69 */         this.bonoDao.guardar(dfc);
/* 58:   */       }
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List<DetalleFacturaCliente> listaBonos(Date fechaDesde, Date fechaHasta, Empresa empresa, Empresa empresaBono, Sucursal sucursal, PuntoDeVenta puntoVenta, Especialidad tipoBono, PersonaResponsable responsableSalidaMercaderia, int idOrganizacion, FacturaCliente facturaCliente, EntidadUsuario usuario, int valorReporte, boolean anulado)
/* 63:   */   {
/* 64:79 */     return this.bonoDao.listaBonos(fechaDesde, fechaHasta, empresa, empresaBono, sucursal, puntoVenta, tipoBono, responsableSalidaMercaderia, idOrganizacion, facturaCliente, usuario, valorReporte, anulado);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void validarBonos(FacturaCliente facturaCliente)
/* 68:   */     throws AS2Exception
/* 69:   */   {
/* 70:86 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/* 71:87 */       if ((!dfc.isEliminado()) && (dfc.isIndicadorBono()) && ((dfc.getMedico() == null) || (dfc.getTipoBono() == null))) {
/* 72:88 */         throw new AS2Exception("com.asinfo.as2.ventas.procesos.servicio.impl.ServicioFacturaClienteImpl.NO_EXISTE_MEDICO_O_TIPO_BONO", new String[] { "" });
/* 73:   */       }
/* 74:   */     }
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void eliminarAnularBono(DetalleFacturaCliente bonoSeleccionado)
/* 78:   */   {
/* 79:95 */     bonoSeleccionado = (DetalleFacturaCliente)this.bonoDao.cargarDetalle(bonoSeleccionado.getIdDetalleFacturaCliente());
/* 80:96 */     bonoSeleccionado.setEstado(Estado.ANULADO);
/* 81:97 */     this.bonoDao.guardar(bonoSeleccionado);
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioEmisionBonoImpl
 * JD-Core Version:    0.7.0.1
 */