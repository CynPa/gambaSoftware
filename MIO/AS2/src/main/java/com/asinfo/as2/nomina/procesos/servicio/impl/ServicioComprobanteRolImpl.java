/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   5:    */ import com.asinfo.as2.dao.ReporteNominaDao;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   8:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.PagoRol;
/*  13:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  14:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  25:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioComprobanteRol;
/*  26:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.List;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.ejb.Stateless;
/*  33:    */ import javax.ejb.TransactionAttribute;
/*  34:    */ import javax.ejb.TransactionAttributeType;
/*  35:    */ import javax.ejb.TransactionManagement;
/*  36:    */ import javax.ejb.TransactionManagementType;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  40:    */ public class ServicioComprobanteRolImpl
/*  41:    */   extends AbstractServicioAS2Financiero
/*  42:    */   implements ServicioComprobanteRol
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 6492950286440474897L;
/*  45:    */   @EJB
/*  46:    */   private ReporteNominaDao reporteNominaDao;
/*  47:    */   @EJB
/*  48:    */   private PagoRolDao pagoRolDao;
/*  49:    */   @EJB
/*  50:    */   private ServicioPeriodo servicioPeriodo;
/*  51:    */   @EJB
/*  52:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  53:    */   @EJB
/*  54:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  55:    */   @EJB
/*  56:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  57:    */   
/*  58:    */   private void validar(PagoRol pagoRol)
/*  59:    */     throws ExcepcionAS2Financiero, ExcepcionAS2Nomina
/*  60:    */   {
/*  61: 76 */     this.servicioPeriodo.buscarPorFecha(pagoRol.getFecha(), pagoRol.getIdOrganizacion(), pagoRol.getDocumento().getDocumentoBase());
/*  62:    */   }
/*  63:    */   
/*  64:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  65:    */   public void contabilizar(PagoRol pagoRol)
/*  66:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, ExcepcionAS2Nomina, AS2Exception
/*  67:    */   {
/*  68: 91 */     if (!pagoRol.isIndicadorSaldoInicial())
/*  69:    */     {
/*  70: 92 */       validar(pagoRol);
/*  71: 93 */       Date fechaContabilizacion = pagoRol.getFecha();
/*  72:    */       
/*  73:    */ 
/*  74: 96 */       Asiento asiento = new Asiento();
/*  75: 97 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  76: 98 */       asiento.setSucursal(AppUtil.getSucursal());
/*  77: 99 */       TipoAsiento tipoAsiento = pagoRol.getDocumento().getTipoAsiento();
/*  78:100 */       asiento.setTipoAsiento(tipoAsiento);
/*  79:101 */       asiento.setEstado(Estado.ELABORADO);
/*  80:102 */       asiento.setIndicadorAutomatico(true);
/*  81:103 */       asiento.setDocumentoOrigen(pagoRol.getDocumento());
/*  82:    */       
/*  83:    */ 
/*  84:    */ 
/*  85:107 */       String concepto = pagoRol.getDocumento().getNombre().trim().concat("-").concat(pagoRol.getFecha().toString());
/*  86:    */       
/*  87:109 */       asiento.setFecha(fechaContabilizacion);
/*  88:112 */       if (pagoRol.getNombreEmpleadoFiniquito() != null) {
/*  89:113 */         concepto = "Finiquito -".concat(pagoRol.getNombreEmpleadoFiniquito());
/*  90:    */       }
/*  91:115 */       asiento.setConcepto(concepto);
/*  92:    */       
/*  93:    */ 
/*  94:118 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(pagoRol.getIdOrganizacion(), DocumentoBase.PAGO_ROL);
/*  95:    */       
/*  96:    */ 
/*  97:121 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(pagoRol.getIdOrganizacion(), DocumentoBase.PAGO_ROL);
/*  98:124 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/*  99:    */       {
/* 100:125 */         List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 101:126 */         lista.addAll(this.pagoRolDao.getInterfazPagoRolDimensiones(pagoRol, documentoContabilizacion
/* 102:127 */           .getProcesoContabilizacion()));
/* 103:    */         
/* 104:129 */         List<DetalleAsiento> lda = new ArrayList();
/* 105:131 */         for (DetalleAsiento da : this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, false)) {
/* 106:134 */           lda.add(da);
/* 107:    */         }
/* 108:137 */         asiento.getListaDetalleAsiento().addAll(lda);
/* 109:    */       }
/* 110:146 */       this.servicioAsiento.guardar(asiento);
/* 111:147 */       pagoRol.setAsiento(asiento);
/* 112:    */     }
/* 113:151 */     pagoRol.setEstado(Estado.CONTABILIZADO);
/* 114:    */     
/* 115:    */ 
/* 116:154 */     this.pagoRolDao.guardar(pagoRol);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioComprobanteRolImpl
 * JD-Core Version:    0.7.0.1
 */