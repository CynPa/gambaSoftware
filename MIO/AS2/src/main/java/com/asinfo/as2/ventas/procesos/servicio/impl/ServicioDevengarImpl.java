/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleValoresContratoVentaDao;
/*   4:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   5:    */ import com.asinfo.as2.dao.InterfazContableProcesoDao;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.ContratoVenta;
/*   8:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   9:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  10:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*  11:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  12:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*  19:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  29:    */ import com.asinfo.as2.util.AppUtil;
/*  30:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  31:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  32:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDevengar;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.ejb.LocalBean;
/*  40:    */ import javax.ejb.SessionContext;
/*  41:    */ import javax.ejb.Singleton;
/*  42:    */ import org.apache.log4j.Logger;
/*  43:    */ 
/*  44:    */ @Singleton
/*  45:    */ @LocalBean
/*  46:    */ public class ServicioDevengarImpl
/*  47:    */   extends AbstractServicioAS2
/*  48:    */   implements ServicioDevengar
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 1L;
/*  51:    */   @EJB
/*  52:    */   private DetalleValoresContratoVentaDao detalleValoresContratoVentaDao;
/*  53:    */   @EJB
/*  54:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  55:    */   @EJB
/*  56:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  57:    */   @EJB
/*  58:    */   private transient FacturaClienteDao facturaClienteDao;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  61:    */   @EJB
/*  62:    */   protected ServicioAsiento servicioAsiento;
/*  63:    */   @EJB
/*  64:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  65:    */   @EJB
/*  66:    */   private InterfazContableProcesoDao interfazContableProcesoDao;
/*  67:    */   
/*  68:    */   public void devengar(Date fecha, InterfazContableProceso interfazContableProceso, Organizacion organizacion)
/*  69:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  70:    */   {
/*  71: 81 */     contabilizar(fecha, interfazContableProceso, organizacion);
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void contabilizar(Date fecha, InterfazContableProceso interfazContableProceso, Organizacion organizacion)
/*  75:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79: 91 */       Asiento asiento = new Asiento();
/*  80: 92 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  81: 93 */       Sucursal sucursal = new Sucursal();
/*  82: 94 */       sucursal.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  83: 95 */       asiento.setSucursal(sucursal);
/*  84: 96 */       TipoAsiento tipoAsiento = null;
/*  85: 97 */       asiento.setFecha(fecha);
/*  86:    */       try
/*  87:    */       {
/*  88: 99 */         tipoAsiento = this.servicioTipoAsiento.buscarPorId(ParametrosSistema.getTipoAsientoInterfazContratoVenta(interfazContableProceso
/*  89:100 */           .getIdOrganizacion()));
/*  90:    */       }
/*  91:    */       catch (Exception e)
/*  92:    */       {
/*  93:102 */         throw new ExcepcionAS2("msg_info_configuracion", Parametro.TIPO_ASIENTO_CONTRATO_VENTA.getNombre());
/*  94:    */       }
/*  95:104 */       asiento.setTipoAsiento(tipoAsiento);
/*  96:105 */       asiento.setEstado(Estado.CONTABILIZADO);
/*  97:106 */       asiento.setConcepto("Devengado");
/*  98:    */       
/*  99:108 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(
/* 100:109 */         AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 101:    */       
/* 102:111 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 103:    */       
/* 104:    */ 
/* 105:114 */       Map<String, DocumentoContabilizacion> mapDocumentoContabilizacion = new HashMap();
/* 106:115 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 107:116 */         if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.VENTAS)) {
/* 108:117 */           mapDocumentoContabilizacion.put("Ventas", dc);
/* 109:118 */         } else if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.INGRESOS_CONTRATO_VENTA)) {
/* 110:119 */           mapDocumentoContabilizacion.put("IngresosContratoVenta", dc);
/* 111:    */         }
/* 112:    */       }
/* 113:124 */       Object listaDetalleValoresContratoVenta = this.detalleValoresContratoVentaDao.listaDetalleValoresContratoVenta(fecha, organizacion);
/* 114:125 */       List<Integer> listSaldosIniciales = new ArrayList();
/* 115:126 */       List<Integer> listSaldosNoIniciales = new ArrayList();
/* 116:127 */       for (DetalleValoresContratoVenta dvcv : (List)listaDetalleValoresContratoVenta)
/* 117:    */       {
/* 118:128 */         dvcv.setIndicadorContabilizado(Boolean.valueOf(true));
/* 119:129 */         if (interfazContableProceso != null) {
/* 120:130 */           dvcv.setInterfazContableProceso(interfazContableProceso);
/* 121:    */         }
/* 122:132 */         this.detalleValoresContratoVentaDao.guardar(dvcv);
/* 123:133 */         if (dvcv.getContratoVenta().isIndicadorSaldoInicial()) {
/* 124:134 */           listSaldosIniciales.add(Integer.valueOf(dvcv.getIdDetalleValoresContratoVenta()));
/* 125:    */         } else {
/* 126:136 */           listSaldosNoIniciales.add(Integer.valueOf(dvcv.getIdDetalleValoresContratoVenta()));
/* 127:    */         }
/* 128:    */       }
/* 129:141 */       Object listaTmp = new ArrayList();
/* 130:142 */       List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 131:    */       
/* 132:144 */       listaTmp = this.facturaClienteDao.getInterfazVentasDimensionesDevengado(listSaldosIniciales, ((DocumentoContabilizacion)mapDocumentoContabilizacion.get("Ventas")).getProcesoContabilizacion(), interfazContableProceso.isIndicadorAgrupaMovimientos(), true);
/* 133:145 */       ((List)listaTmp).addAll(this.facturaClienteDao.getInterfazVentasDimensionesDevengado(listSaldosNoIniciales, ((DocumentoContabilizacion)mapDocumentoContabilizacion.get("Ventas")).getProcesoContabilizacion(), interfazContableProceso.isIndicadorAgrupaMovimientos(), false));
/* 134:146 */       listaDetalleAsiento = this.servicioInterfazContableProceso.generarAsiento(asiento, (List)listaTmp, (DocumentoContabilizacion)mapDocumentoContabilizacion.get("Ventas"), listaCriterioDistribucion, true);
/* 135:147 */       listaTmp = this.facturaClienteDao.getInterfazVentasDimensionesDevengado(listSaldosIniciales, ((DocumentoContabilizacion)mapDocumentoContabilizacion.get("IngresosContratoVenta")).getProcesoContabilizacion(), interfazContableProceso.isIndicadorAgrupaMovimientos(), true);
/* 136:148 */       ((List)listaTmp).addAll(this.facturaClienteDao.getInterfazVentasDimensionesDevengado(listSaldosNoIniciales, ((DocumentoContabilizacion)mapDocumentoContabilizacion.get("IngresosContratoVenta")).getProcesoContabilizacion(), interfazContableProceso.isIndicadorAgrupaMovimientos(), false));
/* 137:149 */       listaDetalleAsiento.addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, (List)listaTmp, (DocumentoContabilizacion)mapDocumentoContabilizacion.get("IngresosContratoVenta"), listaCriterioDistribucion, false));
/* 138:    */       
/* 139:151 */       asiento.setListaDetalleAsiento(listaDetalleAsiento);
/* 140:    */       
/* 141:153 */       this.servicioAsiento.guardar(asiento);
/* 142:155 */       if (interfazContableProceso != null)
/* 143:    */       {
/* 144:156 */         interfazContableProceso.setAsiento(asiento);
/* 145:157 */         interfazContableProceso.setEstado(Estado.CONTABILIZADO);
/* 146:158 */         interfazContableProceso.setFechaContabilizacion(new Date());
/* 147:159 */         this.interfazContableProcesoDao.guardar(interfazContableProceso);
/* 148:    */       }
/* 149:    */     }
/* 150:    */     catch (ExcepcionAS2Ventas e)
/* 151:    */     {
/* 152:162 */       this.context.setRollbackOnly();
/* 153:163 */       throw e;
/* 154:    */     }
/* 155:    */     catch (ExcepcionAS2Financiero e)
/* 156:    */     {
/* 157:165 */       this.context.setRollbackOnly();
/* 158:166 */       throw e;
/* 159:    */     }
/* 160:    */     catch (AS2Exception e)
/* 161:    */     {
/* 162:168 */       this.context.setRollbackOnly();
/* 163:169 */       throw e;
/* 164:    */     }
/* 165:    */     catch (Exception e)
/* 166:    */     {
/* 167:171 */       e.printStackTrace();
/* 168:172 */       this.context.setRollbackOnly();
/* 169:173 */       LOG.error(e);
/* 170:174 */       throw new ExcepcionAS2(e);
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioDevengarImpl
 * JD-Core Version:    0.7.0.1
 */