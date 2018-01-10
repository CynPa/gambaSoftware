/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   8:    */ import com.asinfo.as2.entities.Departamento;
/*   9:    */ import com.asinfo.as2.entities.Empleado;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PagoRol;
/*  12:    */ import com.asinfo.as2.entities.Quincena;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.JsfUtil;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class ReporteSobreEmpleadoBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = -7306672667528700536L;
/*  41:    */   @EJB
/*  42:    */   private ServicioReporteNomina servicioReporteNomina;
/*  43:    */   @EJB
/*  44:    */   private ServicioPagoRol servicioPagoRol;
/*  45:    */   @EJB
/*  46:    */   private ServicioSucursal servicioSucursal;
/*  47:    */   @EJB
/*  48:    */   private ServicioDepartamento servicioDepartamento;
/*  49:    */   private List<SelectItem> listaPagoRol;
/*  50:    */   private PagoRol pagoRol;
/*  51:    */   private Empleado empleado;
/*  52:    */   private Sucursal sucursal;
/*  53:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  54:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  55:    */   private List<Sucursal> listaSucursal;
/*  56:    */   private Departamento departamento;
/*  57:    */   private List<Departamento> listaDepartamento;
/*  58:    */   private boolean indicadorEnvioCorreos;
/*  59: 97 */   public static final String[] fields = { "nombreEmpleado", "identificacion", "operacion", "nombreRubro", "ingresos", "egresos", "fechaPagoRol", "cargoEmpleado", "diasFalta", "tiempo", "departamento", "cuentaBancaria", "diasTrabajados", "quincena", "indicadorProvision", "indicadorImpresionSobre", "email1", "email2", "fechaRolPadre", "codigoEmpleado" };
/*  60:    */   @EJB
/*  61:    */   private ServicioOrganizacion servicioOrganizacion;
/*  62:    */   @EJB
/*  63:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  64:    */   
/*  65:    */   protected JRDataSource getJRDataSource()
/*  66:    */   {
/*  67:114 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  68:115 */     JRDataSource ds = null;
/*  69:    */     
/*  70:117 */     listaDatosReporte = this.servicioReporteNomina.getSobreEmpleado(this.pagoRol, this.empleado, this.formaPagoEmpleado, getSucursal(), AppUtil.getOrganizacion()
/*  71:118 */       .getIdOrganizacion(), this.departamento, false);
/*  72:119 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  73:120 */     return ds;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void actuaizarEnvioCorreos()
/*  77:    */   {
/*  78:124 */     if ((getPagoRol() != null) && (getPagoRol().getIdPagoRol() != 0)) {
/*  79:125 */       this.indicadorEnvioCorreos = true;
/*  80:    */     } else {
/*  81:127 */       this.indicadorEnvioCorreos = false;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Map<String, Object> getReportParameters()
/*  86:    */   {
/*  87:139 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  88:140 */     reportParameters.put("ReportTitle", "Sobre Rol");
/*  89:141 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/*  90:142 */     reportParameters.put("FechaRol", FuncionesUtiles.nombreMes(this.pagoRol.getMes() - 1) + "-" + Integer.toString(this.pagoRol.getAnio()));
/*  91:143 */     reportParameters.remove("IS_IGNORE_PAGINATION");
/*  92:    */     
/*  93:145 */     return reportParameters;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String execute()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:155 */       super.prepareReport();
/* 101:    */     }
/* 102:    */     catch (JRException e)
/* 103:    */     {
/* 104:157 */       LOG.info("Error JRException");
/* 105:158 */       e.printStackTrace();
/* 106:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:    */     catch (IOException e)
/* 109:    */     {
/* 110:161 */       LOG.info("Error IOException");
/* 111:162 */       e.printStackTrace();
/* 112:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:166 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarEmpleado()
/* 118:    */   {
/* 119:170 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected String getCompileFileName()
/* 123:    */   {
/* 124:181 */     return "reporteSobreEmpleado";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void enviarRolEmpleado()
/* 128:    */   {
/* 129:187 */     Organizacion organizacion = this.servicioOrganizacion.buscarPorId(Integer.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 130:188 */     Sucursal sucursal = AppUtil.getSucursal();
/* 131:    */     try
/* 132:    */     {
/* 133:190 */       this.servicioReporteNomina.enviarEmailPagoRolEmpleados(organizacion, getCompileFileName(), getPagoRol(), sucursal, getEmpleado(), 
/* 134:191 */         getFormaPagoEmpleado(), getDepartamento());
/* 135:192 */       addInfoMessage(getLanguageController().getMensaje("msg_info_emails_enviados"));
/* 136:    */     }
/* 137:    */     catch (AS2Exception e)
/* 138:    */     {
/* 139:194 */       JsfUtil.addErrorMessage(e, "");
/* 140:195 */       e.printStackTrace();
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:197 */       addErrorMessage(e.getMessage());
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Empleado getEmpleado()
/* 149:    */   {
/* 150:208 */     if (this.empleado == null) {
/* 151:209 */       this.empleado = new Empleado();
/* 152:    */     }
/* 153:211 */     return this.empleado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setEmpleado(Empleado empleado)
/* 157:    */   {
/* 158:221 */     this.empleado = empleado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<SelectItem> getListaPagoRol()
/* 162:    */   {
/* 163:230 */     List<PagoRol> lista = new ArrayList();
/* 164:231 */     Map<String, String> filters = new HashMap();
/* 165:232 */     filters.put("indicadorFiniquito", "false");
/* 166:    */     
/* 167:234 */     lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 168:235 */     if (this.listaPagoRol == null)
/* 169:    */     {
/* 170:236 */       this.listaPagoRol = new ArrayList();
/* 171:237 */       for (PagoRol pagoRol : lista)
/* 172:    */       {
/* 173:243 */         String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 174:244 */         SelectItem item = new SelectItem(Integer.valueOf(pagoRol.getIdPagoRol()), label);
/* 175:245 */         this.listaPagoRol.add(item);
/* 176:    */       }
/* 177:    */     }
/* 178:248 */     return this.listaPagoRol;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public PagoRol getPagoRol()
/* 182:    */   {
/* 183:257 */     if (this.pagoRol == null) {
/* 184:258 */       this.pagoRol = new PagoRol();
/* 185:    */     }
/* 186:260 */     return this.pagoRol;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setPagoRol(PagoRol pagoRol)
/* 190:    */   {
/* 191:270 */     this.pagoRol = pagoRol;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 195:    */   {
/* 196:279 */     if (this.listaFormaPagoEmpleado == null)
/* 197:    */     {
/* 198:280 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 199:281 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 200:    */       {
/* 201:282 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 202:283 */         this.listaFormaPagoEmpleado.add(item);
/* 203:    */       }
/* 204:    */     }
/* 205:286 */     return this.listaFormaPagoEmpleado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 209:    */   {
/* 210:295 */     return this.formaPagoEmpleado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 214:    */   {
/* 215:305 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Sucursal getSucursal()
/* 219:    */   {
/* 220:314 */     if (this.sucursal == null) {
/* 221:315 */       this.sucursal = new Sucursal();
/* 222:    */     }
/* 223:317 */     return this.sucursal;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setSucursal(Sucursal sucursal)
/* 227:    */   {
/* 228:327 */     this.sucursal = sucursal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public List<Sucursal> getListaSucursal()
/* 232:    */   {
/* 233:336 */     if (this.listaSucursal == null) {
/* 234:337 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 235:    */     }
/* 236:339 */     return this.listaSucursal;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 240:    */   {
/* 241:349 */     this.listaSucursal = listaSucursal;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Departamento getDepartamento()
/* 245:    */   {
/* 246:353 */     return this.departamento;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setDepartamento(Departamento departamento)
/* 250:    */   {
/* 251:357 */     this.departamento = departamento;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List<Departamento> getListaDepartamento()
/* 255:    */   {
/* 256:361 */     if (this.listaDepartamento == null) {
/* 257:362 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 258:    */     }
/* 259:364 */     return this.listaDepartamento;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 263:    */   {
/* 264:368 */     this.listaDepartamento = listaDepartamento;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isIndicadorEnvioCorreos()
/* 268:    */   {
/* 269:372 */     return this.indicadorEnvioCorreos;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setIndicadorEnvioCorreos(boolean indicadorEnvioCorreos)
/* 273:    */   {
/* 274:376 */     this.indicadorEnvioCorreos = indicadorEnvioCorreos;
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteSobreEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */