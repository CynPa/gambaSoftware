/*   1:    */ package com.asinfo.as2.ws.ventas.service.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.dao.GuiaRemisionDao;
/*   5:    */ import com.asinfo.as2.dao.SucursalDao;
/*   6:    */ import com.asinfo.as2.dao.TipoVehiculoDao;
/*   7:    */ import com.asinfo.as2.dao.TransportistaDao;
/*   8:    */ import com.asinfo.as2.dao.VehiculoDao;
/*   9:    */ import com.asinfo.as2.dao.sri.AutorizacionDocumentoSRIDao;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  12:    */ import com.asinfo.as2.entities.DetalleGuiaRemision;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  15:    */ import com.asinfo.as2.entities.Producto;
/*  16:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  17:    */ import com.asinfo.as2.entities.Ruta;
/*  18:    */ import com.asinfo.as2.entities.TipoVehiculo;
/*  19:    */ import com.asinfo.as2.entities.Transportista;
/*  20:    */ import com.asinfo.as2.entities.Vehiculo;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  26:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*  27:    */ import com.asinfo.as2.ventas.reportes.ReporteGuiaRemisionBean;
/*  28:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteGuiaRemision;
/*  29:    */ import com.asinfo.as2.ws.ventas.model.DetalleGuiaRemisionWSEntity;
/*  30:    */ import com.asinfo.as2.ws.ventas.model.GuiaRemisionWSEntity;
/*  31:    */ import com.asinfo.as2.ws.ventas.service.ServicioGuiaRemisionWS;
/*  32:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  33:    */ import java.math.BigDecimal;
/*  34:    */ import java.util.ArrayList;
/*  35:    */ import java.util.Calendar;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.jws.WebService;
/*  41:    */ 
/*  42:    */ @WebService(endpointInterface="com.asinfo.as2.ws.ventas.service.ServicioGuiaRemisionWS")
/*  43:    */ public class ServicioGuiaRemisionWSImpl
/*  44:    */   implements ServicioGuiaRemisionWS
/*  45:    */ {
/*  46:    */   @EJB
/*  47:    */   private ServicioGuiaRemision servicioGuiaRemision;
/*  48:    */   @EJB
/*  49:    */   private SucursalDao sucursalDao;
/*  50:    */   @EJB
/*  51:    */   private VehiculoDao vehiculoDao;
/*  52:    */   @EJB
/*  53:    */   private ServicioDocumento servicioDocumento;
/*  54:    */   @EJB
/*  55:    */   private ServicioSecuencia servicioSecuencia;
/*  56:    */   @EJB
/*  57:    */   private AutorizacionDocumentoSRIDao autorizacionDocumentoSRIDao;
/*  58:    */   @EJB
/*  59:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  60:    */   @EJB
/*  61:    */   private ServicioProducto servicioProducto;
/*  62:    */   @EJB
/*  63:    */   private TransportistaDao transportistaDao;
/*  64:    */   @EJB
/*  65:    */   private TipoVehiculoDao tipoVehiculoDao;
/*  66:    */   @EJB
/*  67:    */   private GuiaRemisionDao guiaRemisionDao;
/*  68:    */   @EJB
/*  69:    */   private ServicioReporteGuiaRemision servicioReporteGuiaRemision;
/*  70:    */   
/*  71:    */   public GuiaRemisionWSEntity guardarGuiaRemision(GuiaRemisionWSEntity guiaRemisionWS)
/*  72:    */     throws AS2Exception
/*  73:    */   {
/*  74: 73 */     GuiaRemision guiaRemision = new GuiaRemision();
/*  75:    */     try
/*  76:    */     {
/*  77: 77 */       Vehiculo vehiculo = this.vehiculoDao.cargarDetalle(guiaRemisionWS.getIdVehiculo().intValue());
/*  78: 78 */       TipoVehiculo tipoVehiculo = this.tipoVehiculoDao.cargarDetalle(vehiculo.getTipoVehiculo().getIdTipoVehiculo());
/*  79: 79 */       Transportista transportista = this.transportistaDao.cargarDetalle(guiaRemisionWS.getIdTransportista().intValue());
/*  80:    */       
/*  81: 81 */       Map<String, String> filters = new HashMap();
/*  82: 82 */       filters.put("idOrganizacion", String.valueOf(guiaRemisionWS.getIdOrganizacion()));
/*  83: 83 */       filters.put("documentoBase", DocumentoBase.GUIA_REMISION.toString());
/*  84: 84 */       filters.put("indicadorDocumentoTributario", String.valueOf(true));
/*  85: 85 */       filters.put("indicadorDocumentoExterior", String.valueOf(false));
/*  86: 86 */       filters.put("activo", "true");
/*  87: 87 */       List<Documento> listaDocumentos = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/*  88: 88 */       Documento documento = null;
/*  89: 89 */       if (listaDocumentos.size() > 0)
/*  90:    */       {
/*  91: 90 */         documento = (Documento)this.servicioDocumento.obtenerListaCombo("nombre", true, filters).get(0);
/*  92: 91 */         documento = this.servicioDocumento.cargarDetalle(documento.getIdDocumento());
/*  93:    */       }
/*  94:    */       else
/*  95:    */       {
/*  96: 93 */         throw new AS2Exception("com.asinfo.as2.ws.ventas.service.impl.ServicioGuiaRemisionWSImpl.CONFIGURAR_GUIA_FLORICOLA", new String[] { "" });
/*  97:    */       }
/*  98: 96 */       guiaRemision.setIdOrganizacion(guiaRemisionWS.getIdOrganizacion().intValue());
/*  99: 97 */       guiaRemision.setIdSucursal(guiaRemisionWS.getIdSucursal().intValue());
/* 100: 98 */       guiaRemision.setCiudadOrigen(((Ruta)tipoVehiculo.getListaRuta().get(0)).getCiudadOrigen());
/* 101: 99 */       guiaRemision.setCiudadDestino(((Ruta)tipoVehiculo.getListaRuta().get(0)).getCiudadDestino());
/* 102:100 */       guiaRemision.setIdHojaRuta(guiaRemisionWS.getIdFacturaCliente().intValue());
/* 103:101 */       guiaRemision.setFecha(guiaRemisionWS.getFecha().getTime());
/* 104:102 */       guiaRemision.setConductor(guiaRemisionWS.getConductor());
/* 105:    */       
/* 106:104 */       guiaRemision.setLicencia(guiaRemisionWS.getLicencia());
/* 107:105 */       guiaRemision.setIdentificacionTransportista(transportista.getIdentificacion());
/* 108:106 */       guiaRemision.setTarifa(BigDecimal.ZERO);
/* 109:107 */       guiaRemision.setDescripcion("Floricola");
/* 110:108 */       guiaRemision.setPlaca(vehiculo.getPlaca());
/* 111:109 */       guiaRemision.setVehiculo(vehiculo);
/* 112:110 */       guiaRemision.setFechaVigencia(guiaRemisionWS.getFechaVigencia().getTime());
/* 113:111 */       guiaRemision.setDocumento(documento);
/* 114:112 */       guiaRemision.setTransportista(transportista);
/* 115:113 */       guiaRemision.setTipoIdentificacionTransportista(transportista.getTipoIdentificacion());
/* 116:114 */       guiaRemision.setRuta(((Ruta)tipoVehiculo.getListaRuta().get(0)).getRuta());
/* 117:115 */       guiaRemision.setEmailTransportista(transportista.getEmail());
/* 118:    */       
/* 119:117 */       guiaRemision.setHoraSalida(guiaRemisionWS.getHoraSalida());
/* 120:118 */       guiaRemision.setHoraLlegada(guiaRemisionWS.getHoraLlegada());
/* 121:119 */       guiaRemision.setRuta(guiaRemisionWS.getRuta());
/* 122:    */       
/* 123:    */ 
/* 124:122 */       filters = new HashMap();
/* 125:123 */       filters.put("idOrganizacion", String.valueOf(guiaRemisionWS.getIdOrganizacion().intValue()));
/* 126:124 */       filters.put("sucursal.codigo", guiaRemisionWS.getEstablecimiento());
/* 127:125 */       filters.put("codigo", guiaRemisionWS.getPuntoVenta());
/* 128:126 */       List<PuntoDeVenta> listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("", false, filters);
/* 129:    */       PuntoDeVenta puntoVenta;
/* 130:127 */       if (listaPuntoVenta.size() > 0) {
/* 131:128 */         puntoVenta = (PuntoDeVenta)listaPuntoVenta.get(0);
/* 132:    */       } else {
/* 133:131 */         throw new AS2Exception("msg_error_punto_factura", new String[] {guiaRemisionWS.getEstablecimiento() + "-" + guiaRemisionWS.getPuntoVenta() });
/* 134:    */       }
/* 135:    */       PuntoDeVenta puntoVenta;
/* 136:136 */       if ((guiaRemision.getNumero() == null) || (guiaRemision.getNumero().isEmpty())) {
/* 137:137 */         this.servicioGuiaRemision.cargarSecuencia(guiaRemision, puntoVenta);
/* 138:    */       }
/* 139:140 */       this.servicioSecuencia.detach(guiaRemision.getDocumento().getSecuencia());
/* 140:142 */       for (DetalleGuiaRemisionWSEntity dgrWS : guiaRemisionWS.getListaDetalleGuiaRemision())
/* 141:    */       {
/* 142:144 */         Producto producto = this.servicioProducto.buscarPorCodigo(dgrWS.getCodigoProducto(), dgrWS.getIdOrganizacion().intValue(), null);
/* 143:145 */         DetalleGuiaRemision dgr = new DetalleGuiaRemision();
/* 144:146 */         dgr.setCantidad(dgrWS.getCantidad());
/* 145:147 */         dgr.setGuiaRemision(guiaRemision);
/* 146:148 */         dgr.setIdOrganizacion(guiaRemisionWS.getIdOrganizacion().intValue());
/* 147:149 */         dgr.setIdSucursal(guiaRemisionWS.getIdSucursal().intValue());
/* 148:150 */         dgr.setProducto(producto);
/* 149:151 */         dgr.setValor1(dgrWS.getValor1());
/* 150:152 */         dgr.setValor2(dgrWS.getValor2());
/* 151:153 */         dgr.setValor3(dgrWS.getValor3());
/* 152:154 */         dgr.setNombre1(dgrWS.getNombre1());
/* 153:155 */         dgr.setNombre2(dgrWS.getNombre2());
/* 154:156 */         dgr.setNombre3(dgrWS.getNombre3());
/* 155:157 */         dgr.setDescripcion("EXPORTACION");
/* 156:158 */         guiaRemision.getListaDetalleGuiaRemision().add(dgr);
/* 157:    */       }
/* 158:162 */       this.servicioGuiaRemision.guardar(guiaRemision);
/* 159:    */       
/* 160:    */ 
/* 161:165 */       guiaRemisionWS.setIdDespachoCliente(Long.valueOf(guiaRemision.getId()));
/* 162:166 */       return guiaRemisionWS;
/* 163:    */     }
/* 164:    */     catch (ExcepcionAS2Identification e)
/* 165:    */     {
/* 166:169 */       throw new AS2Exception("msg_error_identificacion", new String[] { "" });
/* 167:    */     }
/* 168:    */     catch (ExcepcionAS2 e)
/* 169:    */     {
/* 170:171 */       if ("msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())) {
/* 171:172 */         throw new AS2Exception("msg_secuencia_no_encontrada", new String[] { guiaRemision.getDocumento().getNombre() });
/* 172:    */       }
/* 173:174 */       throw new AS2Exception("msg_proceso_erroneo", new String[] { e.getCodigoExcepcion() });
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean liberarGuiaRemisionAutomatica(Long idGuiaRemision)
/* 178:    */     throws AS2Exception
/* 179:    */   {
/* 180:180 */     this.guiaRemisionDao.liberarGuiaRemisionAutomatica(Integer.valueOf(idGuiaRemision.intValue()));
/* 181:181 */     return true;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public ArrayList<Object[]> getDatosReporteGuiaRemision(int idGuiaRemision)
/* 185:    */   {
/* 186:188 */     ArrayList<Object[]> lista = (ArrayList)this.servicioReporteGuiaRemision.getReporteGuiaRemision(0, 0, idGuiaRemision, TipoOrganizacion.TIPO_ORGANIZACION_GENERAL, null);
/* 187:189 */     return lista;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String[] getFieldsReporteGuiaRemision()
/* 191:    */   {
/* 192:194 */     return ReporteGuiaRemisionBean.fields;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.service.impl.ServicioGuiaRemisionWSImpl
 * JD-Core Version:    0.7.0.1
 */