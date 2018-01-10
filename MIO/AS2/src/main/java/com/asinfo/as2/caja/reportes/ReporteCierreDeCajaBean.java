/*   1:    */ package com.asinfo.as2.caja.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.procesos.servicio.ServicioCierreCaja;
/*   4:    */ import com.asinfo.as2.caja.reportes.servicio.ServicioReporteCaja;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.entities.CierreCaja;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  10:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  12:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.text.SimpleDateFormat;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteCierreDeCajaBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private transient ServicioUsuario servicioUsuario;
/*  39:    */   @EJB
/*  40:    */   private transient ServicioCierreCaja servicioCierreCaja;
/*  41:    */   @EJB
/*  42:    */   protected ServicioReporteCaja servicioReporteCaja;
/*  43:    */   private EntidadUsuario user;
/*  44:    */   private CierreCaja cierreCaja;
/*  45:    */   
/*  46:    */   private static enum TIPO_REPORTE_ENUM
/*  47:    */   {
/*  48: 45 */     POR_CAJA("Por caja"),  COMPARATIVO("Comparativo");
/*  49:    */     
/*  50:    */     private String nombre;
/*  51:    */     
/*  52:    */     private TIPO_REPORTE_ENUM(String nombre)
/*  53:    */     {
/*  54: 54 */       this.nombre = nombre;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public String getNombre()
/*  58:    */     {
/*  59: 63 */       return this.nombre;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63: 83 */   private TIPO_REPORTE_ENUM tipoReporte = TIPO_REPORTE_ENUM.COMPARATIVO;
/*  64:    */   private Date fechaDesde;
/*  65:    */   private Date fechaHasta;
/*  66:    */   
/*  67:    */   protected JRDataSource getJRDataSource()
/*  68:    */   {
/*  69: 89 */     JRDataSource ds = null;
/*  70: 90 */     if (TIPO_REPORTE_ENUM.POR_CAJA.equals(this.tipoReporte))
/*  71:    */     {
/*  72: 91 */       if (ParametrosSistema.isReporteCierreCajaPorFactura(getCierreCaja().getIdOrganizacion()).booleanValue()) {
/*  73: 92 */         ds = getJRDataSourcePorFactura();
/*  74:    */       } else {
/*  75: 94 */         ds = getJRDataSourceFactura();
/*  76:    */       }
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 97 */       List<Object[]> listaDatosReporte = new ArrayList();
/*  81: 98 */       String[] fields = { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCierreCaja", "f_formaPago", "f_valor", "f_group", "f_diferencia" };
/*  82:    */       
/*  83:100 */       listaDatosReporte = this.servicioReporteCaja.getReporteCierreCajaComparativo(getCierreCaja().getId());
/*  84:101 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  85:    */     }
/*  86:103 */     return ds;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected JRDataSource getJRDataSourcePorFactura()
/*  90:    */   {
/*  91:108 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  92:109 */     JRDataSource ds = null;
/*  93:110 */     String[] fields = null;
/*  94:111 */     listaDatosReporte = this.servicioReporteCaja.getListaCierreCaja(this.cierreCaja);
/*  95:112 */     fields = new String[] { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCobro", "f_fechaCobro", "f_formaPago", "f_descripcion", "f_valor", "f_documentoReferencia", "f_cierreCaja", "f_nombreComercial", "f_nombreFiscal", "f_banco", "f_numeroFC", "f_dia", "f_idFacturaCliente", "f_indicador" };
/*  96:    */     
/*  97:    */ 
/*  98:115 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  99:116 */     return ds;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected JRDataSource getJRDataSourceFactura()
/* 103:    */   {
/* 104:121 */     List listaDatosReporte = new ArrayList();
/* 105:122 */     JRDataSource ds = null;
/* 106:    */     
/* 107:124 */     String[] fields = { "f_caja", "f_usuario", "f_fechaHasta", "f_estado", "f_numeroCobro", "f_fechaCobro", "f_formaPago", "f_descripcion", "f_valor", "f_documentoReferencia", "f_cierreCaja", "f_nombreComercial", "f_nombreFiscal", "f_banco", "f_numeroCuenta" };
/* 108:    */     try
/* 109:    */     {
/* 110:130 */       listaDatosReporte = this.servicioReporteCaja.getReporteCierreCaja(getCierreCaja().getId());
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:133 */       LOG.info("Error " + e);
/* 115:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 116:    */     }
/* 117:136 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 118:137 */     return ds;
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected String getCompileFileName()
/* 122:    */   {
/* 123:142 */     if (TIPO_REPORTE_ENUM.POR_CAJA.equals(this.tipoReporte))
/* 124:    */     {
/* 125:143 */       if (ParametrosSistema.isReporteCierreCajaPorFactura(getCierreCaja().getIdOrganizacion()).booleanValue())
/* 126:    */       {
/* 127:144 */         if (this.cierreCaja.getId() == 0) {
/* 128:145 */           return "reporteCierreCaja";
/* 129:    */         }
/* 130:147 */         return "reporteCierreCajaPorFactura";
/* 131:    */       }
/* 132:150 */       return "reporteCierreCaja";
/* 133:    */     }
/* 134:153 */     return "reporteCierreCajaComparativo";
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Map<String, Object> getReportParameters()
/* 138:    */   {
/* 139:160 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 140:161 */     reportParameters.put("ReportTitle", "Cierre Caja");
/* 141:162 */     reportParameters.put("p_fechaCierreCaja", this.cierreCaja.getFechaHasta() != null ? this.cierreCaja.getFechaHasta() : "");
/* 142:164 */     if ((AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) && 
/* 143:165 */       (this.cierreCaja.getId() != 0))
/* 144:    */     {
/* 145:166 */       reportParameters.put("p_total", this.servicioReporteCaja.totalChquesPosfechados(this.cierreCaja));
/* 146:167 */       reportParameters.put("p_numero", this.servicioReporteCaja.numeroChequesPosfechados(this.cierreCaja));
/* 147:    */     }
/* 148:170 */     return reportParameters;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String execute()
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:176 */       super.prepareReport();
/* 156:    */     }
/* 157:    */     catch (JRException e)
/* 158:    */     {
/* 159:178 */       LOG.info("Error JRException");
/* 160:179 */       e.printStackTrace();
/* 161:180 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 162:    */     }
/* 163:    */     catch (IOException e)
/* 164:    */     {
/* 165:182 */       LOG.info("Error IOException");
/* 166:183 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 167:    */     }
/* 168:185 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<EntidadUsuario> autocompletarUsuarios(String consulta)
/* 172:    */   {
/* 173:189 */     return this.servicioUsuario.autocompletarUsuarios(consulta);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<CierreCaja> autocompletarCierreCaja(String consulta)
/* 177:    */   {
/* 178:193 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 179:194 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 180:195 */     if (getUser() != null) {
/* 181:196 */       filters.put("usuario.idUsuario", String.valueOf(getUser().getId()));
/* 182:    */     }
/* 183:198 */     if ((this.fechaDesde != null) && (this.fechaHasta != null)) {
/* 184:199 */       filters.put("fechaHasta", OperacionEnum.BETWEEN.name() + sdf.format(this.fechaDesde) + "~" + sdf.format(this.fechaHasta));
/* 185:200 */     } else if (this.fechaDesde != null) {
/* 186:201 */       filters.put("fechaHasta", ">=" + sdf.format(this.fechaDesde));
/* 187:202 */     } else if (this.fechaHasta != null) {
/* 188:203 */       filters.put("fechaHasta", "<=" + sdf.format(this.fechaHasta));
/* 189:    */     }
/* 190:205 */     filters.put("numero", consulta.trim());
/* 191:206 */     List<CierreCaja> list = this.servicioCierreCaja.obtenerListaCombo("numero", true, filters);
/* 192:    */     
/* 193:208 */     return list;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Date getFechaDesde()
/* 197:    */   {
/* 198:212 */     return this.fechaDesde;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setFechaDesde(Date fechaDesde)
/* 202:    */   {
/* 203:216 */     this.fechaDesde = fechaDesde;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Date getFechaHasta()
/* 207:    */   {
/* 208:220 */     return this.fechaHasta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setFechaHasta(Date fechaHasta)
/* 212:    */   {
/* 213:224 */     this.fechaHasta = fechaHasta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public EntidadUsuario getUser()
/* 217:    */   {
/* 218:228 */     return this.user;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setUser(EntidadUsuario user)
/* 222:    */   {
/* 223:232 */     this.user = user;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public TIPO_REPORTE_ENUM getTipoReporte()
/* 227:    */   {
/* 228:236 */     return this.tipoReporte;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setTipoReporte(TIPO_REPORTE_ENUM tipoReporte)
/* 232:    */   {
/* 233:240 */     this.tipoReporte = tipoReporte;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<SelectItem> getListaTipoReporte()
/* 237:    */   {
/* 238:244 */     List<SelectItem> lista = new ArrayList();
/* 239:245 */     for (TIPO_REPORTE_ENUM tr : TIPO_REPORTE_ENUM.values()) {
/* 240:246 */       if (TIPO_REPORTE_ENUM.COMPARATIVO.equals(tr))
/* 241:    */       {
/* 242:247 */         if (ParametrosSistema.getCierreCajaPorDenominacionFormaCobro(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 243:248 */           lista.add(new SelectItem(tr, tr.getNombre()));
/* 244:    */         }
/* 245:    */       }
/* 246:    */       else {
/* 247:251 */         lista.add(new SelectItem(tr, tr.getNombre()));
/* 248:    */       }
/* 249:    */     }
/* 250:254 */     return lista;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public CierreCaja getCierreCaja()
/* 254:    */   {
/* 255:258 */     return this.cierreCaja;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setCierreCaja(CierreCaja cierreCaja)
/* 259:    */   {
/* 260:262 */     this.cierreCaja = cierreCaja;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.ReporteCierreDeCajaBean
 * JD-Core Version:    0.7.0.1
 */