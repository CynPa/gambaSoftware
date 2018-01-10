/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleHojaRutaDao;
/*   4:    */ import com.asinfo.as2.dao.HojaRutaDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   9:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  10:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*  11:    */ import com.asinfo.as2.entities.Documento;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.HojaRuta;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.entities.Transportista;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  22:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  23:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*  24:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  25:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.ejb.Stateless;
/*  32:    */ import javax.ejb.TransactionAttribute;
/*  33:    */ import javax.ejb.TransactionAttributeType;
/*  34:    */ import javax.ejb.TransactionManagement;
/*  35:    */ import javax.ejb.TransactionManagementType;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  39:    */ public class ServicioHojaRutaImpl
/*  40:    */   implements ServicioHojaRuta
/*  41:    */ {
/*  42:    */   @EJB
/*  43:    */   private HojaRutaDao hojaRutaDao;
/*  44:    */   @EJB
/*  45:    */   private DetalleHojaRutaDao detalleHojaRutaDao;
/*  46:    */   @EJB
/*  47:    */   private ServicioSecuencia servicioSecuencia;
/*  48:    */   @EJB
/*  49:    */   private ServicioPeriodo servicioPeriodo;
/*  50:    */   @EJB
/*  51:    */   private ServicioDespachoCliente servicioDespachoCliente;
/*  52:    */   @EJB
/*  53:    */   private ServicioDocumento servicioDocumento;
/*  54:    */   @EJB
/*  55:    */   private ServicioEmpresa servicioEmpresa;
/*  56:    */   @EJB
/*  57:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  58:    */   @EJB
/*  59:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  60:    */   
/*  61:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  62:    */   public void guardar(HojaRuta hojaRuta)
/*  63:    */     throws ExcepcionAS2, ExcepcionAS2Inventario, ExcepcionAS2Financiero
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67: 88 */       if ((hojaRuta.getIndicadorHojaRutaTransportista() == null) || (!hojaRuta.getIndicadorHojaRutaTransportista().booleanValue())) {
/*  68: 89 */         hojaRuta.setIndicadorHojaRutaTransportista(Boolean.valueOf(false));
/*  69:    */       }
/*  70: 92 */       hojaRuta.setEstado(Estado.ELABORADO);
/*  71:    */       
/*  72: 94 */       validar(hojaRuta);
/*  73:    */       
/*  74:    */ 
/*  75: 97 */       cargarSecuencia(hojaRuta);
/*  76:100 */       for (DetalleHojaRuta dhr : hojaRuta.getListaDetalleHojaRuta())
/*  77:    */       {
/*  78:101 */         if (dhr.isSeleccionado()) {
/*  79:102 */           dhr.setEliminado(false);
/*  80:    */         } else {
/*  81:104 */           dhr.setEliminado(true);
/*  82:    */         }
/*  83:106 */         dhr.setHojaRuta(hojaRuta);
/*  84:107 */         dhr.setIdSucursal(hojaRuta.getSucursal().getIdSucursal());
/*  85:108 */         this.detalleHojaRutaDao.guardar(dhr);
/*  86:    */       }
/*  87:111 */       this.hojaRutaDao.guardar(hojaRuta);
/*  88:    */     }
/*  89:    */     catch (ExcepcionAS2 e)
/*  90:    */     {
/*  91:114 */       throw e;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void validar(HojaRuta hojaRuta)
/*  96:    */     throws ExcepcionAS2Financiero
/*  97:    */   {
/*  98:127 */     this.servicioPeriodo.buscarPorFecha(hojaRuta.getFecha(), hojaRuta.getIdOrganizacion(), hojaRuta.getDocumento().getDocumentoBase());
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void cargarSecuencia(HojaRuta hojaRuta)
/* 102:    */     throws ExcepcionAS2
/* 103:    */   {
/* 104:139 */     if (hojaRuta.getNumero().equals(""))
/* 105:    */     {
/* 106:140 */       String numero = "";
/* 107:141 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(hojaRuta.getDocumento().getId(), hojaRuta.getFecha());
/* 108:142 */       hojaRuta.setNumero(numero);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public HojaRuta buscarPorId(Integer idHojaRuta)
/* 113:    */     throws ExcepcionAS2
/* 114:    */   {
/* 115:153 */     return (HojaRuta)this.hojaRutaDao.buscarPorId(idHojaRuta);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public HojaRuta cargarDetalle(Integer idHojaRuta)
/* 119:    */   {
/* 120:163 */     HojaRuta hojaRuta = this.hojaRutaDao.cargarDetalle(idHojaRuta.intValue());
/* 121:164 */     for (DetalleHojaRuta detalleHojaRuta : hojaRuta.getListaDetalleHojaRuta()) {
/* 122:166 */       detalleHojaRuta.setSeleccionado(true);
/* 123:    */     }
/* 124:169 */     return hojaRuta;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<HojaRuta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 128:    */   {
/* 129:179 */     return this.hojaRutaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List getReporteHojaRuta(int idHojaRuta)
/* 133:    */     throws ExcepcionAS2
/* 134:    */   {
/* 135:190 */     return null;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void esEditable(HojaRuta hojaRuta)
/* 139:    */     throws ExcepcionAS2Financiero, ExcepcionAS2Ventas
/* 140:    */   {
/* 141:195 */     this.servicioPeriodo.buscarPorFecha(hojaRuta.getFecha(), hojaRuta.getIdOrganizacion(), hojaRuta.getDocumento().getDocumentoBase());
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int contarPorCriterio(Map<String, String> filters)
/* 145:    */   {
/* 146:205 */     return this.hojaRutaDao.contarPorCriterio(filters);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<DetalleDespachoCliente> cargarDespachos(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 150:    */   {
/* 151:215 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<DetalleHojaRuta> cargarDetalleHojaRuta(int idOrganizacion, Sucursal sucursal, HojaRuta hojaRuta, Date fechaDesde, Date fechaHasta)
/* 155:    */   {
/* 156:220 */     return this.hojaRutaDao.cargarDespachos(idOrganizacion, sucursal, fechaDesde, fechaHasta);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<Object[]> reporteHojaRuta(int idOrganizacion, int idHojaRuta)
/* 160:    */   {
/* 161:225 */     return this.hojaRutaDao.reporteHojaRuta(idOrganizacion, idHojaRuta);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public List<DetalleHojaRuta> detalleHojaRutaTransportista(int idOrganizacion, Sucursal sucursal, Transportista transportista, Date fechaDesde, Empresa empresa, Date fechaHasta)
/* 165:    */   {
/* 166:232 */     List<DetalleHojaRuta> listaDetalleHojaRuta = new ArrayList();
/* 167:233 */     for (DespachoCliente despacho : this.hojaRutaDao.detalleHojaRutaTransportista(idOrganizacion, sucursal, transportista, fechaDesde, empresa, fechaHasta))
/* 168:    */     {
/* 169:234 */       DetalleHojaRuta dhr = new DetalleHojaRuta(despacho.getIdOrganizacion(), despacho.getSucursal().getIdSucursal(), despacho);
/* 170:235 */       listaDetalleHojaRuta.add(dhr);
/* 171:    */     }
/* 172:238 */     return listaDetalleHojaRuta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<Object[]> reporteHojaRutaTransportista(int idOrganizacion, int idHojaRuta, boolean detallado)
/* 176:    */   {
/* 177:243 */     return this.hojaRutaDao.reporteHojaRutaTransportista(idOrganizacion, idHojaRuta, detallado);
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioHojaRutaImpl
 * JD-Core Version:    0.7.0.1
 */