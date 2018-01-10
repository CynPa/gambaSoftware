/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.dao.PagoEmpleadoDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  11:    */ import com.asinfo.as2.entities.Empleado;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.PagoEmpleado;
/*  14:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  15:    */ import com.asinfo.as2.entities.Secuencia;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  26:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  27:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoEmpleado;
/*  28:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  29:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.Date;
/*  33:    */ import java.util.HashMap;
/*  34:    */ import java.util.List;
/*  35:    */ import java.util.Map;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.ejb.SessionContext;
/*  38:    */ import javax.ejb.Stateless;
/*  39:    */ import javax.ejb.TransactionAttribute;
/*  40:    */ import javax.ejb.TransactionAttributeType;
/*  41:    */ 
/*  42:    */ @Stateless
/*  43:    */ public class ServicioPagoEmpleadoImpl
/*  44:    */   extends AbstractServicioAS2Financiero
/*  45:    */   implements ServicioPagoEmpleado
/*  46:    */ {
/*  47:    */   private static final long serialVersionUID = 985880648357644817L;
/*  48:    */   @EJB
/*  49:    */   private PagoEmpleadoDao pagoEmpleadoDao;
/*  50:    */   @EJB
/*  51:    */   private ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  52:    */   @EJB
/*  53:    */   private ServicioSecuencia servicioSecuencia;
/*  54:    */   @EJB
/*  55:    */   private ServicioPeriodo servicioPeriodo;
/*  56:    */   @EJB
/*  57:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  58:    */   @EJB
/*  59:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  60:    */   @EJB
/*  61:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  62:    */   
/*  63:    */   public void guardar(PagoEmpleado pagoEmpleado)
/*  64:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  65:    */   {
/*  66: 88 */     int cantidadAprobados = 0;
/*  67: 89 */     PagoRolEmpleado pagoRolEmpleadoAux = new PagoRolEmpleado();
/*  68: 90 */     for (PagoRolEmpleado pre : pagoEmpleado.getListaPagoRolEmpleado()) {
/*  69: 91 */       if (pre.isTraSeleccionado())
/*  70:    */       {
/*  71: 92 */         cantidadAprobados++;
/*  72: 93 */         pagoRolEmpleadoAux = pre;
/*  73:    */       }
/*  74:    */     }
/*  75: 96 */     if (cantidadAprobados == 1)
/*  76:    */     {
/*  77: 97 */       pagoEmpleado.setNombres(pagoRolEmpleadoAux.getEmpleado().getNombres());
/*  78: 98 */       pagoEmpleado.setApellidos(pagoRolEmpleadoAux.getEmpleado().getApellidos());
/*  79: 99 */       pagoEmpleado.setIdentificacion(pagoRolEmpleadoAux.getEmpleado().getEmpresa().getIdentificacion());
/*  80:    */     }
/*  81:102 */     this.pagoEmpleadoDao.guardar(pagoEmpleado);
/*  82:103 */     for (PagoRolEmpleado pagoRolEmpleado : pagoEmpleado.getListaPagoRolEmpleado()) {
/*  83:104 */       if (pagoRolEmpleado.isTraSeleccionado()) {
/*  84:105 */         this.servicioPagoRolEmpleado.actualizarPagoEmpleado(pagoRolEmpleado, pagoEmpleado, true);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void contabilizar(PagoEmpleado pagoEmpleado, Date fechaContabilizacion)
/*  90:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  91:    */   {
/*  92:117 */     if (pagoEmpleado.getDocumento().isIndicadorContabilizar())
/*  93:    */     {
/*  94:118 */       PagoRolEmpleado pagoRolEmpleado = null;
/*  95:120 */       for (PagoRolEmpleado pre : pagoEmpleado.getListaPagoRolEmpleado()) {
/*  96:121 */         if ((pre.isTraSeleccionado()) && (pre.getDocumentoReferencia() != null) && (!pre.getDocumentoReferencia().isEmpty()))
/*  97:    */         {
/*  98:122 */           this.servicioPagoRolEmpleado.actualizarReferenciaPagoRolEmpleado(pre);
/*  99:123 */           pagoRolEmpleado = pre;
/* 100:    */         }
/* 101:    */       }
/* 102:127 */       if (pagoRolEmpleado != null)
/* 103:    */       {
/* 104:128 */         Secuencia secuencia = pagoRolEmpleado.getSecuencia();
/* 105:129 */         if (secuencia != null) {
/* 106:131 */           this.servicioSecuencia.actualizarSecuencia(secuencia, pagoRolEmpleado.getDocumentoReferencia());
/* 107:    */         }
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:134 */         throw new ExcepcionAS2("msg_info_seleccionar_registros_contabilizar");
/* 112:    */       }
/* 113:137 */       Asiento asiento = new Asiento();
/* 114:138 */       asiento.setConcepto(pagoEmpleado.getDocumento().getNombre().concat(pagoEmpleado.getNumero()));
/* 115:139 */       asiento.setFecha(fechaContabilizacion);
/* 116:140 */       asiento.setIdOrganizacion(pagoEmpleado.getIdOrganizacion());
/* 117:141 */       asiento.setSucursal(AppUtil.getSucursal());
/* 118:142 */       asiento.setDocumentoOrigen(pagoEmpleado.getDocumento());
/* 119:143 */       asiento.setTipoAsiento(pagoEmpleado.getDocumento().getTipoAsiento());
/* 120:    */       
/* 121:145 */       List<DetalleInterfazContable> listaDAIC = this.pagoEmpleadoDao.getListaDIC(pagoEmpleado);
/* 122:146 */       super.generarAsiento(asiento, listaDAIC, pagoEmpleado.getDocumento());
/* 123:    */       
/* 124:148 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(pagoEmpleado
/* 125:149 */         .getIdOrganizacion(), DocumentoBase.PAGO_EMPLEADO, ProcesoContabilizacionEnum.SUELDOS_POR_PAGAR_EMPLEADO);
/* 126:    */       
/* 127:    */ 
/* 128:152 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(pagoEmpleado.getIdOrganizacion(), DocumentoBase.PAGO_EMPLEADO);
/* 129:154 */       for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/* 130:    */       {
/* 131:155 */         List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 132:156 */         lista.addAll(this.pagoEmpleadoDao.getListaPagoRolEmpleado(pagoEmpleado));
/* 133:157 */         if (lista.size() > 0) {
/* 134:158 */           asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true, true));
/* 135:    */         }
/* 136:    */       }
/* 137:162 */       this.servicioAsiento.guardar(asiento);
/* 138:    */       
/* 139:164 */       pagoEmpleado.setAsiento(asiento);
/* 140:165 */       pagoEmpleado.setEstado(Estado.CONTABILIZADO);
/* 141:166 */       pagoEmpleado.setFechaContabilizar(asiento.getFecha());
/* 142:167 */       this.pagoEmpleadoDao.guardar(pagoEmpleado);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void eliminar(PagoEmpleado pagoEmpleado)
/* 147:    */   {
/* 148:179 */     this.pagoEmpleadoDao.eliminar(pagoEmpleado);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public PagoEmpleado buscarPorId(int idPagoEmpleado)
/* 152:    */   {
/* 153:189 */     return (PagoEmpleado)this.pagoEmpleadoDao.buscarPorId(Integer.valueOf(idPagoEmpleado));
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<PagoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 157:    */   {
/* 158:199 */     return this.pagoEmpleadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int contarPorCriterio(Map<String, String> filters)
/* 162:    */   {
/* 163:209 */     return this.pagoEmpleadoDao.contarPorCriterio(filters);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public PagoEmpleado cargarDetalle(int idPagoEmpleado)
/* 167:    */   {
/* 168:219 */     return this.pagoEmpleadoDao.cargarDetalle(idPagoEmpleado);
/* 169:    */   }
/* 170:    */   
/* 171:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 172:    */   public List<PagoRolEmpleado> cargarEmpleadosPendientes(int idPagoRol, boolean requiereAprobacion)
/* 173:    */   {
/* 174:230 */     return this.pagoEmpleadoDao.cargarEmpleadosPendientes(idPagoRol, requiereAprobacion);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void anular(PagoEmpleado pagoEmpleado)
/* 178:    */     throws ExcepcionAS2Financiero
/* 179:    */   {
/* 180:    */     try
/* 181:    */     {
/* 182:239 */       this.servicioPeriodo.buscarPorFecha(pagoEmpleado.getFecha(), pagoEmpleado.getIdOrganizacion(), pagoEmpleado.getDocumento().getDocumentoBase());
/* 183:241 */       if ((pagoEmpleado.getAsiento() != null) && (
/* 184:242 */         (pagoEmpleado.getAsiento().getEstado() == Estado.ANULADO) || (pagoEmpleado.getAsiento().getEstado() == Estado.REVISADO))) {
/* 185:243 */         throw new ExcepcionAS2Financiero("msg_error_editar");
/* 186:    */       }
/* 187:246 */       if (pagoEmpleado.getEstado() != Estado.ANULADO)
/* 188:    */       {
/* 189:247 */         for (PagoRolEmpleado pagoRolEmpleado : pagoEmpleado.getListaPagoRolEmpleado()) {
/* 190:248 */           this.servicioPagoRolEmpleado.actualizarPagoEmpleado(pagoRolEmpleado, null, false);
/* 191:    */         }
/* 192:250 */         this.pagoEmpleadoDao.actualizarEstado(pagoEmpleado.getIdPagoEmpleado(), Estado.ANULADO);
/* 193:252 */         if (pagoEmpleado.getAsiento() != null)
/* 194:    */         {
/* 195:253 */           pagoEmpleado.getAsiento().setIndicadorAutomatico(false);
/* 196:254 */           this.servicioAsiento.anular(pagoEmpleado.getAsiento());
/* 197:    */         }
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:258 */         throw new ExcepcionAS2Financiero("msg_error_anular");
/* 202:    */       }
/* 203:    */     }
/* 204:    */     catch (ExcepcionAS2Financiero e)
/* 205:    */     {
/* 206:262 */       e.printStackTrace();
/* 207:263 */       this.context.setRollbackOnly();
/* 208:264 */       throw e;
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void actualizarEstado(int idPagoEmpleado, Estado estado)
/* 213:    */   {
/* 214:277 */     this.pagoEmpleadoDao.actualizarEstado(idPagoEmpleado, estado);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void actualizarAtributoEntidad(PagoEmpleado pagoEmpleado, HashMap<String, Object> campos)
/* 218:    */   {
/* 219:282 */     this.pagoEmpleadoDao.actualizarAtributoEntidad(pagoEmpleado, campos);
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoEmpleadoImpl
 * JD-Core Version:    0.7.0.1
 */