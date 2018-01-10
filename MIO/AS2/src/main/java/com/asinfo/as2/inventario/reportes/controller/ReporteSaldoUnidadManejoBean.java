/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.SaldoUnidadManejo;
/*   9:    */ import com.asinfo.as2.entities.Subempresa;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Transportista;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  13:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.event.SelectEvent;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteSaldoUnidadManejoBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @EJB
/*  39:    */   private ServicioMovimientoUnidadManejo servicioMovimientoUnidadManejo;
/*  40:    */   @EJB
/*  41:    */   private ServicioEmpresa servicioEmpresa;
/*  42:    */   @EJB
/*  43:    */   private ServicioSucursal servicioSucursal;
/*  44:    */   @EJB
/*  45:    */   private ServicioTransportista servicioTransportista;
/*  46: 56 */   private final String COMPILE_FILE_NAME = "reporteSaldosUnidadManejo";
/*  47:    */   private Date fecha;
/*  48:    */   private Date fechaHasta;
/*  49:    */   private Empresa empresa;
/*  50:    */   private Sucursal sucursal;
/*  51:    */   private Transportista transportista;
/*  52:    */   private Subempresa subempresa;
/*  53:    */   protected List<Transportista> listaTransportista;
/*  54:    */   private List<Sucursal> listaSucursal;
/*  55:    */   private List<Subempresa> listaSubempresa;
/*  56:    */   
/*  57:    */   protected JRDataSource getJRDataSource()
/*  58:    */   {
/*  59: 72 */     JRDataSource ds = null;
/*  60:    */     
/*  61: 74 */     List<SaldoUnidadManejo> listaDatosReporte = this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(AppUtil.getOrganizacion()
/*  62: 75 */       .getIdOrganizacion(), this.sucursal, this.empresa, this.subempresa, this.transportista, null);
/*  63: 76 */     ds = new JRBeanCollectionDataSource(listaDatosReporte);
/*  64: 77 */     return ds;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Map<String, Object> getReportParameters()
/*  68:    */   {
/*  69: 85 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  70: 86 */     reportParameters.put("ReportTitle", "Reporte Saldos Unidad Manejo");
/*  71:    */     
/*  72: 88 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  73:    */     
/*  74: 90 */     return reportParameters;
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected String getCompileFileName()
/*  78:    */   {
/*  79: 98 */     return "reporteSaldosUnidadManejo";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String execute()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:107 */       super.prepareReport();
/*  87:    */     }
/*  88:    */     catch (JRException e)
/*  89:    */     {
/*  90:109 */       LOG.info("Error JRException");
/*  91:110 */       e.printStackTrace();
/*  92:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  93:    */     }
/*  94:    */     catch (IOException e)
/*  95:    */     {
/*  96:113 */       LOG.info("Error IOException");
/*  97:114 */       e.printStackTrace();
/*  98:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  99:    */     }
/* 100:118 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Date getFecha()
/* 104:    */   {
/* 105:126 */     if (this.fecha == null) {
/* 106:127 */       this.fecha = FuncionesUtiles.getFechaInicioMes(new Date());
/* 107:    */     }
/* 108:129 */     return this.fecha;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Date getFechaHasta()
/* 112:    */   {
/* 113:133 */     if (this.fechaHasta == null) {
/* 114:134 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/* 115:    */     }
/* 116:136 */     return this.fechaHasta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setFecha(Date fecha)
/* 120:    */   {
/* 121:144 */     this.fecha = fecha;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Empresa getEmpresa()
/* 125:    */   {
/* 126:153 */     return this.empresa;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setEmpresa(Empresa empresa)
/* 130:    */   {
/* 131:160 */     if (empresa != null)
/* 132:    */     {
/* 133:161 */       this.sucursal = null;
/* 134:162 */       this.transportista = null;
/* 135:    */     }
/* 136:164 */     this.empresa = empresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 140:    */   {
/* 141:173 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<Subempresa> autocompletarSubEmpresa(String consulta)
/* 145:    */   {
/* 146:177 */     return this.servicioEmpresa.autocompletarSubEmpresa(consulta, getEmpresa());
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setFechaHasta(Date fechaHasta)
/* 150:    */   {
/* 151:181 */     this.fechaHasta = fechaHasta;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Transportista> getListaTransportista()
/* 155:    */   {
/* 156:189 */     if (this.listaTransportista == null)
/* 157:    */     {
/* 158:190 */       HashMap<String, String> filters = new HashMap();
/* 159:191 */       filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 160:192 */       filters.put("activo", "true");
/* 161:193 */       filters.put("usuario.idUsuario", "!=0");
/* 162:194 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 163:    */     }
/* 164:197 */     return this.listaTransportista;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaTransportista(List<Transportista> listaTransportista)
/* 168:    */   {
/* 169:205 */     this.listaTransportista = listaTransportista;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<Sucursal> getListaSucursal()
/* 173:    */   {
/* 174:212 */     if (this.listaSucursal == null) {
/* 175:213 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 176:    */     }
/* 177:215 */     return this.listaSucursal;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 181:    */   {
/* 182:223 */     this.listaSucursal = listaSucursal;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Sucursal getSucursal()
/* 186:    */   {
/* 187:230 */     return this.sucursal;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setSucursal(Sucursal sucursal)
/* 191:    */   {
/* 192:238 */     if (sucursal != null)
/* 193:    */     {
/* 194:239 */       this.transportista = null;
/* 195:240 */       this.empresa = null;
/* 196:241 */       this.subempresa = null;
/* 197:    */     }
/* 198:243 */     this.sucursal = sucursal;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Transportista getTransportista()
/* 202:    */   {
/* 203:250 */     return this.transportista;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setTransportista(Transportista transportista)
/* 207:    */   {
/* 208:258 */     if (transportista != null)
/* 209:    */     {
/* 210:259 */       this.sucursal = null;
/* 211:260 */       this.empresa = null;
/* 212:261 */       this.subempresa = null;
/* 213:    */     }
/* 214:264 */     this.transportista = transportista;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Subempresa getSubempresa()
/* 218:    */   {
/* 219:271 */     return this.subempresa;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setSubempresa(Subempresa subempresa)
/* 223:    */   {
/* 224:279 */     this.subempresa = subempresa;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void actualizarCliente(SelectEvent event)
/* 228:    */   {
/* 229:283 */     setEmpresa((Empresa)event.getObject());
/* 230:284 */     cargarSubempresas();
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void cargarSubempresas()
/* 234:    */   {
/* 235:288 */     if (getEmpresa() != null) {
/* 236:289 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(getEmpresa().getId());
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<Subempresa> getListaSubempresa()
/* 241:    */   {
/* 242:297 */     if (this.listaSubempresa == null) {
/* 243:298 */       this.listaSubempresa = new ArrayList();
/* 244:    */     }
/* 245:300 */     return this.listaSubempresa;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 249:    */   {
/* 250:308 */     this.listaSubempresa = listaSubempresa;
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteSaldoUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */