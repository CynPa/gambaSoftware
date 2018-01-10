/*   1:    */ package com.asinfo.as2.caja.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.reportes.servicio.ServicioReporteCaja;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   6:    */ import com.asinfo.as2.entities.Banco;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.CierreCaja;
/*   9:    */ import com.asinfo.as2.entities.Cobro;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*  11:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*  13:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FormaPago;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  18:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.math.BigDecimal;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.RequestScoped;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @RequestScoped
/*  37:    */ public class ReporteCierreCajaBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   protected ServicioReporteCaja servicioReporteCaja;
/*  43:    */   private CierreCaja cierreCaja;
/*  44:    */   private BigDecimal total;
/*  45:    */   private Integer numero;
/*  46:    */   
/*  47:    */   protected JRDataSource getJRDataSource()
/*  48:    */   {
/*  49: 58 */     JRDataSource ds = null;
/*  50: 59 */     if (ParametrosSistema.getCierreCajaPorDenominacionFormaCobro(getCierreCaja().getIdOrganizacion()).booleanValue()) {
/*  51: 60 */       ds = getJRDataSourcePorDenominacionFormaCobro();
/*  52: 61 */     } else if (ParametrosSistema.isReporteCierreCajaPorFactura(getCierreCaja().getIdOrganizacion()).booleanValue()) {
/*  53: 62 */       ds = getJRDataSourcePorFactura();
/*  54:    */     } else {
/*  55: 64 */       ds = getJRDataSourceFactura();
/*  56:    */     }
/*  57: 66 */     return ds;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected JRDataSource getJRDataSourcePorDenominacionFormaCobro()
/*  61:    */   {
/*  62: 71 */     List listaDatosReporte = new ArrayList();
/*  63: 72 */     JRDataSource ds = null;
/*  64: 73 */     String[] fields = { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCierreCaja", "f_nombreDenominacion", "f_cantidadDenominacion", "f_totalDenominacion" };
/*  65:    */     try
/*  66:    */     {
/*  67: 76 */       listaDatosReporte = this.servicioReporteCaja.getReporteCierreCajaPorDenominacionFormaCobro(getCierreCaja().getId());
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71: 78 */       LOG.info("Error " + e);
/*  72: 79 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74: 81 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  75: 82 */     return ds;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected JRDataSource getJRDataSourceFactura()
/*  79:    */   {
/*  80: 87 */     List listaDatosReporte = new ArrayList();
/*  81: 88 */     JRDataSource ds = null;
/*  82:    */     
/*  83: 90 */     String[] fields = { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCobro", "f_fechaCobro", "f_formaPago", "f_descripcion", "f_valor", "f_documentoReferencia", "f_cierreCaja", "f_nombreComercial", "f_nombreFiscal", "f_banco", "f_numeroCuenta" };
/*  84: 94 */     if (getCierreCaja().getId() == 0) {
/*  85: 96 */       cargarPreVisualizacion(listaDatosReporte);
/*  86:    */     } else {
/*  87:    */       try
/*  88:    */       {
/*  89:101 */         listaDatosReporte = this.servicioReporteCaja.getReporteCierreCaja(getCierreCaja().getId());
/*  90:    */       }
/*  91:    */       catch (Exception e)
/*  92:    */       {
/*  93:104 */         LOG.info("Error " + e);
/*  94:105 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  95:    */       }
/*  96:    */     }
/*  97:108 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  98:109 */     return ds;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected JRDataSource getJRDataSourcePorFactura()
/* 102:    */   {
/* 103:113 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 104:114 */     JRDataSource ds = null;
/* 105:115 */     String[] fields = null;
/* 106:117 */     if (getCierreCaja().getId() == 0)
/* 107:    */     {
/* 108:119 */       cargarPreVisualizacion(listaDatosReporte);
/* 109:    */       
/* 110:121 */       fields = new String[] { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCobro", "f_fechaCobro", "f_formaPago", "f_descripcion", "f_valor", "f_documentoReferencia", "f_cierreCaja", "f_nombreComercial", "f_nombreFiscal", "f_banco", "f_numeroCuenta" };
/* 111:    */     }
/* 112:    */     else
/* 113:    */     {
/* 114:127 */       listaDatosReporte = this.servicioReporteCaja.getListaCierreCaja(this.cierreCaja);
/* 115:    */       
/* 116:129 */       fields = new String[] { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCobro", "f_fechaCobro", "f_formaPago", "f_descripcion", "f_valor", "f_documentoReferencia", "f_cierreCaja", "f_nombreComercial", "f_nombreFiscal", "f_banco", "f_numeroFC", "f_dia", "f_idFacturaCliente", "f_indicador" };
/* 117:    */     }
/* 118:135 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 119:136 */     return ds;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected String getCompileFileName()
/* 123:    */   {
/* 124:147 */     if (ParametrosSistema.getCierreCajaPorDenominacionFormaCobro(getCierreCaja().getIdOrganizacion()).booleanValue()) {
/* 125:148 */       return "reporteCierreCajaPorDenominacion";
/* 126:    */     }
/* 127:149 */     if (ParametrosSistema.isReporteCierreCajaPorFactura(getCierreCaja().getIdOrganizacion()).booleanValue())
/* 128:    */     {
/* 129:150 */       if (this.cierreCaja.getId() == 0) {
/* 130:151 */         return "reporteCierreCaja";
/* 131:    */       }
/* 132:153 */       return "reporteCierreCajaPorFactura";
/* 133:    */     }
/* 134:157 */     return "reporteCierreCaja";
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Map<String, Object> getReportParameters()
/* 138:    */   {
/* 139:168 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 140:169 */     reportParameters.put("ReportTitle", "Cierre Caja");
/* 141:170 */     reportParameters.put("p_fechaCierreCaja", this.cierreCaja.getFechaHasta() != null ? this.cierreCaja.getFechaHasta() : "");
/* 142:173 */     if ((AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) && (this.cierreCaja.getId() != 0))
/* 143:    */     {
/* 144:174 */       this.total = this.servicioReporteCaja.totalChquesPosfechados(this.cierreCaja);
/* 145:175 */       this.numero = this.servicioReporteCaja.numeroChequesPosfechados(this.cierreCaja);
/* 146:176 */       reportParameters.put("p_total", this.total);
/* 147:177 */       reportParameters.put("p_numero", this.numero);
/* 148:    */     }
/* 149:180 */     return reportParameters;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String execute()
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:189 */       super.prepareReport();
/* 157:    */     }
/* 158:    */     catch (JRException e)
/* 159:    */     {
/* 160:191 */       LOG.info("Error JRException");
/* 161:192 */       e.printStackTrace();
/* 162:193 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 163:    */     }
/* 164:    */     catch (IOException e)
/* 165:    */     {
/* 166:195 */       LOG.info("Error IOException");
/* 167:196 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 168:    */     }
/* 169:199 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void cargarPreVisualizacion(List listaDatosReporte)
/* 173:    */   {
/* 174:205 */     Object[] ob = new Object[this.cierreCaja.getListaDetalleCierreCaja().size()];
/* 175:206 */     for (DetalleCierreCaja detalleCierreCaja : this.cierreCaja.getListaDetalleCierreCaja())
/* 176:    */     {
/* 177:208 */       ob = new Object[15];
/* 178:209 */       ob[0] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCaja().getNombre() : detalleCierreCaja
/* 179:210 */         .getAnticipoCliente().getCaja().getNombre());
/* 180:211 */       ob[1] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getUsuarioCreacion() : detalleCierreCaja
/* 181:212 */         .getAnticipoCliente().getUsuarioCreacion());
/* 182:213 */       ob[2] = detalleCierreCaja.getCierreCaja().getFechaHasta();
/* 183:214 */       ob[3] = detalleCierreCaja.getCierreCaja().getEstado();
/* 184:215 */       ob[4] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCobro().getNumero() : detalleCierreCaja
/* 185:216 */         .getAnticipoCliente().getNumero());
/* 186:217 */       ob[5] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCobro().getFecha() : detalleCierreCaja
/* 187:218 */         .getAnticipoCliente().getFecha());
/* 188:219 */       ob[6] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getFormaPago().getNombre() : detalleCierreCaja
/* 189:220 */         .getAnticipoCliente().getFormaPago().getNombre());
/* 190:221 */       ob[7] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getDescripcion() : detalleCierreCaja
/* 191:222 */         .getAnticipoCliente().getDescripcion());
/* 192:223 */       ob[8] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getValor() : detalleCierreCaja
/* 193:224 */         .getAnticipoCliente().getValor());
/* 194:225 */       ob[9] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getDocumentoReferencia() : detalleCierreCaja
/* 195:226 */         .getAnticipoCliente().getDocumentoReferencia());
/* 196:227 */       ob[10] = detalleCierreCaja.getCierreCaja().getNumero();
/* 197:228 */       ob[11] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCobro().getEmpresa()
/* 198:229 */         .getNombreComercial() : detalleCierreCaja.getAnticipoCliente().getEmpresa().getNombreComercial());
/* 199:230 */       ob[12] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCobro().getEmpresa()
/* 200:231 */         .getNombreFiscal() : detalleCierreCaja.getAnticipoCliente().getEmpresa().getNombreFiscal());
/* 201:232 */       ob[13] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCuentaBancariaOrganizacion()
/* 202:233 */         .getCuentaBancaria().getBanco().getNombre() : detalleCierreCaja.getAnticipoCliente().getCuentaBancariaOrganizacion()
/* 203:234 */         .getCuentaBancaria().getBanco().getNombre());
/* 204:235 */       ob[14] = (detalleCierreCaja.getDetalleFormaCobro() != null ? detalleCierreCaja.getDetalleFormaCobro().getCuentaBancariaOrganizacion()
/* 205:236 */         .getCuentaBancaria().getNumero() : detalleCierreCaja.getAnticipoCliente().getCuentaBancariaOrganizacion().getCuentaBancaria()
/* 206:237 */         .getNumero());
/* 207:    */       
/* 208:239 */       listaDatosReporte.add(ob);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public CierreCaja getCierreCaja()
/* 213:    */   {
/* 214:250 */     if (this.cierreCaja == null) {
/* 215:251 */       this.cierreCaja = new CierreCaja();
/* 216:    */     }
/* 217:253 */     return this.cierreCaja;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setCierreCaja(CierreCaja cierreCaja)
/* 221:    */   {
/* 222:263 */     this.cierreCaja = cierreCaja;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public BigDecimal getTotal()
/* 226:    */   {
/* 227:270 */     return this.total;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setTotal(BigDecimal total)
/* 231:    */   {
/* 232:278 */     this.total = total;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Integer getNumero()
/* 236:    */   {
/* 237:285 */     return this.numero;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setNumero(Integer numero)
/* 241:    */   {
/* 242:293 */     this.numero = numero;
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.ReporteCierreCajaBean
 * JD-Core Version:    0.7.0.1
 */